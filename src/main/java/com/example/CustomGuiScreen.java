package com.example;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class CustomGuiScreen extends Screen {

    private final int windowWidth = 300;
    private final int windowHeight = 200;
    private int x;
    private int y;

    private enum Tab { COMBAT, MOVEMENT, VISUALS }
    private Tab currentTab = Tab.COMBAT;

    public CustomGuiScreen() {
        super(Text.literal("Custom Menu"));
    }

    @Override
    protected void init() {
        this.x = (this.width - this.windowWidth) / 2;
        this.y = (this.height - this.windowHeight) / 2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.fill(x, y, x + windowWidth, y + windowHeight, 0xED202020);
        renderTabs(context, mouseX, mouseY);
        renderTabContent(context, mouseX, mouseY);
        super.render(context, mouseX, mouseY, delta);
    }

    private void renderTabs(DrawContext context, int mouseX, int mouseY) {
        Tab[] tabs = Tab.values();
        int tabWidth = windowWidth / tabs.length;
        int tabHeight = 20;

        for (int i = 0; i < tabs.length; i++) {
            Tab tab = tabs[i];
            int tabX = x + (i * tabWidth);
            int tabY = y;

            boolean isCurrent = (tab == currentTab);
            boolean isHovered = mouseX >= tabX && mouseX < tabX + tabWidth && mouseY >= tabY && mouseY < tabY + tabHeight;

            int tabColor = isCurrent ? 0xFF3A3A3A : (isHovered ? 0xFF2A2A2A : 0xFF1A1A1A);
            context.fill(tabX, tabY, tabX + tabWidth, tabY + tabHeight, tabColor);

            int textX = tabX + (tabWidth - this.textRenderer.getWidth(tab.name())) / 2;
            int textY = tabY + (tabHeight - this.textRenderer.fontHeight) / 2;
            
            int textColor = isCurrent ? 0xFFFFFFFF : 0xFFA0A0A0;
            context.drawText(this.textRenderer, tab.name(), textX, textY, textColor, false);
        }
    }

    private void renderTabContent(DrawContext context, int mouseX, int mouseY) {
        int contentY = y + 30;
        int contentX = x + 10;

        switch (currentTab) {
            case COMBAT:
                context.drawText(this.textRenderer, "§c[Combat Modules]§r", contentX, contentY, 0xFFFFFFFF, false);
                context.drawText(this.textRenderer, "- Killaura (Not implemented)", contentX, contentY + 15, 0xFFA0A0A0, false);
                break;
            case MOVEMENT:
                context.drawText(this.textRenderer, "§a[Movement Modules]§r", contentX, contentY, 0xFFFFFFFF, false);
                context.drawText(this.textRenderer, "- Fly (Not implemented)", contentX, contentY + 15, 0xFFA0A0A0, false);
                break;
            case VISUALS:
                context.drawText(this.textRenderer, "§b[Visuals Modules]§r", contentX, contentY, 0xFFFFFFFF, false);
                context.drawText(this.textRenderer, "- ESP (Not implemented)", contentX, contentY + 15, 0xFFA0A0A0, false);
                break;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            Tab[] tabs = Tab.values();
            int tabWidth = windowWidth / tabs.length;
            int tabHeight = 20;

            if (mouseY >= y && mouseY < y + tabHeight) {
                for (int i = 0; i < tabs.length; i++) {
                    int tabX = x + (i * tabWidth);
                    if (mouseX >= tabX && mouseX < tabX + tabWidth) {
                        this.currentTab = tabs[i];
                        if (this.client != null && this.client.getSoundManager() != null) {
                            this.client.getSoundManager().play(net.minecraft.client.sound.PositionedSoundInstance.master(
                                    net.minecraft.sound.SoundEvents.UI_BUTTON_CLICK, 1.0F));
                        }
                        return true;
                    }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
        }
