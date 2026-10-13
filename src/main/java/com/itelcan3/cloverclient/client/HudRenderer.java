package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HudRenderer extends Gui {

    public static String hudText = "Clover Client";
    public static int hudColor = 0x00AA00;
    public static float hudScale = 1.0f;
    public static boolean hudEnabled = true;

    private static class ModuleItem {
        String name;
        int color;

        ModuleItem(String name, int color) {
            this.name = name;
            this.color = color;
        }
    }

    @SubscribeEvent
        public void onRender(RenderGameOverlayEvent.Post event) {
            if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
                
                if (!hudEnabled) return;

                drawHud();
                drawArrayList();
            }
        }

    public void drawHud() {
        if (!hudEnabled) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (hudText == null || hudText.isEmpty() || mc.thePlayer == null || mc.gameSettings.showDebugInfo) return;

        GlStateManager.pushMatrix();
        GlStateManager.scale(hudScale, hudScale, 1.0f);

        int scaledX = (int) (5 / hudScale);
        int scaledY = (int) (5 / hudScale);

        mc.fontRendererObj.drawString("\u00A7l" + hudText, scaledX, scaledY, hudColor);

        scaledY += 10;
        mc.fontRendererObj.drawString("FPS: \u00A7f" + Minecraft.getDebugFPS(), scaledX, scaledY, hudColor);

        scaledY += 10;
        mc.fontRendererObj.drawString("Player: \u00A7f" + mc.thePlayer.getGameProfile().getName(), scaledX, scaledY, hudColor);

        GlStateManager.popMatrix();
    }

    private void drawArrayList() {
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.gameSettings.showDebugInfo) return;

        List<ModuleItem> activeModules = new ArrayList<ModuleItem>();

        if (AutoBlock.enabled) {
            activeModules.add(new ModuleItem("AutoBlock", 0xF542F5));
        }

        if (AutoClicker.enabled)
        {
            activeModules.add(new ModuleItem("AutoClicker", 0xAA0000));
        }

        if (Chams.enabled) {
            activeModules.add(new ModuleItem("Chams", Chams.color));
        }

        if (!ESP.hitboxMode.equalsIgnoreCase("off")) {
            activeModules.add(new ModuleItem("ESP \u00A77" + ESP.hitboxMode.toUpperCase(), 0x55FF55));
        }

        if (EspConfig.storageESPEnabled) {
            activeModules.add(new ModuleItem("StorageESP", 0xFF69B4));
        }

        if (!EspConfig.mobESPMode.equalsIgnoreCase("off")) {
            activeModules.add(new ModuleItem("MobESP", 0xFF5555));
        }

        if (FastPlace.enabled) {
            activeModules.add(new ModuleItem("FastPlace", 0xFFFF00));
        }

        if (Freecam.enabled) {
            activeModules.add(new ModuleItem("Freecam", 0x008080));
        }

        if (Sprint.enabled) {
            activeModules.add(new ModuleItem("Sprint", 0x55FFFF));
        }

        if (SpawnerFinder.enabled) {
            activeModules.add(new ModuleItem("SpawnerFinder", SpawnerFinder.color));
        }       

        if (activeModules.isEmpty()) return;

        activeModules.sort(new Comparator<ModuleItem>() {
            @Override
            public int compare(ModuleItem m1, ModuleItem m2) {
                int w1 = mc.fontRendererObj.getStringWidth(m1.name);
                int w2 = mc.fontRendererObj.getStringWidth(m2.name);
                return (w2 < w1) ? -1 : ((w2 == w1) ? 0 : 1);
            }
        });

        ScaledResolution sr = new ScaledResolution(mc);
        int yOffset = 5;

        for (ModuleItem item : activeModules) {
            int stringWidth = mc.fontRendererObj.getStringWidth(item.name);
            int x = sr.getScaledWidth() - stringWidth - 5;

            mc.fontRendererObj.drawStringWithShadow(item.name, x, yOffset, item.color);
            yOffset += mc.fontRendererObj.FONT_HEIGHT + 2;
        }
    }
}
