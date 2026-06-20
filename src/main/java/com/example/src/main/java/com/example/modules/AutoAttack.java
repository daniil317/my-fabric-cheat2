package com.example.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding; // Для биндов
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW; // Для отслеживания нажатий клавиш

public class AutoAttack {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    
    // Настройки
    public boolean enabled = false;      // По умолчанию выключен
    public double range = 3.0;           // Настраиваемая дистанция
    public int delay = 10;               // Задержка (в тиках)
    private int timer = 0;
    
    // Биндинг (например, клавиша R)
    private final KeyBinding toggleKey = new KeyBinding("key.autoattack.toggle", GLFW.GLFW_KEY_R, "category.myclient");

    public void onTick() {
        // Проверка нажатия клавиши (бинд)
        if (toggleKey.wasPressed()) {
            enabled = !enabled;
            // Можно добавить сообщение в чат, чтобы знать статус
        }

        if (!enabled || mc.player == null || mc.world == null) return;

        if (timer > 0) {
            timer--;
            return;
        }

        // Поиск цели с учетом твоей настраиваемой дистанции (range)
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof LivingEntity && entity != mc.player && mc.player.distanceTo(entity) <= range) {
                
                // Атака
                mc.interactionManager.attackEntity(mc.player, entity);
                mc.player.swingHand(Hand.MAIN_HAND);
                
                timer = delay;
                break; 
            }
        }
    }
                  }

