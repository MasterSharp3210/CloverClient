package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class FastPlace {

    public static boolean enabled = false;
    public static int delay = 0;

    private static final Field rightClickDelayField = ReflectionHelper.findField(
            Minecraft.class,
            "rightClickDelayTimer", "field_71467_ac"
    );

    static {
        if (rightClickDelayField != null) {
            rightClickDelayField.setAccessible(true);
        }
    }

    public static void toggle() {
        enabled = !enabled;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START || !enabled) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.theWorld == null) return;

        ItemStack heldItem = mc.thePlayer.getHeldItem();
        if (heldItem != null && heldItem.getItem() instanceof ItemBlock) {
            try {
                if (rightClickDelayField != null) {
                    rightClickDelayField.setInt(mc, delay);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}