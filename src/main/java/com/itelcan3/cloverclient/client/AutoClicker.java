package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class AutoClicker {

    public static boolean enabled = false;

    private static final Minecraft mc = Minecraft.getMinecraft();

    private static final long CLICK_DELAY = 50L;

    private static long lastClick = 0L;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (!enabled || mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        if (!Mouse.isButtonDown(0)) {
            releaseAttack();
            return;
        }

        long now = System.currentTimeMillis();

        if (now - lastClick >= CLICK_DELAY) {
            lastClick = now;

            KeyBinding.setKeyBindState(
                    mc.gameSettings.keyBindAttack.getKeyCode(),
                    true
            );

            KeyBinding.onTick(
                    mc.gameSettings.keyBindAttack.getKeyCode()
            );
        }
    }

    private static void releaseAttack() {
        if (mc.gameSettings != null
                && mc.gameSettings.keyBindAttack != null) {

            KeyBinding.setKeyBindState(
                    mc.gameSettings.keyBindAttack.getKeyCode(),
                    false
            );
        }
    }
}