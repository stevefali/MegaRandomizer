package org.stevefal.megarandomizer.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.networking.MegaMessages;
import org.stevefal.megarandomizer.networking.packets.SetGameRulesC2SPacket;

@OnlyIn(Dist.CLIENT)
public class MegaRandomOptionsScreen extends Screen {

    private final int U_OFFSET = 182;
    private final int V_OFFSET = 24;
    private final int U_WIDTH = 15;
    private final int V_HEIGHT = 15;

    private final Screen lastScreen;

    private final Level level;

    private Button doneButton;
    private Button blocksRandomButton;
    private Button entitiesRandomButton;
    private Button playerRandomButton;


    private final boolean showMegaRandomOptions;

    private static final Component ENTITY_DROPS_ON = Component.translatable("menu.megarandomoptions.entity_drops_on");
    private static final Component ENTITY_DROPS_OFF = Component.translatable("menu.megarandomoptions.entity_drops_off");
    private static final Component BLOCK_DROPS_ON = Component.translatable("menu.megarandomoptions.block_drops_on");
    private static final Component BLOCK_DROPS_OFF = Component.translatable("menu.megarandomoptions.block_drops_off");
    private static final Component PLAYER_DROPS_ON = Component.translatable("menu.megarandomoptions.player_drops_on");
    private static final Component PLAYER_DROPS_OFF = Component.translatable("menu.megarandomoptions.player_drops_off");


    private static final Component DONE = Component.literal("Done");


    public MegaRandomOptionsScreen(Screen lastScreen, Level level, boolean showMegaRandomOptions) {
        super(Component.translatable("menu.megarandomoptions"));

        this.lastScreen = lastScreen;
        this.level = level;
        this.showMegaRandomOptions = showMegaRandomOptions;
    }


    protected void init() {
        if (this.showMegaRandomOptions) {
            this.setupMegaRandomizerMenu();
        }

    }

    private void setupMegaRandomizerMenu() {
        GridLayout gridlayout = new GridLayout();
        gridlayout.defaultCellSetting().padding(4, 4, 4, 0);
        GridLayout.RowHelper gridlayout$rowhelper = gridlayout.createRowHelper(2);


        this.blocksRandomButton = gridlayout$rowhelper.addChild(Button.builder(getBlocksComponent(), (button) -> {
            MegaMessages.sendToServer(new SetGameRulesC2SPacket(!level.getGameRules().getBoolean(MegaGameRules.RULE_DOBLOCKRANDOMDROPS),
                    level.getGameRules().getBoolean(MegaGameRules.RULE_DOENTITYRANDOMDROPS),
                    level.getGameRules().getBoolean(MegaGameRules.RULE_DOPLAYERRANDOMDROPS)));
        }).width(204).build(), 2);

        this.entitiesRandomButton = gridlayout$rowhelper.addChild(Button.builder(getEntityComponent(), (button) -> {
            MegaMessages.sendToServer(new SetGameRulesC2SPacket(level.getGameRules().getBoolean(MegaGameRules.RULE_DOBLOCKRANDOMDROPS),
                    !level.getGameRules().getBoolean(MegaGameRules.RULE_DOENTITYRANDOMDROPS),
                    level.getGameRules().getBoolean(MegaGameRules.RULE_DOPLAYERRANDOMDROPS)));
        }).width(204).build(), 2);

        this.playerRandomButton = gridlayout$rowhelper.addChild(Button.builder(getPlayerComponent(), (button) -> {
            MegaMessages.sendToServer(new SetGameRulesC2SPacket(level.getGameRules().getBoolean(MegaGameRules.RULE_DOBLOCKRANDOMDROPS),
                    level.getGameRules().getBoolean(MegaGameRules.RULE_DOENTITYRANDOMDROPS),
                    !level.getGameRules().getBoolean(MegaGameRules.RULE_DOPLAYERRANDOMDROPS)));
        }).width(204).build(), 2);



        this.doneButton = gridlayout$rowhelper.addChild(Button.builder(DONE, (button) -> {
            button.active = false;
            this.minecraft.setScreen(this.lastScreen);
        }).width(204).build(), 2);

        gridlayout.arrangeElements();
        FrameLayout.alignInRectangle(gridlayout, 0, 0, this.width, this.height, 0.5F, 0.25F);
        gridlayout.visitWidgets(this::addRenderableWidget);
    }




    private Component getBlocksComponent() {
        if (level.getGameRules().getBoolean(MegaGameRules.RULE_DOBLOCKRANDOMDROPS)) {
            return BLOCK_DROPS_ON;
        } else  {
            return BLOCK_DROPS_OFF;
        }
    }

    private Component getEntityComponent() {
        if (level.getGameRules().getBoolean(MegaGameRules.RULE_DOENTITYRANDOMDROPS)) {
            return ENTITY_DROPS_ON;
        } else {
            return ENTITY_DROPS_OFF;
        }
    }

    private Component getPlayerComponent() {
        if (level.getGameRules().getBoolean(MegaGameRules.RULE_DOPLAYERRANDOMDROPS)) {
            return PLAYER_DROPS_ON;
        } else {
            return PLAYER_DROPS_OFF;
        }
    }


    public void tick() {
        super.tick();


        blocksRandomButton.setMessage(getBlocksComponent());
        entitiesRandomButton.setMessage(getEntityComponent());
        playerRandomButton.setMessage(getPlayerComponent());
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.showMegaRandomOptions) {
            this.renderBackground(guiGraphics);
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
        if (this.showMegaRandomOptions && this.minecraft != null && this.minecraft.getReportingContext().hasDraftReport() && this.doneButton != null) {
            guiGraphics.blit(AbstractWidget.WIDGETS_LOCATION, this.doneButton.getX() + this.doneButton.getWidth() - 17, this.doneButton.getY() + 3, U_OFFSET, V_OFFSET, U_WIDTH, V_HEIGHT);
        }
    }


}
