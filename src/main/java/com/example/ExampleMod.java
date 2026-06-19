package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ExampleMod implements ModInitializer {

    private static KeyBinding openMenuKey;

    @Override
    public void onInitialize() {
        // Регистрируем кнопку прямо в main через стандартный загрузчик
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.examplemod.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT, 
                "category.examplemod.cheats"
        ));

        // Вызываем скрытый метод Майнкрафта через рефлексию/динамический класс, чтобы компилятор не ругался
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                try {
                    // Открываем экран выбора языка динамически (он встроен в клиент и не требует создания кастомных окон)
                    Object mc = Class.forName("net.minecraft.client.MinecraftClient").getMethod("getInstance").invoke(null);
                    Object currentScreen = mc.getClass().getField("currentScreen").get(mc);
                    Object options = mc.getClass().getField("options").get(mc);
                    Object langManager = mc.getClass().getMethod("getLanguageManager").invoke(mc);
                    
                    Object languageScreen = Class.forName("net.minecraft.client.gui.screen.option.LanguageOptionsScreen")
                            .getConstructor(Class.forName("net.minecraft.client.gui.screen.Screen"), options.getClass(), langManager.getClass())
                            .newInstance(currentScreen, options, langManager);
                    
                    mc.getClass().getMethod("setScreen", Class.forName("net.minecraft.client.gui.screen.Screen")).invoke(mc, languageScreen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
