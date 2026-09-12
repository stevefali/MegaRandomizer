package org.stevefal.megarandomizer.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.stevefal.megarandomizer.megadata.clientdata.MegaTrackerClientData;

import java.util.Map;


@OnlyIn(Dist.CLIENT)
public class MegaTrackerScreen extends Screen {

    private StringWidget leftHeader;
    private StringWidget rightHeader;
    private StringWidget volatileWarningWidget;
    private StringWidget loadingWidget;
    private MegaTrackerList megaTrackerList;
    private Button doneButton;
    private Button modeButton;
    private boolean dropsMode = true;

    private static final Component RETURN_TO_GAME = Component.translatable("menu.returnToGame");
    private static final int ITEM_HEIGHT = 20;
    private static final int BUTTON_WIDTH_HALF = 98;

    private static final Component VANILLA_DROP = Component.translatable("menu.megatracker.vanilla_drop").withStyle(
            ChatFormatting.BOLD);
    private static final Component VANILLA_MOB = Component.translatable("menu.megatracker.vanilla_mob").withStyle(
            ChatFormatting.BOLD);
    private static final Component RANDOMIZED_DROP = Component.translatable("menu.megatracker.randomized_drop").withStyle(
            ChatFormatting.BOLD);
    private static final Component RANDOMIZED_MOB = Component.translatable("menu.megatracker.randomized_mob").withStyle(
            ChatFormatting.BOLD);
    private static final Component SHOW_MOBS = Component.translatable("menu.megatracker.show_mobs");
    private static final Component SHOW_DROPS = Component.translatable("menu.megatracker.show_drops");
    private static final Component VOLATILE_WARNING = Component.translatable("menu.megatracker.volatile_warning").withStyle(
            ChatFormatting.RED);
    private static final Component LOADING = Component.translatable("menu.megatracker.loading");


    public MegaTrackerScreen() {
        super(Component.translatable("menu.megatracker"));
    }

    protected void init() {
        this.megaTrackerList = new MegaTrackerList(
                this.minecraft,
                this.width,
                this.height - 51,
                ITEM_HEIGHT,
                ITEM_HEIGHT
        );

        this.volatileWarningWidget = new StringWidget(VOLATILE_WARNING, this.font);
        this.loadingWidget = new StringWidget(LOADING, this.font);

        volatileWarningWidget.setPosition(this.width / 2 - (this.font.width(VOLATILE_WARNING) / 2), 6);
        loadingWidget.setPosition(this.width / 2 - (this.font.width(LOADING) / 2), this.height / 2);

        setupTrackerScreen();
    }

    private void setupTrackerScreen() {
        this.addRenderableWidget(this.megaTrackerList);
        renderList();

        this.leftHeader = new StringWidget(getLeftHeaderComponent(), this.font);
        this.rightHeader = new StringWidget(getRightHeaderComponent(), this.font);

        int rightHeaderWidth = this.font.width(getRightHeaderComponent());

        this.addRenderableWidget(leftHeader);
        this.addRenderableWidget(rightHeader);

        leftHeader.setPosition(20, 6);
        rightHeader.setPosition(width - rightHeaderWidth - 24, 6);


        doneButton = Button.builder(
                RETURN_TO_GAME, (button) -> {
                    this.minecraft.setScreen(null);
                    this.minecraft.mouseHandler.grabMouse();
                }
        ).width(BUTTON_WIDTH_HALF).build();

        this.addRenderableWidget(doneButton);
        doneButton.setPosition(this.width / 2 - 102, this.height - Button.DEFAULT_HEIGHT - 4);

        modeButton = Button.builder(
                getModeButtonComponent(), button -> {
                    toggleDropsMode();
                    renderList();
                    megaTrackerList.setScrollAmount(0);
                }
        ).width(BUTTON_WIDTH_HALF).build();

        this.addRenderableWidget(modeButton);
        modeButton.setPosition(this.width / 2 + 4, this.height - Button.DEFAULT_HEIGHT - 4);

    }


