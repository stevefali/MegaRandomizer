package org.stevefal.megarandomizer.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.WorldData;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import org.apache.commons.lang3.ObjectUtils;
import org.stevefal.megarandomizer.MegaRandomizer;
import org.stevefal.megarandomizer.commands.ReshuffleCommand;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.megadrops.RandomDrops;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = MegaRandomizer.MODID)
public class ModEvents {

    // Setup and Shuffle the drops list when the server is ready
    @SubscribeEvent
    public static void onServerReady(ServerStartedEvent event) {
        final WorldData worldData = event.getServer().getWorldData();
        final GameRules gameRules = worldData.getGameRules();
        final boolean excludeCreativeItems = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDECREATIVEITEMS);
        final boolean excludeSpawnEggs = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDESPAWNEGGS);
        final boolean excludeHeads = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDEHEADS);
        RandomDrops.shuffleItems(worldData.worldGenOptions().seed(), excludeCreativeItems, excludeSpawnEggs, excludeHeads);
    }

    // Randomize entity drops
    @SubscribeEvent
    public static void onEntityDrop(LivingDropsEvent event) {
        Level lev = event.getEntity().level();
        LivingEntity ent = event.getEntity();
        if (!lev.isClientSide) {
            if (ent instanceof Player) {
                if (lev.getGameRules().getBoolean(MegaGameRules.RULE_DOPLAYERRANDOMDROPS)) {
                    randomizeEntityDrops(event, lev, ent);
                }
            } else {
                if (lev.getGameRules().getBoolean(MegaGameRules.RULE_DOENTITYRANDOMDROPS)) {
                    randomizeEntityDrops(event, lev, ent);
                }
            }
        }
    }

    private static void randomizeEntityDrops(LivingDropsEvent event, Level level, LivingEntity ent) {
        ArrayList<ItemEntity> randomizedDrops = new ArrayList<>();
        event.getDrops().forEach(vanillaDrops -> {
            for (int i = 0; i < vanillaDrops.getItem().getCount(); i++) {
                randomizedDrops.add(new ItemEntity(level, ent.getX(), ent.getY(), ent.getZ(), RandomDrops.getRandomizedItem(vanillaDrops.getItem())));
            }
        });
        event.getDrops().clear();
        event.getDrops().addAll(randomizedDrops);
    }

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new ReshuffleCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
