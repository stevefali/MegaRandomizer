package org.stevefal.megarandomizer.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.WorldData;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import org.stevefal.megarandomizer.MegaRandomizer;
import org.stevefal.megarandomizer.commands.ReshuffleCommand;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.megadrops.RandomDrops;
import org.stevefal.megarandomizer.networking.MegaMessages;
import org.stevefal.megarandomizer.networking.packets.GameRulesSyncS2CPacket;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = MegaRandomizer.MODID)
public class ServerEvents {

    /**
     * Setup and Shuffle the drops list when the server is ready
     */
    @SubscribeEvent
    public static void onServerReady(ServerStartedEvent event) {
        final WorldData worldData = event.getServer().getWorldData();
        final GameRules gameRules = worldData.getGameRules();
        final boolean excludeCreativeItems = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDE_CREATIVEITEMS);
        final boolean excludeSpawnEggs = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDE_SPAWNEGGS);
        final boolean excludeHeads = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDE_HEADS);
        RandomDrops.shuffleItems(worldData.worldGenOptions().seed(), excludeCreativeItems, excludeSpawnEggs, excludeHeads);
    }

    /**
     * Initialize client side MegaGameRule values
     */
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        if (player.serverLevel().players().size() < 2) {
            GameRules gameRules = player.getServer().getGameRules();
            MegaMessages.sendToPlayer(new GameRulesSyncS2CPacket(gameRules.getBoolean(MegaGameRules.RULE_DO_BLOCK_RANDOMDROPS),
                    gameRules.getBoolean(MegaGameRules.RULE_DO_ENTITY_RANDOMDROPS),
                    gameRules.getBoolean(MegaGameRules.RULE_DO_PLAYER_RANDOMDROPS),
                    gameRules.getBoolean(MegaGameRules.RULE_EXCLUDE_CREATIVEITEMS),
                    gameRules.getBoolean(MegaGameRules.RULE_EXCLUDE_SPAWNEGGS),
                    gameRules.getBoolean(MegaGameRules.RULE_EXCLUDE_HEADS)), player);
        }
    }

    /**
     * Randomize entity drops
     */
    @SubscribeEvent
    public static void onEntityDrop(LivingDropsEvent event) {
        Level lev = event.getEntity().level();
        LivingEntity ent = event.getEntity();
        if (!lev.isClientSide) {
            if (ent instanceof Player) {
                if (lev.getServer().getGameRules().getBoolean(MegaGameRules.RULE_DO_PLAYER_RANDOMDROPS)) {
                    randomizeEntityDrops(event, lev, ent);
                }
            } else {
                if (lev.getServer().getGameRules().getBoolean(MegaGameRules.RULE_DO_ENTITY_RANDOMDROPS)) {
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
