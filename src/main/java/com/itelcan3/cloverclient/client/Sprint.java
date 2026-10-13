package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Sprint {

    public static boolean enabled = false;
    
    private static final float VANILLA_FLY_SPEED = 0.05f;
    private static final float FAST_FLY_SPEED = 0.20f;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!enabled || event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.theWorld == null) return;

        if (mc.gameSettings.keyBindForward.isKeyDown() && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
        }

        if (mc.thePlayer.capabilities.isCreativeMode || mc.thePlayer.isSpectator()) {
            if (mc.thePlayer.capabilities.isFlying) {
                // Se stai premendo lo sprint mentre voli, aumenta la velocità
                if (mc.gameSettings.keyBindSprint.isKeyDown() || mc.thePlayer.isSprinting()) {
                    mc.thePlayer.capabilities.setFlySpeed(FAST_FLY_SPEED);
                } else {
                    mc.thePlayer.capabilities.setFlySpeed(VANILLA_FLY_SPEED);
                }
            } else {
                mc.thePlayer.capabilities.setFlySpeed(VANILLA_FLY_SPEED);
            }
        }
    }
}
