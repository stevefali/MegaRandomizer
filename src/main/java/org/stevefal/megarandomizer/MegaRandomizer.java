package org.stevefal.megarandomizer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.stevefal.megarandomizer.blockloot.MegaLootModifiers;
import org.stevefal.megarandomizer.blockloot.condition.MegaLootItemConditions;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.networking.MegaMessages;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MegaRandomizer.MODID)
public class MegaRandomizer {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "mega_randomizer";


    public MegaRandomizer(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        // Register the block loot modifier
        MegaLootModifiers.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        // Register network messages
        MegaMessages.register();

        // Register Mega GameRules
        MegaGameRules.register();

        // Register LootItemConditions
        MegaLootItemConditions.register();
    }
}
