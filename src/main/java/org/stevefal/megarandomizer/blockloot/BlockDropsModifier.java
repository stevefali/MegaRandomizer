package org.stevefal.megarandomizer.blockloot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.megadrops.RandomDrops;

import java.util.ArrayList;
import java.util.function.Supplier;


public class BlockDropsModifier extends LootModifier {

    public static final Supplier<MapCodec<BlockDropsModifier>> BLOCK_CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.mapCodec(inst -> codecStart(inst)
                    .apply(inst, BlockDropsModifier::new)));


    protected BlockDropsModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    // Called when BlockDropSourceCondition.test() returns true
    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(LootTable lootTable, ObjectArrayList<ItemStack> generatedLoot, LootContext context) {

        if (context.getLevel().getGameRules().getBoolean(MegaGameRules.RULE_DO_BLOCK_RANDOMDROPS)) {
            // Replace the loot items
            ArrayList<ItemStack> randomizedLoot = new ArrayList<>();
            generatedLoot.forEach(vanillaLootItem -> {
                randomizedLoot.add(new ItemStack(RandomDrops.getRandomizedItem(vanillaLootItem).getItem(), vanillaLootItem.getCount()));
            });
            generatedLoot.clear();
            generatedLoot.addAll(randomizedLoot);
        }

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return BLOCK_CODEC.get();
    }
}
