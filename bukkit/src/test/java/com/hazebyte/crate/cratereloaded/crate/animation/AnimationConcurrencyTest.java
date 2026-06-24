package com.hazebyte.crate.cratereloaded.crate.animation;

import com.hazebyte.crate.BukkitTest;
import com.hazebyte.crate.api.crate.CrateType;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.crate.animation.scroller.Csgo;
import com.hazebyte.crate.cratereloaded.model.CrateImpl;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/** Constructing an animation for one player must not disturb the shared in-progress registry. */
public class AnimationConcurrencyTest extends BukkitTest {

    @AfterEach
    public void clearTracking() {
        Animation.clearTracked();
    }

    private static PluginSettingComponent settings() {
        PluginSettingComponent settings = Mockito.mock(PluginSettingComponent.class);
        Mockito.when(settings.getCrateAnimationShuffleDisplay())
                .thenReturn(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE));
        return settings;
    }

    private static Csgo newAnimation() {
        return new Csgo(new CrateImpl("regression-crate", CrateType.KEY), 160, settings());
    }

    @Test
    public void constructingAnimationForSecondPlayerDoesNotEvictFirst() {
        Player first = server.addPlayer();
        Player second = server.addPlayer();

        Csgo firstAnimation = newAnimation();
        Animation.players.add(first.getUniqueId().toString());
        Assertions.assertTrue(firstAnimation.has(first), "first player should be tracked while opening");

        // A second player opening constructs a fresh Animation; it must not evict the first.
        Csgo secondAnimation = newAnimation();
        Animation.players.add(second.getUniqueId().toString());

        Assertions.assertTrue(
                firstAnimation.has(first),
                "constructing an animation for a second player must not evict the first player");
        Assertions.assertTrue(secondAnimation.has(second), "second player should be tracked while opening");
    }

    @Test
    public void reentrancyGuardSurvivesSpammedOpenForSamePlayer() {
        Player player = server.addPlayer();

        newAnimation();
        Animation.players.add(player.getUniqueId().toString());

        // A spammed re-open builds a fresh Animation; the player must still read as mid-animation.
        Csgo spam = newAnimation();
        Assertions.assertFalse(spam.isDisabled(player), "a spammed re-open must still see the player as mid-animation");
        Assertions.assertTrue(spam.has(player));
    }

    @Test
    public void clearTrackedFreesEveryPlayer() {
        Player a = server.addPlayer();
        Player b = server.addPlayer();

        Csgo animation = newAnimation();
        Animation.players.add(a.getUniqueId().toString());
        Animation.players.add(b.getUniqueId().toString());

        // clearTracked() must release everyone, or a reload leaves players flagged mid-animation
        Animation.clearTracked();

        Assertions.assertFalse(animation.has(a));
        Assertions.assertFalse(animation.has(b));
    }

    @Test
    public void staleEntryDoesNotLockPlayerOut() {
        Player player = server.addPlayer();
        StubAnimation animation = new StubAnimation();

        // Leaked entry while the player isn't viewing an animation: the open must recover, not reject forever
        Animation.players.add(player.getUniqueId().toString());

        Inventory opened = animation.open(player, Mockito.mock(Location.class));
        Assertions.assertNotNull(opened, "a stale registry entry must not lock the player out");
    }

    @Test
    public void activeAnimationBlocksReopen() {
        Player player = server.addPlayer();
        StubAnimation animation = new StubAnimation();

        Assertions.assertNotNull(animation.open(player, Mockito.mock(Location.class)));

        // The player is now viewing the animation inventory, so a rapid re-open must be rejected.
        Assertions.assertNull(
                animation.open(player, Mockito.mock(Location.class)),
                "re-open must be rejected while the player is viewing the animation");
    }

    /** Minimal concrete animation that opens a real {@link AnimationHolder} inventory for the player. */
    private static final class StubAnimation extends Animation {
        StubAnimation() {
            super(new CrateImpl("regression-crate", CrateType.KEY), settings());
        }

        @Override
        public void setDefault() {}

        @Override
        protected Inventory openCrate(Player player, Location location) {
            AnimationHolder holder =
                    new AnimationHolder(this, player, Collections.emptyList(), Collections.emptyList());
            Inventory inventory = Bukkit.createInventory(holder, 9);
            player.openInventory(inventory);
            return inventory;
        }

        @Override
        public void onDisable(List<Reward> rewards, List<Reward> constant, Player player) {}

        @Override
        public void onEnd(Player player, Location location, Reward reward, Inventory inventory) {}

        @Override
        public void onEnd(Player player, Location location, List<Reward> reward, Inventory inventory) {}

        @Override
        public AnimationTask task(Inventory inventory, Player player, List<Reward> rewards, Location location) {
            return null;
        }

        @Override
        public AnimationTask task(AnimationTask task) {
            return null;
        }
    }
}
