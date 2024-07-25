package org.stevefal.megarandomizer.blockloot.condition;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

import java.util.Set;

public class BlockDropSourceCondition implements LootItemCondition {
    public LootItemConditionType getType() {
        return LootItemConditions.BLOCK_STATE_PROPERTY;
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

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<BlockDropSourceCondition> {

        public void serialize(JsonObject jsonObject, BlockDropSourceCondition blockDropSourceCondition, JsonSerializationContext jsonSerializationContext) {
        }

        public BlockDropSourceCondition deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return new BlockDropSourceCondition();
        }
    }
}
