package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ESP {

    public static String hitboxMode = "off";

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if (Freecam.enabled) {
            return;
        }       
        if (hitboxMode.equalsIgnoreCase("off")) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.getRenderManager() == null || mc.theWorld == null) return;

        RenderManager renderManager = mc.getRenderManager();
        double renderX = renderManager.viewerPosX;
        double renderY = renderManager.viewerPosY;
        double renderZ = renderManager.viewerPosZ;

        int color = EspConfig.playerESPColor;
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;

        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(2.5f);

        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (player == mc.thePlayer || player.isDead) continue;

            double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.partialTicks - renderX;
            double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.partialTicks - renderY;
            double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.partialTicks - renderZ;

            float paddingMultiplier = 1.2f; 
            float width = (player.width / 2.0f) * paddingMultiplier;
            float height = player.height * 1.05f;

            if (hitboxMode.equalsIgnoreCase("3d")) {
                AxisAlignedBB box = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
                draw3DBox(box, r, g, b, 0.8f);

            } else if (hitboxMode.equalsIgnoreCase("2d")) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(x, y + (player.height / 2.0f), z);
                GlStateManager.rotate(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);

                float pitchRad = (float) Math.toRadians(renderManager.playerViewX);
                float cosPitch = Math.abs((float) Math.cos(pitchRad));
                float sinPitch = Math.abs((float) Math.sin(pitchRad));

                float dynamicWidth = width;
                float dynamicHeightMin = -((height / 2.0f) * cosPitch + width * sinPitch);
                float dynamicHeightMax = (height / 2.0f) * cosPitch + width * sinPitch;

                Tessellator tessellator = Tessellator.getInstance();
                WorldRenderer worldrenderer = tessellator.getWorldRenderer();

                worldrenderer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);
                worldrenderer.pos(-dynamicWidth, dynamicHeightMin, 0.0D).color(r, g, b, 0.8f).endVertex();
                worldrenderer.pos(dynamicWidth, dynamicHeightMin, 0.0D).color(r, g, b, 0.8f).endVertex();
                worldrenderer.pos(dynamicWidth, dynamicHeightMax, 0.0D).color(r, g, b, 0.8f).endVertex();
                worldrenderer.pos(-dynamicWidth, dynamicHeightMax, 0.0D).color(r, g, b, 0.8f).endVertex();
                tessellator.draw();

                GlStateManager.popMatrix();
            }
        }

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private void draw3DBox(AxisAlignedBB boundingBox, float r, float g, float b, float a) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).color(r, g, b, a).endVertex();
        tessellator.draw();

        worldrenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).color(r, g, b, a).endVertex();
        tessellator.draw();

        worldrenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).color(r, g, b, a).endVertex();
        tessellator.draw();
    }
}