package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandESP extends CommandBase {

    @Override
    public String getCommandName() {
        return "cloveresp";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/cloveresp [2d|3d|color <color_name>]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            if (ESP.hitboxMode.equalsIgnoreCase("off")) {
                ESP.hitboxMode = "3d";
            } else {
                ESP.hitboxMode = "off";
            }
            sendMessage("\u00A7fESP " + (ESP.hitboxMode.equalsIgnoreCase("off") ? "\u00A7cdisabled" : "\u00A7aenabled (" + ESP.hitboxMode.toUpperCase() + ")"));
            return;
        }

        String arg = args[0].toLowerCase();

        if (arg.equals("2d") || arg.equals("3d")) {
            ESP.hitboxMode = arg;
            sendMessage("\u00A7fESP set to \u00A7a" + arg.toUpperCase());
            return;
        }

        if (arg.equals("color") && args.length >= 2) {
            int col = EspConfig.parseColor(args[1]);
            if (col != -1) {
                EspConfig.playerESPColor = col;
                sendMessage("\u00A7fESP color set to \u00A7a" + args[1]);
            } else {
                sendMessage("\u00A7cInvalid color name!");
            }
            return;
        }

        sendUsage();
    }

    private void sendMessage(String text) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
    }

    private void sendUsage() {
        sendMessage("\u00A7cUsage: /cloverESP [2d|3d]");
        sendMessage("\u00A7cUsage: /cloverESP color <color_name>");
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}