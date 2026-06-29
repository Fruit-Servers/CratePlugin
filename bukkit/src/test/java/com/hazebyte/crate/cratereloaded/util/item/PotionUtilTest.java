package com.hazebyte.crate.cratereloaded.util.item;

import com.hazebyte.crate.BukkitTest;
import java.util.stream.Stream;
import org.bukkit.potion.PotionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PotionUtilTest extends BukkitTest {

    @ParameterizedTest
    @MethodSource("potionNames")
    public void matchType_resolvesLegacyAndModernNames(String name, PotionType expected) {
        Assertions.assertEquals(expected, PotionUtil.matchType(name));
    }

    static Stream<Arguments> potionNames() {
        return Stream.of(
                // pre-1.20.5 names Minecraft renamed or removed
                Arguments.of("SPEED", PotionType.SWIFTNESS),
                Arguments.of("JUMP", PotionType.LEAPING),
                Arguments.of("INSTANT_HEAL", PotionType.HEALING),
                Arguments.of("INSTANT_DAMAGE", PotionType.HARMING),
                Arguments.of("REGEN", PotionType.REGENERATION),
                Arguments.of("UNCRAFTABLE", PotionType.WATER),
                // case-insensitive, and modern names still resolve
                Arguments.of("speed", PotionType.SWIFTNESS),
                Arguments.of("SWIFTNESS", PotionType.SWIFTNESS),
                Arguments.of("POISON", PotionType.POISON));
    }

    @Test
    public void matchType_returnsNull_forUnknownName() {
        Assertions.assertNull(PotionUtil.matchType("NOT_A_POTION"));
    }
}
