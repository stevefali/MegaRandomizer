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
            boolean isSinglePlayer = Minecraft.getInstance().isSingleplayer();
            if (event.getScreen() instanceof PauseScreen) {
                Minecraft.getInstance().setScreen(new ModPauseScreen(true, isSinglePlayer));
            }
        }
    }


}
