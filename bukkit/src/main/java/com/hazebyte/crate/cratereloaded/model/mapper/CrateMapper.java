package com.hazebyte.crate.cratereloaded.model.mapper;

import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.model.CrateImpl;
import com.hazebyte.crate.cratereloaded.model.CrateV2;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {RewardMapper.class, CommonMapperUtil.class})
public interface CrateMapper {

    @Mapping(target = "displayName", qualifiedByName = "wrapOptional")
    @Mapping(target = "displayItem", qualifiedByName = "wrapOptional")
    @Mapping(source = "UUID", target = "uuid")
    @Mapping(source = "cost", target = "salePrice")
    @Mapping(source = "buyable", target = "forSale")
    @Mapping(target = "rewards", qualifiedByName = "toRewardListV2")
    @Mapping(target = "constantRewards", qualifiedByName = "toRewardListV2")
    CrateV2 fromImplementation(CrateImpl crate);

    @Mapping(source = "crateName", target = "name")
    @Mapping(source = "salePrice", target = "cost")
    @Mapping(source = "forSale", target = "buyable")
    @Mapping(target = "displayItem", qualifiedByName = "unwrap")
    @Mapping(target = "displayName", qualifiedByName = "unwrap")
    CrateImpl toImplementation(CrateV2 crateV2);

    // The mapper sets the reward lists directly rather than via CrateImpl#addReward, so the
    // reward -> parent back-reference is wired here. Without it Reward#getParent is null and
    // parent-derived placeholders never resolve in display item lore.
    @AfterMapping
    default void linkRewardParents(@MappingTarget CrateImpl crate) {
        if (crate.getRewards() != null) {
            for (Reward reward : crate.getRewards()) {
                reward.setParent(crate);
            }
        }
        if (crate.getConstantRewards() != null) {
            for (Reward reward : crate.getConstantRewards()) {
                reward.setParent(crate);
            }
        }
    }
}
