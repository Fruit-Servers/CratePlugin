package com.hazebyte.crate.cratereloaded.util;

import com.hazebyte.crate.BukkitTest;
import java.util.stream.Stream;
import org.bukkit.enchantments.Enchantment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EnchantmentsTest extends BukkitTest {

    @ParameterizedTest
    @MethodSource("enchantNames")
    public void getByName_resolvesLegacyAndModernNames(String name, Enchantment expected) {
        Assertions.assertEquals(expected, Enchantments.getByName(name));
    }

    static Stream<Arguments> enchantNames() {
        return Stream.of(
                // pre-1.20.5 constant names Minecraft renamed
                Arguments.of("DURABILITY", Enchantment.UNBREAKING),
                Arguments.of("DIG_SPEED", Enchantment.EFFICIENCY),
                Arguments.of("DAMAGE_ALL", Enchantment.SHARPNESS),
                Arguments.of("DAMAGE_UNDEAD", Enchantment.SMITE),
                Arguments.of("DAMAGE_ARTHROPODS", Enchantment.BANE_OF_ARTHROPODS),
                Arguments.of("LOOT_BONUS_BLOCKS", Enchantment.FORTUNE),
                Arguments.of("LOOT_BONUS_MOBS", Enchantment.LOOTING),
                Arguments.of("ARROW_DAMAGE", Enchantment.POWER),
                Arguments.of("ARROW_KNOCKBACK", Enchantment.PUNCH),
                Arguments.of("ARROW_FIRE", Enchantment.FLAME),
                Arguments.of("ARROW_INFINITE", Enchantment.INFINITY),
                Arguments.of("PROTECTION_ENVIRONMENTAL", Enchantment.PROTECTION),
                Arguments.of("PROTECTION_FIRE", Enchantment.FIRE_PROTECTION),
                Arguments.of("PROTECTION_FALL", Enchantment.FEATHER_FALLING),
                Arguments.of("PROTECTION_EXPLOSIONS", Enchantment.BLAST_PROTECTION),
                Arguments.of("PROTECTION_PROJECTILE", Enchantment.PROJECTILE_PROTECTION),
                // case-insensitive, and a modern name still resolves
                Arguments.of("durability", Enchantment.UNBREAKING),
                Arguments.of("SHARPNESS", Enchantment.SHARPNESS));
    }
}
