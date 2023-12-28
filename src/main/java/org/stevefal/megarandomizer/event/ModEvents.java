package org.stevefal.megarandomizer.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.stevefal.megarandomizer.MegaRandomizer;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.megadrops.RandomDrops;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = MegaRandomizer.MODID)
public class ModEvents {

    // Setup and Shuffle the drops list when the server is ready
    @SubscribeEvent
    public static void onServerReady(ServerStartedEvent event) {
        RandomDrops.shuffleItems(event.getServer().getWorldData().worldGenOptions().seed());
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

}
