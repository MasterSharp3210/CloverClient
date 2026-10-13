package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class SpawnerFinder {
    public static boolean enabled = false;
    public static int color = 0x4682B4;

    public static void toggle() {
        enabled = !enabled;
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if (Freecam.enabled) {
            return;
        }   
        if (!enabled) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.getRenderViewEntity() == null) return;

        double renderPosX = mc.getRenderViewEntity().lastTickPosX + (mc.getRenderViewEntity().posX - mc.getRenderViewEntity().lastTickPosX) * event.partialTicks;
        double renderPosY = mc.getRenderViewEntity().lastTickPosY + (mc.getRenderViewEntity().posY - mc.getRenderViewEntity().lastTickPosY) * event.partialTicks;
        double renderPosZ = mc.getRenderViewEntity().lastTickPosZ + (mc.getRenderViewEntity().posZ - mc.getRenderViewEntity().lastTickPosZ) * event.partialTicks;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;

        for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
            if (tileEntity instanceof TileEntityMobSpawner) {
                BlockPos pos = tileEntity.getPos();
                
                double x = pos.getX() - renderPosX;
                double y = pos.getY() - renderPosY;
                double z = pos.getZ() - renderPosZ;

                net.minecraft.util.AxisAlignedBB bb = new net.minecraft.util.AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);

                GlStateManager.color(r, g, b, 0.4f);
                net.minecraft.client.renderer.RenderGlobal.drawOutlinedBoundingBox(bb, (int)(r * 255), (int)(g * 255), (int)(b * 255), 255);
            }
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
