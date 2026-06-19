package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ExampleModClient implements ClientModInitializer {

    private static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.examplemod.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT, 
                "category.examplemod.cheats"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                MinecraftClient.getInstance().setScreen(new InsideGuiScreen());
            }
        });
    }

    public static class InsideGuiScreen extends Screen {
        public InsideGuiScreen() {
            super(Text.literal("My Custom Menu"));
        }

        @Override
        protected void init() {
            // Используем гарантированные методы для создания кнопки без привязки к точным размерам экрана
            int buttonWidth = 200;
            int buttonHeight = 20;
            int x = (this.width - buttonWidth) / 2;
            int y = (this.height - buttonHeight) / 2;

            this.addDrawableChild(ButtonWidget.builder(Text.literal("Test Button"), button -> {
                if (MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().player.sendMessage(Text.literal("Кнопка работает!"), false);
                }
            }).dimensions(x, y, buttonWidth, buttonHeight).build());
        }
    }
}
