package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class StorageESP {

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if (Freecam.enabled) {
            return;
        }
        if (!EspConfig.storageESPEnabled) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.theWorld == null) return;

        double renderX = mc.getRenderManager().viewerPosX;
        double renderY = mc.getRenderManager().viewerPosY;
        double renderZ = mc.getRenderManager().viewerPosZ;

        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(2.5f);

        for (TileEntity tile : mc.theWorld.loadedTileEntityList) {
            BlockPos pos = tile.getPos();
            AxisAlignedBB box = new AxisAlignedBB(
                    pos.getX() - renderX,
                    pos.getY() - renderY,
                    pos.getZ() - renderZ,
                    pos.getX() + 1 - renderX,
                    pos.getY() + 1 - renderY,
                    pos.getZ() + 1 - renderZ
            );

            int color = -1;

            if (tile instanceof TileEntityChest) {
                TileEntityChest chest = (TileEntityChest) tile;
                if (chest.getChestType() == 1) {
                    if (!EspConfig.showTrappedChest) continue;
                    color = EspConfig.colorTrappedChest;
                } else {
                    if (!EspConfig.showChest) continue;
                    color = EspConfig.colorChest;
                }
            } else if (tile instanceof TileEntityEnderChest) {
                if (!EspConfig.showEnderChest) continue;
                color = EspConfig.colorEnderChest;
            } else if (tile instanceof TileEntityFurnace) {
                if (!EspConfig.showFurnace) continue;
                color = EspConfig.colorFurnace;
            } else {
                continue;
            }

            float r = ((color >> 16) & 0xFF) / 255.0f;
            float g = ((color >> 8) & 0xFF) / 255.0f;
            float b = (color & 0xFF) / 255.0f;

            String mode = EspConfig.storageDrawMode;

            if (mode.equalsIgnoreCase("fill") || mode.equalsIgnoreCase("both")) {
                drawFilledBox(box, r, g, b, 0.35f);
            }

            if (mode.equalsIgnoreCase("outline") || mode.equalsIgnoreCase("both")) {
                RenderGlobal.drawOutlinedBoundingBox(box, (int)(r * 255), (int)(g * 255), (int)(b * 255), 255);
            }
        }

        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private void drawFilledBox(AxisAlignedBB box, float r, float g, float b, float a) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        // Bottom
        worldrenderer.pos(box.minX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.minX, box.minY, box.maxZ).color(r, g, b, a).endVertex();

        // Top
        worldrenderer.pos(box.minX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, a).endVertex();

        // North
        worldrenderer.pos(box.minX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.minZ).color(r, g, b, a).endVertex();

        // South
        worldrenderer.pos(box.minX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();

        // West
        worldrenderer.pos(box.minX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.minX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.minZ).color(r, g, b, a).endVertex();

        // East
        worldrenderer.pos(box.maxX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, a).endVertex();

        tessellator.draw();
    }
}