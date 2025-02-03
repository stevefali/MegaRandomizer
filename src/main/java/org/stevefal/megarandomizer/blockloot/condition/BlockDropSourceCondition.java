package org.stevefal.megarandomizer.blockloot.condition;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

import java.util.Set;

public record BlockDropSourceCondition(Block block) implements LootItemCondition {

    public static final MapCodec<BlockDropSourceCondition> CODEC = RecordCodecBuilder.mapCodec((builderInst) -> {
        return builderInst.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("value")
                .forGetter(BlockDropSourceCondition::block)).apply(builderInst, BlockDropSourceCondition::new);
    });

    public LootItemConditionType getType() {
        return LootItemConditions.BLOCK_STATE_PROPERTY;
    }

    public BlockDropSourceCondition(Block block) {
        this.block = null;
    }

    /**
     * Get the parameters used by this object.
     */
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootContextParams.BLOCK_STATE);
    }

    public boolean test(LootContext lootContext) {
        BlockState blockstate = lootContext.getParamOrNull(LootContextParams.BLOCK_STATE);
        // Check if the drops are from a block
        if (blockstate != null) {
            return true;
        } else {
            return false;
        }
    }
}
