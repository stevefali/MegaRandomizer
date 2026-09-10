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

import java.lang.management.BufferPoolMXBean;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MegaTrackerScreen extends Screen {

    private StringWidget leftHeader;
    private StringWidget rightHeader;
    private MegaTrackerList megaTrackerListLeft;
    private Button doneButton;
    private Button modeButton;
    private boolean dropsMode = true;

    private static final Component RETURN_TO_GAME = Component.translatable("menu.returnToGame");
    private static final int ITEM_HEIGHT = 20;
    private static final int BUTTON_WIDTH_HALF = 98;


    protected MegaTrackerScreen(Component pTitle) {
        super(pTitle);
    }

    protected void init() {
        setupTrackerScreen();
    }

    private void setupTrackerScreen() {
//        GridLayout gridlayout = new GridLayout();
//        gridlayout.defaultCellSetting().padding(4, 4, 4, 2);
//        GridLayout.RowHelper gridlayoutRowHelper = gridlayout.createRowHelper(1);

//        System.out.println("****** longest width: " + this.font.width("Cracked Polished Blackstone Bricks "));
//        System.out.println("****** ➡ width: " + this.font.width("➡ "));
        this.leftHeader = new StringWidget(Component.literal("Source"), this.font);
        this.rightHeader = new StringWidget(Component.literal("Randomized Drop"), this.font);

        int rightHeaderWidth = this.font.width("Randomized Drop");

        this.addRenderableWidget(leftHeader);
        this.addRenderableWidget(rightHeader);

        leftHeader.setPosition(24, 4);
        rightHeader.setPosition(width - rightHeaderWidth - 24, 4);

        this.megaTrackerListLeft = new MegaTrackerList(
                this.minecraft,
                this.width,
                this.height,
//                32,
                ITEM_HEIGHT,
//                this.height - 36,
                this.height - 28,
                ITEM_HEIGHT
        );

        this.megaTrackerListLeft.addMegaTrackerEntry(
                new MegaTrackerEntry(
                        Component.literal("Grass Block"),
                        Component.literal("Dirt block"),
                        this.font,
                        this.width
                )
        );

        this.megaTrackerListLeft.addMegaTrackerEntry(
                new MegaTrackerEntry(
                        Component.literal("Cracked Polished Blackstone Bricks"),
                        Component.literal("Dirt block"),
                        this.font,
                        this.width
                )
        );

        this.megaTrackerListLeft.addMegaTrackerEntry(
                new MegaTrackerEntry(
                        Component.literal("Sand"),
                        Component.literal("Cracked Polished Blackstone Bricks"),
                        this.font,
                        this.width
                )
        );

        this.megaTrackerListLeft.addMegaTrackerEntry(
                new MegaTrackerEntry(
                        Component.literal("Cracked Polished Blackstone Bricks"),
                        Component.literal("Cracked Polished Blackstone Bricks"),
                        this.font,
                        this.width
                )
        );


        for (int i = 0; i < 30; i++) {

            this.megaTrackerListLeft.addMegaTrackerEntry(
//                    new MegaTrackerEntry(Component.literal("Grass Block  §a\u27A1  §rSome Drop"), this.font)
                    new MegaTrackerEntry(
                            Component.literal("Grass Block"),
                            Component.literal("Dirt block"),
                            this.font,
                            this.width
                    )
            );

//            this.stringWidget = gridlayoutRowHelper.addChild(new StringWidget(
//                    Component.literal("Grass Block"),
//                    this.font
//            ), 1);

        }


        this.addRenderableWidget(this.megaTrackerListLeft);

        doneButton = Button.builder(
                RETURN_TO_GAME, (button) -> {
                    this.minecraft.setScreen((Screen) null);
                    this.minecraft.mouseHandler.grabMouse();
                }
        ).width(BUTTON_WIDTH_HALF).build();

        this.addRenderableWidget(doneButton);
        doneButton.setPosition(this.width / 2 - 102, this.height - Button.DEFAULT_HEIGHT - 4);

        modeButton = Button.builder(
                Component.literal("Tracker Mode"), button -> {
                    toggleDropsMode();
                }
        ).width(BUTTON_WIDTH_HALF).build();


//        gridlayoutRowHelper.addChild(
//                Button.builder(
//                        RETURN_TO_GAME, (p_280814_) -> {
//                            this.minecraft.setScreen((Screen) null);
//                            this.minecraft.mouseHandler.grabMouse();
//                        }
//                ).width(204).build(), 2, gridlayout.newCellSettings().paddingTop(50)
//        );


//        gridlayout.arrangeElements();
//        FrameLayout.alignInRectangle(gridlayout, 0, 0, this.width, this.height, 0.5F, 0.25F);
//        gridlayout.visitWidgets(this::addRenderableWidget);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    private void toggleDropsMode() {
        dropsMode = !dropsMode;
    }


    class MegaTrackerList extends ObjectSelectionList<MegaTrackerEntry> {

        public MegaTrackerList(Minecraft minecraft, int width, int height, int y0, int y1, int itemHeight) {
            super(minecraft, width, height, y0, y1, itemHeight);
        }

        public void addMegaTrackerEntry(MegaTrackerEntry megaTrackerEntry) {
            this.addEntry(megaTrackerEntry);
        }

        @Override
        public int getRowWidth() {
            return this.width - 20;
        }

        @Override
        protected int getScrollbarPosition() {
//            return this.width / 2 + this.getRowWidth() / 2;
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
//        public MegaTrackerEntry(Component queryText, Font entryFont) {
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
            int arrowX = (width / 2) - 6;
            int resultTextWidth = entryFont.width(resultText.getString());
            int resultTextX = left + width - resultTextWidth - 14;
//            int resultTextX = arrowX + 12;


            if (hovering) {
//                guiGraphics.fill(left, top, left + width, top + height, 0x26FFFFFF);
                guiGraphics.fill(0, top, parentWidth, top + height, 0x26FFFFFF);
            }
            if (index % 2 == 1) {
//                guiGraphics.fill(left, top, left + width, top + height, 0x44000000);
//                guiGraphics.fill(left, top, left + width, top + height, 0x10FFFFFF);
                guiGraphics.fill(0, top, parentWidth, top + height, 0x10FFFFFF);
            } /*else {
                guiGraphics.fill(left, top, left + width, top + height, 0x10FFFFFF);
            }
*/

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
