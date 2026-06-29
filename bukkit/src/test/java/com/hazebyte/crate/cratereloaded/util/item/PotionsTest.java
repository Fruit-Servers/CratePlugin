package com.hazebyte.crate.cratereloaded.util.item;

import com.hazebyte.crate.BukkitTest;
import java.util.stream.Stream;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PotionsTest extends BukkitTest {

    @ParameterizedTest
    @MethodSource("effectNames")
    public void getByName_resolvesLegacyAndModernNames(String name, PotionEffectType expected) {
        Assertions.assertEquals(expected, Potions.getByName(name));
    }

    static Stream<Arguments> effectNames() {
        return Stream.of(
                // pre-1.20.5 constant names Minecraft renamed
                Arguments.of("DAMAGE_RESISTANCE", PotionEffectType.RESISTANCE),
                Arguments.of("FAST_DIGGING", PotionEffectType.HASTE),
                Arguments.of("INCREASE_DAMAGE", PotionEffectType.STRENGTH),
                Arguments.of("SLOW", PotionEffectType.SLOWNESS),
                Arguments.of("SLOW_DIGGING", PotionEffectType.MINING_FATIGUE),
                // already-aliased legacy + a modern name still resolve
                Arguments.of("CONFUSION", PotionEffectType.NAUSEA),
                Arguments.of("STRENGTH", PotionEffectType.STRENGTH));
    }
}
