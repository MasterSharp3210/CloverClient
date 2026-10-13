package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Chams {

    public static boolean enabled = false;
    public static int color = 0xAA00AA;
    public static int invisibleColor = 0x00FFFF;

    @SubscribeEvent
    public void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        if (!enabled) return;

        EntityPlayer player = event.entityPlayer;
        Minecraft mc = Minecraft.getMinecraft();

        if (player == mc.thePlayer) return;

        boolean isBehindWall = !mc.thePlayer.canEntityBeSeen(player);

        if (!isBehindWall) {
            return; 
        }

        int activeColor = player.isInvisible() ? invisibleColor : color;

        float r = ((activeColor >> 16) & 0xFF) / 255.0f;
        float g = ((activeColor >> 8) & 0xFF) / 255.0f;
        float b = (activeColor & 0xFF) / 255.0f;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GlStateManager.disableLighting();
        GlStateManager.enableTexture2D();

        GlStateManager.color(r, g, b, 0.6f);
    }

    @SubscribeEvent
    public void onRenderPlayerPost(RenderPlayerEvent.Post event) {
        if (!enabled) return;
        EntityPlayer player = event.entityPlayer;
        Minecraft mc = Minecraft.getMinecraft();

        if (player == mc.thePlayer || !mc.thePlayer.canEntityBeSeen(player)) {
            if (player != mc.thePlayer) {
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }
    }
}
