package org.stevefal.megarandomizer.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.stevefal.megarandomizer.MegaRandomizer;
import org.stevefal.megarandomizer.gui.ModPauseScreen;

@OnlyIn(Dist.CLIENT)
public class ClientEvents {

    @Mod.EventBusSubscriber(modid = MegaRandomizer.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        // Intercept the stock PauseScreen and instead display ModPauseScreen
        @SubscribeEvent
        public static void onPauseMenuTriggered(ScreenEvent.Init event) {
            final Minecraft minecraft = Minecraft.getInstance();
            boolean isSinglePlayer = minecraft.isSingleplayer();
            if (event.getScreen() instanceof PauseScreen && !(event.getScreen() instanceof ModPauseScreen)) {
                long seed = 0;
                if(minecraft.hasSingleplayerServer() && minecraft.getSingleplayerServer() != null) {
                    seed = minecraft.getSingleplayerServer().getWorldData().worldGenOptions().seed();
                } else if(minecraft.level != null && minecraft.level.getServer() != null) {
                    seed = minecraft.level.getServer().getWorldData().worldGenOptions().seed();
                } else if (minecraft.player != null && minecraft.player.clientLevel.getServer() != null){
                    seed = minecraft.player.clientLevel.getServer().getWorldData().worldGenOptions().seed();
                } else {
                    //do nothing because it should not get here (if it got here we have a problem)
                }
                minecraft.setScreen(new ModPauseScreen(true, isSinglePlayer, seed));
            }
        }
    }


}
