package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class MobESP {

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if (Freecam.enabled) {
            return;
        }
        if (EspConfig.mobESPMode.equalsIgnoreCase("off")) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.theWorld == null) return;

        RenderManager renderManager = mc.getRenderManager();
        double renderX = renderManager.viewerPosX;
        double renderY = renderManager.viewerPosY;
        double renderZ = renderManager.viewerPosZ;

        int color = EspConfig.mobESPColor;
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

        for (Entity entity : mc.theWorld.loadedEntityList) {
            if ((entity instanceof EntityMob || entity instanceof EntityAnimal) && !entity.isDead) {

                double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.partialTicks - renderX;
                double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.partialTicks - renderY;
                double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.partialTicks - renderZ;

                if (EspConfig.mobESPMode.equalsIgnoreCase("3d")) {
                    AxisAlignedBB box = entity.getEntityBoundingBox().offset(-entity.posX, -entity.posY, -entity.posZ).offset(x, y, z);
                    RenderGlobal.drawOutlinedBoundingBox(box, (int)(r * 255), (int)(g * 255), (int)(b * 255), 255);

                } else if (EspConfig.mobESPMode.equalsIgnoreCase("2d")) {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(x, y + (entity.height / 2.0f), z);
                    GlStateManager.rotate(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);

                    float width = entity.width / 2.0f;
                    float height = entity.height;

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
        }

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
