package com.hazebyte.crate.cratereloaded.model;

import com.hazebyte.crate.api.crate.AnimationType;
import com.hazebyte.crate.api.crate.CrateType;
import com.hazebyte.crate.api.crate.EndAnimationType;
import com.hazebyte.crate.api.effect.Category;
import com.hazebyte.crate.cratereloaded.crate.animation.Animation;
import java.util.*;
import javax.annotation.Nullable;
import lombok.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CrateV2 {

    @NonNull
    private String crateName;

    @NonNull
    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    @NonNull
    @Builder.Default
    private Optional<String> displayName = Optional.empty();

    @NonNull
    @Builder.Default
    private Optional<ItemStack> displayItem = Optional.empty();

    @NonNull
    @Builder.Default
    private AnimationType animationType = AnimationType.NONE;

    @NonNull
    @Builder.Default
    private EndAnimationType endAnimationType = EndAnimationType.BLANK;

    @Nullable
    private Animation animation;

    @NonNull
    private CrateType type;

    @NonNull
    @Builder.Default
    private ItemStack item = new ItemStack(Material.CHEST);

    @Builder.Default
    private double salePrice = 0;

    @Builder.Default
    private boolean forSale = false;

    @NonNull
    @Builder.Default
    private List<String> openMessage = Collections.emptyList();

    @NonNull
    @Builder.Default
    private List<String> broadcastMessage = Collections.emptyList();

    private boolean previewable;

    private int previewRows;

    private boolean confirmBeforeUse;

    private ItemStack acceptButton;

    private ItemStack declineButton;

    @NonNull
    @Builder.Default
    private int minimumRewards = 1;

    @NonNull
    @Builder.Default
    private int maximumRewards = 1;

    @NonNull
    @Builder.Default
    private List<RewardV2> rewards = Collections.emptyList();

    @NonNull
    @Builder.Default
    private List<RewardV2> constantRewards = Collections.emptyList();

    @NonNull
    @Builder.Default
    private List<String> holographicText = Collections.emptyList();

    /**
     * Maps effect categories to effect configuration keys.
     * Example: {PERSISTENT: ["particle1", "halo"], OPEN: ["explosion"]}
     * This is configuration data that will be resolved by EffectResolverComponent.
     */
    @NonNull
    @Builder.Default
    private Map<Category, List<String>> effectCategoryToId = new HashMap<>();
}
