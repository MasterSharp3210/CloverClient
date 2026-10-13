package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandHud extends CommandBase {

    @Override
    public String getCommandName() {
        return "cloverhud";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/cloverhud <color|text|on|off>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        if (args.length < 1) {
            sendUsage();
            return;
        }

        if (args[0].equalsIgnoreCase("on")) {
            HudRenderer.hudEnabled = true;
            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText("\u00A7fHUD has been \u00A7aenabled")
            );
            return;
        }

        if (args[0].equalsIgnoreCase("off")) {
            HudRenderer.hudEnabled = false;
            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText("\u00A7fHUD has been \u00A7cdisabled")
            );
            return;
        }

        if (args[0].equalsIgnoreCase("color")) {

            if (args.length < 2) {
                sendColors();
                return;
            }

            String color = args[1].toLowerCase();
            String chatColorCode;

            if (color.equals("red")) {
                HudRenderer.hudColor = 0xFF0000;
                chatColorCode = "\u00A7c";
            } else if (color.equals("darkred")) {
                HudRenderer.hudColor = 0xAA0000;
                chatColorCode = "\u00A74";
            } else if (color.equals("green")) {
                HudRenderer.hudColor = 0x55FF55;
                chatColorCode = "\u00A7a";
            } else if (color.equals("darkgreen")) {
                HudRenderer.hudColor = 0x005500;
                chatColorCode = "\u00A72";
            } else if (color.equals("blue")) {
                HudRenderer.hudColor = 0x5555FF;
                chatColorCode = "\u00A79";
            } else if (color.equals("darkblue")) {
                HudRenderer.hudColor = 0x0000AA;
                chatColorCode = "\u00A71";
            } else if (color.equals("aqua")) {
                HudRenderer.hudColor = 0x55FFFF;
                chatColorCode = "\u00A7b";
            } else if (color.equals("darkaqua")) {
                HudRenderer.hudColor = 0x00AAAA;
                chatColorCode = "\u00A73";
            } else if (color.equals("prismarine")) {
                HudRenderer.hudColor = 0x07614A;
                chatColorCode = "\u00A73";
            } else if (color.equals("yellow")) {
                HudRenderer.hudColor = 0xFFFF55;
                chatColorCode = "\u00A7e";
            } else if (color.equals("gold")) {
                HudRenderer.hudColor = 0xFFAA00;
                chatColorCode = "\u00A76";
            } else if (color.equals("orange")) {
                HudRenderer.hudColor = 0xFF7608;
                chatColorCode = "\u00A76";
            } else if (color.equals("pink")) {
                HudRenderer.hudColor = 0xFF55FF;
                chatColorCode = "\u00A7d";
            } else if (color.equals("purple")) {
                HudRenderer.hudColor = 0xAA00AA;
                chatColorCode = "\u00A75";
            } else if (color.equals("magenta")) {
                HudRenderer.hudColor = 0xFF0679;
                chatColorCode = "\u00A7d";
            } else if (color.equals("white")) {
                HudRenderer.hudColor = 0xFFFFFF;
                chatColorCode = "\u00A7f";
            } else if (color.equals("gray")) {
                HudRenderer.hudColor = 0xAAAAAA;
                chatColorCode = "\u00A77";
            } else if (color.equals("darkgray")) {
                HudRenderer.hudColor = 0x555555;
                chatColorCode = "\u00A78";
            } else if (color.equals("black")) {
                HudRenderer.hudColor = 0x000000;
                chatColorCode = "\u00A70";
            } else {
                sendColors();
                return;
            }

            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText("\u00A7fHUD color set to " + chatColorCode + color)
            );

            return;
        }

        if (args[0].equalsIgnoreCase("text")) {

            if (args.length < 3) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("\u00A7cUsage: /cloverhud text <text> <scale_percentage>")
                );
                return;
            }

            float scalePercentage;
            try {
                scalePercentage = Float.parseFloat(args[args.length - 1]);
            } catch (NumberFormatException e) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("\u00A7cThe scale must be a valid number (e.g. 100, 150, 200).")
                );
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length - 1; i++) {
                sb.append(args[i]).append(" ");
            }

            HudRenderer.hudText = sb.toString().trim();
            HudRenderer.hudScale = scalePercentage / 100.0f;

            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText("\u00A7fHUD text set to: \u00A7a" + HudRenderer.hudText + " \u00A7fwith scale \u00A7a" + scalePercentage + "%")
            );

            return;
        }

        sendUsage();
    }

    private void sendUsage() {
        if (Minecraft.getMinecraft().thePlayer != null) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText("\u00A7cUsage: " + getCommandUsage(null))
            );
        }
    }

    private void sendColors() {
        Minecraft.getMinecraft().thePlayer.addChatMessage(
                new ChatComponentText("\u00A7fAvailable colors: " +
                        "\u00A7cred\u00A7f, " +
                        "\u00A74darkred\u00A7f, " +
                        "\u00A7agreen\u00A7f, " +
                        "\u00A72darkgreen\u00A7f, " +
                        "\u00A79blue\u00A7f, " +
                        "\u00A71darkblue\u00A7f, " +
                        "\u00A7baqua\u00A7f, " +
                        "\u00A73darkaqua\u00A7f, " +
                        "\u00A73prismarine\u00A7f, " +
                        "\u00A7eyellow\u00A7f, " +
                        "\u00A76gold\u00A7f, " +
                        "\u00A76orange\u00A7f, " +
                        "\u00A7dpink\u00A7f, " +
                        "\u00A75purple\u00A7f, " +
                        "\u00A7dmagenta\u00A7f, " +
                        "\u00A7fwhite\u00A7f, " +
                        "\u00A77gray\u00A7f, " +
                        "\u00A78darkgray\u00A7f, " +
                        "\u00A70black")
        );
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}