package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

public class ExampleMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // Регистрируем команду /cheat прямо через серверный обработчик
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("cheat")
                .executes(context -> {
                    // Этот текст увидят все, когда ты введешь команду
                    context.getSource().sendMessage(Text.literal("§c[Cheat] §aФункция успешно активирована!"));
                    return 1;
                })
            );
        });
    }
}
