package com.hazebyte.crate.cratereloaded.util;

import com.hazebyte.crate.cratereloaded.CorePlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import org.bukkit.enchantments.Enchantment;

public class Enchantments {
    private static final Map<String, Enchantment> ENCHANTS = new HashMap<>();
    private static final StringJoiner enchantSB = new StringJoiner(", ");

    static {
        for (Enchantment e : Enchantment.values()) enchantSB.add(e.getName());

        put("environmental_protection", Enchantment.PROTECTION);
        put("protection", Enchantment.PROTECTION);
        put("fire_protection", Enchantment.FIRE_PROTECTION);
        put("fall_protection", Enchantment.FEATHER_FALLING);
        put("explosion_protection", Enchantment.BLAST_PROTECTION);
        put("projectile_protection", Enchantment.PROJECTILE_PROTECTION);
        put("oxygen", Enchantment.RESPIRATION);
        put("water_worker", Enchantment.AQUA_AFFINITY);
        put("thorns", Enchantment.THORNS);
        put("depth_strider", Enchantment.DEPTH_STRIDER);
        put("frost_walker", "FROST_WALKER");
        put("binding_curse", "BINDING_CURSE");
        put("sharpness", Enchantment.SHARPNESS);
        put("smite", Enchantment.SMITE);
        put("bane_of_arthropods", Enchantment.BANE_OF_ARTHROPODS);
        put("fire_aspect", Enchantment.FIRE_ASPECT);
        put("looting", Enchantment.LOOTING);
        put("sweeping_edge", "SWEEPING_EDGE");
        put("efficiency", Enchantment.EFFICIENCY);
        put("silk_touch", Enchantment.SILK_TOUCH);
        put("unbreaking", Enchantment.UNBREAKING);
        put("fortune", Enchantment.FORTUNE);
        put("power", Enchantment.POWER);
        put("punch", Enchantment.PUNCH);
        put("flame", Enchantment.FLAME);
        put("infinity", Enchantment.INFINITY);
        put("luck", Enchantment.LUCK_OF_THE_SEA);
        put("lure", Enchantment.LURE);
        put("mending", "MENDING");
        put("vanishing_curse", "VANISHING_CURSE");

        put("channeling", "CHANNELING");
        put("impaling", "IMPALING");
        put("multishot", "MULTSHOT");
        put("piercing", "PIERCING");
        put("quick_charge", "QUICK_CHARGE");

        // Pre-1.20.5 constant names Minecraft renamed; kept so configs written before the rename resolve.
        put("durability", Enchantment.UNBREAKING);
        put("dig_speed", Enchantment.EFFICIENCY);
        put("damage_all", Enchantment.SHARPNESS);
        put("damage_undead", Enchantment.SMITE);
        put("damage_arthropods", Enchantment.BANE_OF_ARTHROPODS);
        put("loot_bonus_blocks", Enchantment.FORTUNE);
        put("loot_bonus_mobs", Enchantment.LOOTING);
        put("arrow_damage", Enchantment.POWER);
        put("arrow_knockback", Enchantment.PUNCH);
        put("arrow_fire", Enchantment.FLAME);
        put("arrow_infinite", Enchantment.INFINITY);
        put("protection_environmental", Enchantment.PROTECTION);
        put("protection_fire", Enchantment.FIRE_PROTECTION);
        put("protection_fall", Enchantment.FEATHER_FALLING);
        put("protection_explosions", Enchantment.BLAST_PROTECTION);
        put("protection_projectile", Enchantment.PROJECTILE_PROTECTION);
    }

    private static void put(String name, Enchantment enchant) {
        ENCHANTS.put(name, enchant);
    }

    private static void put(String name, String enchant) {
        Enchantment enchantment = Enchantment.getByName(enchant);
        if (enchantment != null) {
            ENCHANTS.put(name, enchantment);
        } else {
            CorePlugin.getPlugin().getLogger().finer(enchant + " was not found.");
        }
    }

    public static Enchantment getByName(String name) {
        Enchantment enchantment = Enchantment.getByName(name.toUpperCase());
        return enchantment == null ? ENCHANTS.get(name.toLowerCase()) : enchantment;
    }

    public static String getStringFormat() {
        return enchantSB.toString();
    }
}