    private void renderList() {

        this.megaTrackerList.clearMegaTrackerEntries();
        this.removeWidget(volatileWarningWidget);

        if (getListData().isEmpty()) {
            this.addRenderableWidget(loadingWidget);
        } else {

            this.removeWidget(loadingWidget);
            if (this.dropsMode && MegaTrackerClientData.getIsDoVolatile()) {
                this.addRenderableWidget(volatileWarningWidget);
            }

            for (Map.Entry<String, String> entry : getListData().entrySet()) {
                this.megaTrackerList.addMegaTrackerEntry(
                        new MegaTrackerEntry(
                                Component.literal(entry.getKey()),
                                Component.literal(entry.getValue()),
                                this.font,
                                this.width
                        )
                );
            }

        }
    }

    private Component getLeftHeaderComponent() {
        return dropsMode ? VANILLA_DROP : VANILLA_MOB;
    }

    private Component getRightHeaderComponent() {
        return dropsMode ? RANDOMIZED_DROP : RANDOMIZED_MOB;
    }

    private Component getModeButtonComponent() {
        return dropsMode ? SHOW_MOBS : SHOW_DROPS;
    }

    private Map<String, String> getListData() {
        return this.dropsMode ? MegaTrackerClientData.getDiscoveredDrops() : MegaTrackerClientData.getDiscoveredSpawns();
    }

    @Override
    public void tick() {
        super.tick();
        leftHeader.setMessage(getLeftHeaderComponent());
        rightHeader.setMessage(getRightHeaderComponent());
        modeButton.setMessage(getModeButtonComponent());
        renderList();

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    private void toggleDropsMode() {
        dropsMode = !dropsMode;
    }


    class MegaTrackerList extends ObjectSelectionList<MegaTrackerEntry> {

        public MegaTrackerList(Minecraft minecraft, int width, int height, int y, int itemHeight) {
            super(minecraft, width, height, y, itemHeight);
        }

        public void addMegaTrackerEntry(MegaTrackerEntry megaTrackerEntry) {
            this.addEntry(megaTrackerEntry);
        }


        public void clearMegaTrackerEntries() {
            this.clearEntries();
        }

        @Override
        public int getRowWidth() {
            return this.width - 20;
        }

        @Override
        protected int getScrollbarPosition() {
            return this.width - 8;
        }
    }


    private static class MegaTrackerEntry extends ObjectSelectionList.Entry<MegaTrackerEntry> {
        private final Component queryText;
        private final Component resultText;
        private final Component arrow;
        private final Font entryFont;

        private final int parentWidth;

        public MegaTrackerEntry(Component queryText, Component resultText, Font entryFont, int parentWidth) {
            this.queryText = queryText;
            this.resultText = resultText;
            this.entryFont = entryFont;
            this.arrow = Component.literal("➡").withStyle(ChatFormatting.GREEN);
            this.parentWidth = parentWidth;
        }

        @Override
        public void render(
                GuiGraphics guiGraphics,
                int index,
                int top,
                int left,
                int width,
                int height,
                int mouseX,
                int mouseY,
                boolean hovering,
                float partialTick) {

            int textY = top + (height - 9) / 2;
            int queryTextX = left + 4;
            int arrowX = (parentWidth / 2) - 4;
            int resultTextWidth = entryFont.width(resultText.getString());
            int resultTextX = left + width - resultTextWidth - 14;

            if (hovering) {
                guiGraphics.fill(0, top, parentWidth, top + height, 0x26FFFFFF);
            }
            if (index % 2 == 1) {
                guiGraphics.fill(0, top, parentWidth, top + height, 0x10FFFFFF);
            }

            guiGraphics.drawString(entryFont, this.queryText, queryTextX, textY, 0xFFFFFF);
            guiGraphics.drawString(entryFont, this.arrow, arrowX, textY, 0xFFFFFF);
            guiGraphics.drawString(entryFont, this.resultText, resultTextX, textY, 0xFFFFFF);
        }

        @Override
        public Component getNarration() {
            return this.queryText;
        }

    }


}