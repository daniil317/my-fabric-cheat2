package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
    
    public static final String MOD_ID = "modid";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static KeyBinding openMenuKey;

    @Override
    public void onInitialize() {
        LOGGER.info("Mod initialized.");

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
