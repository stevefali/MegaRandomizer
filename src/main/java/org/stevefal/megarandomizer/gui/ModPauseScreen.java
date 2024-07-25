package org.stevefal.megarandomizer.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.Component;
import org.stevefal.megarandomizer.networking.MegaMessages;
import org.stevefal.megarandomizer.networking.packets.RequestGameRulesSyncC2SPacket;


public class ModPauseScreen extends PauseScreen {

    private static final int BUTTON_WIDTH_FULL = 204;
    private static final Component MEGA_RANDOMIZER_MENU = Component.translatable("menu.megarandomoptions");
    private final boolean showPauseMenu;
    private final boolean isSinglePlayer;
    private final long seed;

    public ModPauseScreen(boolean pShowPauseMenu, boolean isSinglePlayer, long seed) {
        super(pShowPauseMenu);
        this.showPauseMenu = pShowPauseMenu;
        this.isSinglePlayer = isSinglePlayer;
        this.seed = seed;
    }

    @Override
    protected void init() {
        super.init();
        if (this.showPauseMenu) {
            this.createModPauseMenu();
        }
    }

    private void createModPauseMenu() {

        if (this.isSinglePlayer) {
            this.renderables.forEach(renderable -> {
                if (renderable instanceof Button) {
                    if (((Button) renderable).getMessage().equals(Component.translatable("menu.returnToMenu"))) {
                        ((Button) renderable).setY(this.height / 4 + 144 + 3);
                    }
                }
            });

            Button megaRandomButton = Button.builder(MEGA_RANDOMIZER_MENU, (button) -> {
                MegaMessages.sendToServer(new RequestGameRulesSyncC2SPacket());
                minecraft.setScreen(new MegaRandomOptionsScreen(this, this.minecraft.level, true, seed));
            }).width(BUTTON_WIDTH_FULL).build();

            this.addRenderableWidget(megaRandomButton);
            megaRandomButton.setPosition(this.width / 2 - 102, this.height / 4 + 120 + 3);
        }
    }
}

