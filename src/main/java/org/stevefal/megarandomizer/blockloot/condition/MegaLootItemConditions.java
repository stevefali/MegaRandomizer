package org.stevefal.megarandomizer.blockloot.condition;

import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

public class MegaLootItemConditions {
    public static LootItemConditionType BLOCK_DROP_SOURCE;


    public static void register() {
        BLOCK_DROP_SOURCE = LootItemConditions.register("block_drop_source", new BlockDropSourceCondition.Serializer());
    }
}
