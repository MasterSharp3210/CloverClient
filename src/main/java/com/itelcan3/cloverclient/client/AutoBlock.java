package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

public class AutoBlock {

    public static boolean enabled = false;

    private static boolean isSword(ItemStack stack) {
        return stack != null && stack.getItem() instanceof ItemSword;
    }

    public static boolean shouldBlock() {
        Minecraft mc = Minecraft.getMinecraft();

        if (!enabled || mc.thePlayer == null || mc.theWorld == null) {
            return false;
        }

        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            return false;
        }

        if (!isSword(mc.thePlayer.getHeldItem())) {
            return false;
        }

        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            return false;
        }

        return Mouse.isButtonDown(0) || Mouse.isButtonDown(1) || mc.thePlayer.isSwingInProgress;
    }
}