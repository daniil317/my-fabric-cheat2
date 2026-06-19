package com.example.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CustomGuiScreen extends Screen {
    public CustomGuiScreen() {
        super(Text.literal("My Custom Menu"));
    }

    @Override
    protected void init() {
        // Добавляем тестовую кнопку по центру экрана
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Test Button"), button -> {
            if (this.client != null && this.client.player != null) {
                this.client.player.sendMessage(Text.literal("Кнопка работает!"), false);
            }
        }).dimensions(this.width / 2 - 100, this.height / 2 - 10, 200, 20).build());
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
