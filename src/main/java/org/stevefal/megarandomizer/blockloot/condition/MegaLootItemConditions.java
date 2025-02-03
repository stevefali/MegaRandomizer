package org.stevefal.megarandomizer.blockloot.condition;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class MegaLootItemConditions {
    public static LootItemConditionType BLOCK_DROP_SOURCE;


    public static void register() {

        BLOCK_DROP_SOURCE = Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE,
                ResourceLocation.withDefaultNamespace("block_drop_source"),
                new LootItemConditionType(BlockDropSourceCondition.CODEC));
    }
}
