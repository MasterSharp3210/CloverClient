package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.Display;

public class Freecam {
    public static boolean enabled = false;
    public static EntityFreeCam entity;
    public static MouseHelper mouseHelper = new MouseHelper();
    public static MovementInput movementInput;
    public static Vec3 teleportDestination;

    public static void toggle() {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (player == null) return;

        enabled = !enabled;

        if (enabled) {
            entity = new EntityFreeCam();
            mc.theWorld.spawnEntityInWorld(entity);
            mc.setRenderViewEntity(entity);

            movementInput = player.movementInput;
            player.movementInput = new MovementInput(); 
            mc.mouseHelper = new StaticMouseHelper();
        } else {
            player.movementInput = movementInput;
            mc.setRenderViewEntity(mc.thePlayer);
            if (entity != null) {
                mc.theWorld.removeEntity(entity);
            }
            mc.mouseHelper = new MouseHelper();
            entity = null;
        }
    }

    @SubscribeEvent
    public void onMouseInput(MouseEvent event) {
        if (enabled) {
            EntityFreeCam.speed += Integer.signum(event.dwheel) * 0.25;
            if (EntityFreeCam.speed < 0.25) EntityFreeCam.speed = 0.25;
            if (EntityFreeCam.speed > 5.0) EntityFreeCam.speed = 5.0;
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRenderPre(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.START || !enabled || entity == null) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (Display.isActive() && mc.inGameHasFocus) {
            mouseHelper.mouseXYChange();

            float multi = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            multi = multi * multi * multi * 8.0F;

            float x = mouseHelper.deltaX * multi;
            float y = mouseHelper.deltaY * multi;

            if (mc.gameSettings.invertMouse) {
                y *= -1;
            }

            entity.setAngles(x, y);
        }

        mc.setRenderViewEntity(entity);
    }

    @SubscribeEvent
    public void onRenderPost(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !enabled) return;
        Minecraft mc = Minecraft.getMinecraft();
        mc.setRenderViewEntity(mc.thePlayer);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (enabled && event.entity instanceof EntityPlayerSP) toggle();
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (enabled && teleportDestination != null) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.thePlayer.getPositionVector().distanceTo(teleportDestination) < 1.0) {
                teleportDestination = null;
                mc.thePlayer.rotationPitch = entity.rotationPitch;
                mc.thePlayer.rotationYaw = entity.rotationYaw;

                if (mc.thePlayer.capabilities.allowFlying) {
                    mc.thePlayer.capabilities.isFlying = true;
                    mc.thePlayer.sendPlayerAbilities();
                }
                toggle();
            }
        }
    }

    @SubscribeEvent
    public void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        if (!enabled || !(event.entity instanceof EntityPlayerSP)) return;
        if (teleportDestination != null) event.setCanceled(true);
        Minecraft.getMinecraft().getRenderManager().livingPlayer = Minecraft.getMinecraft().thePlayer;
    }

    @SubscribeEvent
    public void onRenderHand(RenderHandEvent event) {
        if (enabled) event.setCanceled(true);
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Pre event) {
        if (enabled) {
            if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR
                    || event.type == RenderGameOverlayEvent.ElementType.EXPERIENCE
                    || event.type == RenderGameOverlayEvent.ElementType.HEALTH
                    || event.type == RenderGameOverlayEvent.ElementType.FOOD
                    || event.type == RenderGameOverlayEvent.ElementType.ARMOR
                    || event.type == RenderGameOverlayEvent.ElementType.AIR
                    || event.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
                event.setCanceled(true);
                return;
            }

            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.enableAlpha();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}