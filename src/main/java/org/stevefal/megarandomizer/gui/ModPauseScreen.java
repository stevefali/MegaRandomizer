package org.stevefal.megarandomizer.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.Component;
import org.stevefal.megarandomizer.networking.MegaMessages;
import org.stevefal.megarandomizer.networking.packets.toserver.RequestGameRulesSyncC2SPacket;
import org.stevefal.megarandomizer.networking.packets.toserver.RequestTrackerDataSyncC2SPacket;


public class ModPauseScreen extends PauseScreen {

    private static final int BUTTON_WIDTH_FULL = 204;
    private static final int BUTTON_WIDTH_HALF = 98;
    private static final Component MEGA_RANDOMIZER_MENU = Component.translatable("menu.megarandomoptions");
    private final boolean showPauseMenu;
    private final boolean isSinglePlayer;
    private final long seed;

    private GridLayout modGridLayout;

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
//                        ((Button) renderable).setY(this.height / 4 + 168 + 3);
                        ((Button) renderable).setY(this.height / 4 + 144 + 3);
                    } else {
                        ((Button) renderable).setY(((Button) renderable).getY() - 24);
                    }
                } else {
                    if (renderable instanceof StringWidget title) {
                        if (title.getMessage().equals(this.title)) {
                            title.setY(title.getY() - 24);
                        }
                    }
                }
//                if (renderable instanceof GridLayout grid) {
//                    modGridLayout = grid;
//                }
            });


//            if (modGridLayout != null) {
//                System.out.println("Using grid layout!");
//                GridLayout.RowHelper modRowHelper = modGridLayout.createRowHelper(2);
//                Button megaRandomButton = modRowHelper.addChild(Button.builder(MEGA_RANDOMIZER_MENU, (button) -> {
//                    MegaMessages.sendToServer(new RequestGameRulesSyncC2SPacket());
//                    minecraft.setScreen(new MegaRandomOptionsScreen(this, this.minecraft.level, true, seed));
//                }).width(BUTTON_WIDTH_FULL).build(), 1);
//
//                this.addRenderableWidget(megaRandomButton);
//                megaRandomButton.setY(this.height / 4 + 120 + 3);
//
//                Button megaTrackerButton = modRowHelper.addChild(Button.builder(Component.literal("Mega Randomizer Tracker"), (button) -> {
//                    minecraft.setScreen(new MegaTrackerScreen(Component.literal("Item Drops tracker")));
//                }).width(BUTTON_WIDTH_FULL).build(), 1);
//
//                megaTrackerButton.setY(this.height / 4 + 120 + 3);
//
//                modGridLayout.arrangeElements();
//
//            }

            /*Button megaRandomButton = Button.builder(MEGA_RANDOMIZER_MENU, (button) -> {
                MegaMessages.sendToServer(new RequestGameRulesSyncC2SPacket());
                minecraft.setScreen(new MegaRandomOptionsScreen(this, this.minecraft.level, true, seed));
            }).width(BUTTON_WIDTH_HALF).build();

            this.addRenderableWidget(megaRandomButton);
            megaRandomButton.setPosition(this.width / 2 - 102, this.height / 4 + 120 + 3);

            Button megaTrackerButton = Button.builder(Component.literal("Mega Randomizer Tracker"), (button) -> {
                minecraft.setScreen(new MegaTrackerScreen(Component.literal("Item Drops tracker")));
            }).width(BUTTON_WIDTH_HALF).build();

            this.addRenderableWidget(megaTrackerButton);
            megaTrackerButton.setPosition(this.width / 2 -102 + (BUTTON_WIDTH_FULL / 2) + 8, this.height / 4 + 120 + 3);*/


            Button megaRandomButton = Button.builder(
                    MEGA_RANDOMIZER_MENU, (button) -> {
                        MegaMessages.sendToServer(new RequestGameRulesSyncC2SPacket());
                        minecraft.setScreen(new MegaRandomOptionsScreen(this, this.minecraft.level, true, seed));
                    }
            ).width(BUTTON_WIDTH_FULL).build();

            this.addRenderableWidget(megaRandomButton);
            megaRandomButton.setPosition(this.width / 2 - 102, this.height / 4 + 96 + 3);

            Button megaTrackerButton = Button.builder(
                    Component.literal("Mega Randomizer Tracker"), (button) -> {
                        MegaMessages.sendToServer(new RequestTrackerDataSyncC2SPacket());
                        minecraft.setScreen(new MegaTrackerScreen(Component.literal("Item Drops tracker")));
                    }
            ).width(BUTTON_WIDTH_FULL).build();

            this.addRenderableWidget(megaTrackerButton);
            megaTrackerButton.setPosition(this.width / 2 - 102, this.height / 4 + 120 + 3);

        }
    }
}

