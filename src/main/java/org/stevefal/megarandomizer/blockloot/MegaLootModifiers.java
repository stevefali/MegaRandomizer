package org.stevefal.megarandomizer.blockloot;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.stevefal.megarandomizer.MegaRandomizer;

public class MegaLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MegaRandomizer.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> RANDOMIZE_BLOCK_DROPS =
            LOOT_MODIFIER_SERIALIZERS.register("randomize_block_drops", BlockDropsModifier.BLOCK_CODEC);


    public static void register(IEventBus bus) {
        LOOT_MODIFIER_SERIALIZERS.register(bus);
    }
}
