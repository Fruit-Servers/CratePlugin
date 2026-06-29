package com.hazebyte.crate.cratereloaded.util.item;

import com.hazebyte.crate.api.ServerVersion;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.util.format.Digits;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.potion.PotionEffectType;

public class Potions {
    private static final Map<String, PotionEffectType> POTIONS = new HashMap<>();

    static {
        put("absorption", PotionEffectType.ABSORPTION);
        put("blindness", PotionEffectType.BLINDNESS);
        put("confusion", PotionEffectType.NAUSEA);
        put("nausea", PotionEffectType.NAUSEA);
        put("resistance", PotionEffectType.RESISTANCE);
        put("haste", PotionEffectType.HASTE);
        put("fire_resistance", PotionEffectType.FIRE_RESISTANCE);
        put("harm", PotionEffectType.INSTANT_DAMAGE);
        put("instant_damage", PotionEffectType.INSTANT_DAMAGE);
        put("heal", PotionEffectType.INSTANT_HEALTH);
        put("instant_health", PotionEffectType.INSTANT_HEALTH);
        put("health_boost", PotionEffectType.HEALTH_BOOST);
        put("hunger", PotionEffectType.HUNGER);
        put("strength", PotionEffectType.STRENGTH);
        put("invisibility", PotionEffectType.INVISIBILITY);
        put("jump", PotionEffectType.JUMP_BOOST);
        put("jump_boost", PotionEffectType.JUMP_BOOST);
        put("levitation", "LEVITATION", ServerVersion.v1_9_R1);
        put("luck", "LUCK", ServerVersion.v1_9_R1);
        put("unluck", "UNLUCK", ServerVersion.v1_9_R1);
        put("night_vision", PotionEffectType.NIGHT_VISION);
        put("poison", PotionEffectType.POISON);
        put("regeneration", PotionEffectType.REGENERATION);
        put("saturation", PotionEffectType.SATURATION);
        put("slowness", PotionEffectType.SLOWNESS);
        put("fatigue", PotionEffectType.MINING_FATIGUE);
        put("mining_fatigue", PotionEffectType.MINING_FATIGUE);
        put("slow_falling", "SLOW_FALLING", ServerVersion.v1_13_R1);
        put("conduit_power", "CONDUIT_POWER", ServerVersion.v1_13_R1);
        put("dolphins_grace", "DOLPHINS_GRACE", ServerVersion.v1_13_R1);
        put("speed", PotionEffectType.SPEED);
        put("water_breath", PotionEffectType.WATER_BREATHING);
        put("weakness", PotionEffectType.WEAKNESS);
        put("wither", PotionEffectType.WITHER);

        // Pre-1.20.5 constant names Minecraft renamed; kept so configs written before the rename resolve.
        put("damage_resistance", PotionEffectType.RESISTANCE);
        put("fast_digging", PotionEffectType.HASTE);
        put("increase_damage", PotionEffectType.STRENGTH);
        put("slow", PotionEffectType.SLOWNESS);
        put("slow_digging", PotionEffectType.MINING_FATIGUE);
    }

    private static void put(String name, PotionEffectType effectType) {
        POTIONS.put(name, effectType);
    }

    private static void put(String name, String effectType, ServerVersion minimumVersion) {
        if (CorePlugin.getPlugin().getServerVersion().gte(minimumVersion)) {
            PotionEffectType type = PotionEffectType.getByName(effectType.toUpperCase());
            if (type != null) {
                POTIONS.put(name, type);
            }
        }
    }

    public static PotionEffectType getByName(String string) {
        PotionEffectType potionEffectType;
        if (Digits.containsDigit(string)) {
            potionEffectType = PotionEffectType.getById(Integer.parseInt(string));
        } else {
            potionEffectType = PotionEffectType.getByName(string.toUpperCase());
        }

        if (potionEffectType == null) {
            potionEffectType = POTIONS.get(string.toLowerCase());
        }
        return potionEffectType;
    }
}
