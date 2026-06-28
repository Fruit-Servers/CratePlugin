package de.slikey.effectlib.effect;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.util.RandomUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

public class FireworkEffect extends Effect {

    public org.bukkit.FireworkEffect.Type fireworkType = org.bukkit.FireworkEffect.Type.BALL;
    public Color color = Color.RED;
    public Color color2 = Color.ORANGE;
    public Color color3 = Color.BLACK;
    public Color fadeColor = Color.BLACK;
    public boolean trail = true;

    public int explosions = 1;
    public double radius = 0.5;
    public int power = 1;

    protected org.bukkit.FireworkEffect firework;

    public FireworkEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.INSTANT;
        period = 20;
        asynchronous = false;
    }

    @Override
    public void onRun() {
        if (firework == null) {
            org.bukkit.FireworkEffect.Builder b = org.bukkit.FireworkEffect.builder().with(fireworkType);
            b.withColor(color).withColor(color2).withColor(color3);
            b.withFade(fadeColor);
            b.trail(trail);
            firework = b.build();
        }

        Location location = getLocation();
        for (int i = 0; i < explosions; i++) {
            Vector v = RandomUtils.getRandomVector().multiply(radius);
            location.add(v);
            final Firework fw = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK_ROCKET);
            FireworkMeta meta = fw.getFireworkMeta();
            meta.setPower(power);
            meta.addEffect(firework);
            fw.setFireworkMeta(meta);
            location.subtract(v);
        }

    }
}
