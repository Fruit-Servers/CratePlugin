package com.hazebyte.crate.cratereloaded.component.impl;

import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.effect.Category;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.EffectResolverComponent;
import com.hazebyte.crate.cratereloaded.component.EffectServiceComponent;
import com.hazebyte.crate.cratereloaded.provider.effect.EffectWrapper;
import com.hazebyte.crate.cratereloaded.provider.effect.slikey.SlikeyEffect;
import com.hazebyte.crate.cratereloaded.provider.effect.slikey.SlikeyEffectNames;
import com.hazebyte.crate.cratereloaded.util.LocationUtil;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Level;

public class EffectServiceComponentImpl extends EffectManager implements EffectServiceComponent {

    private final CorePlugin plugin;
    private final Map<String, ConfigurationSection> idToEffectConfigMapping;
    private final Map<Location, List<EffectWrapper>> locationToEffectMapping;
    private final List<String> availableEffects;
    private final Set<String> invalidEffectWarnings = new HashSet<>();

    public EffectServiceComponentImpl(JavaPlugin javaPlugin) {
        super(javaPlugin);
        this.plugin = (CorePlugin) javaPlugin;
        this.locationToEffectMapping = new HashMap<>();
        this.idToEffectConfigMapping = new HashMap<>();
        this.availableEffects = SlikeyEffectNames.discoverEffectNames(Effect.class.getClassLoader());
    }

    @Override
    public void startEffectsWhenCrateIsBound(Crate crate, Location location) {
        EffectResolverComponent effectResolver = plugin.getJavaPluginComponent().getEffectResolverComponent();
        List<EffectWrapper> effects = effectResolver.getEffects(crate, Category.PERSISTENT);
        startEffects(location, effects);
    }

    @Override
    public void resetEffectsWhenCrateIsUnbound(Location location) {
        stopEffects(location);
        restartEffectsIfExistingBindsAreAvailable(location);
    }

    @Override
    public void startEffects(Location location, List<EffectWrapper> effects) {
        if (effects == null) return;
        effects.forEach(effect -> startEffect(location, effect));
    }

    @Override
    public void stopEffects(Location location) {
        List<EffectWrapper> effects = locationToEffectMapping.get(location);
        if (effects != null) {
            effects.forEach(EffectWrapper::stop);
            effects.clear();
            locationToEffectMapping.put(location, null);
        }
    }

    @Override
    public Optional<ConfigurationSection> getEffectConfiguration(String id) {
        return Optional.ofNullable(idToEffectConfigMapping.get(id));
    }

    @Override
    public void registerEffectConfiguration(String id, ConfigurationSection effectSection) {
        idToEffectConfigMapping.put(id, effectSection);
    }

    @Override
    public Optional<EffectWrapper> createEffect(ConfigurationSection configurationSection) {
        if (configurationSection == null) return Optional.empty();

        String effectClass = configurationSection.getString("class");
        if (effectClass == null || effectClass.isEmpty()) return Optional.empty();

        try {
            Effect effect = this.getEffect(effectClass, configurationSection, null, null, null, (Player) null, null);

            if (effect == null) {
                String path = configurationSection.getCurrentPath();
                String key = effectClass + "@" + path;

                if (invalidEffectWarnings.add(key)) {
                    StringBuilder msg = new StringBuilder();
                    msg.append("Effect '").append(effectClass).append("' is invalid");

                    if (path != null && !path.isEmpty()) {
                        msg.append(" at '").append(path).append("'");
                    }

                    msg.append(".");

                    if (!availableEffects.isEmpty()) {
                        msg.append(" Available built-in effects: ")
                                .append(String.join(", ", availableEffects));
                    }

                    plugin.getLogger().warning(msg.toString());
                }

                return Optional.empty();
            }

            String categoryClass = configurationSection.getString("category", Category.OPEN.name());
            Category category;
            try {
                category = Category.valueOf(categoryClass.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException ex) {
                plugin.getLogger().warning("Invalid effect category '" + categoryClass + "' for effect '" + effectClass +
                        "' at '" + configurationSection.getCurrentPath() + "'.");
                return Optional.empty();
            }

            if (category == Category.PERSISTENT) {
                effect.infinite();
            }

            SlikeyEffect slikeyEffect = new SlikeyEffect(effect);
            return Optional.of(slikeyEffect);
        } catch (Exception e) {
            plugin.getLogger().log(
                    Level.WARNING,
                    "Failed to create effect '" + effectClass + "' at '" + configurationSection.getCurrentPath() + "'",
                    e
            );
            return Optional.empty();
        }
    }

    private void startEffect(Location location, EffectWrapper effectWrapper) {
        if (effectWrapper == null || hasEffectRunning(location)) {
            return;
        }

        if (!effectWrapper.isPersistent()) {
            return;
        }

        effectWrapper.setLocation(LocationUtil.getBlockCenter(location));
        locationToEffectMapping.putIfAbsent(location, new ArrayList<>());
        locationToEffectMapping.get(location).add(effectWrapper);
        effectWrapper.start();
    }

    private void restartEffectsIfExistingBindsAreAvailable(Location location) {
        List<Crate> crates = plugin.getBlockCrateRegistrar().getCrates(location);
        if (crates == null || crates.isEmpty()) {
            return;
        }

        EffectResolverComponent effectResolver = plugin.getJavaPluginComponent().getEffectResolverComponent();
        crates.stream()
                .map(crate -> effectResolver.getEffects(crate, Category.PERSISTENT))
                .filter(effectWrappers -> !effectWrappers.isEmpty())
                .findFirst()
                .ifPresent(effectWrappers -> startEffects(location, effectWrappers));
    }

    private boolean hasEffectRunning(Location location) {
        return locationToEffectMapping.get(location) != null
                && locationToEffectMapping.get(location).size() > 0;
    }
}
