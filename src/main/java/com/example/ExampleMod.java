package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer, ClientModInitializer {
    
    public static final String MOD_ID = "modid";
    public static final Logger LOGGER = LoggerFactory.LoggerFactory.getLogger(MOD_ID);
    private static KeyBinding openMenuKey;

    // Этот метод нужен для главного запуска мода
    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world! Mod initialized.");
    }

    // Этот метод отвечает за кнопку открытия меню на клиенте
    @Override
    public void onInitializeClient() {
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.examplemod.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O, 
                "category.examplemod.cheats"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openMenuKey.wasPressed() && client.player != null) {
                client.setScreen(new CustomGuiScreen());
            }
        });
    }
}
