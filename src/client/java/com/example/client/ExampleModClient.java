package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
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
        // Кнопка на Правый Шифт
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.examplemod.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT, 
                "category.examplemod.cheats"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openMenuKey.wasPressed() && client.player != null) {
                client.setScreen(new InsideGuiScreen());
            }
        });
    }

    // Меню прямо внутри этого же файла, чтобы компилятор не терял классы
    public static class InsideGuiScreen extends Screen {
        public InsideGuiScreen() {
            super(Text.literal("My Custom Menu"));
        }

        @Override
        protected void init() {
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
}
