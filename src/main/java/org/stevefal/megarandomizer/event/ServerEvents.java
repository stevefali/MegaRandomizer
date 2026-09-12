package org.stevefal.megarandomizer.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.WorldData;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.stevefal.megarandomizer.MegaRandomizer;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.megadata.MegaSavedDataAccess;
import org.stevefal.megarandomizer.megadrops.RandomDrops;
import org.stevefal.megarandomizer.megamobs.IMegaMob;
import org.stevefal.megarandomizer.megamobs.RandomSpawns;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = MegaRandomizer.MODID)
public class ServerEvents {

    // Setup and Shuffle the drops list when the server is ready
    @SubscribeEvent
    public static void onServerReady(ServerStartedEvent event) {
        MegaSavedDataAccess.initializeMegaSavedData(event.getServer());

        final WorldData worldData = event.getServer().getWorldData();
        final GameRules gameRules = worldData.getGameRules();
        final boolean excludeCreativeItems = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDECREATIVEITEMS);
        final boolean excludeSpawnEggs = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDESPAWNEGGS);
        final boolean excludeHeads = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDEHEADS);
        final boolean excludeBosses = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDE_BOSSES);
        RandomDrops.shuffleItems(
                worldData.worldGenOptions().seed(),
                excludeCreativeItems,
                excludeSpawnEggs,
                excludeHeads
        );
        RandomSpawns.shuffleEntities(worldData.worldGenOptions().seed(), excludeBosses);
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
                randomizedDrops.add(new ItemEntity(
                        level, ent.getX(), ent.getY(), ent.getZ(), RandomDrops.getRandomizedItem(
                        vanillaDrops.getItem(),
                        level.getServer().getGameRules().getBoolean(MegaGameRules.RULE_DO_VOLATILE_DROPS)
                )
                ));
            }
        });
        event.getDrops().clear();
        event.getDrops().addAll(randomizedDrops);
    }

    @SubscribeEvent
    public static void onVanillaMobSpawn(MobSpawnEvent.FinalizeSpawn event) {
        if (event.getEntity().getServer().getGameRules().getBoolean(MegaGameRules.RULE_DO_RANDOM_SPAWNS)) {

            if (event.getSpawnType() != MobSpawnType.SPAWN_EGG) {
                Entity vanillaEntity = event.getEntity();
                ServerLevel level = event.getLevel().getLevel();

                EntityType<?> randomizedEntityType = RandomSpawns.getRandomizedEntityType(vanillaEntity.getType());
                Entity randomizedEntity = randomizedEntityType.create(level);

                if (randomizedEntity != null) {
                    event.setSpawnCancelled(true);
                    vanillaEntity.discard();
                    event.setCanceled(true);

                    randomizedEntity.setPos(event.getX(), event.getY(), event.getZ());

                    level.addFreshEntity(randomizedEntity);
                    ((IMegaMob) randomizedEntity).setMegaMobIsPersistenceRequired(false);
                }
            }
        }
    }

}
