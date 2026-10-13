package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandMobESP extends CommandBase {

    @Override
    public String getCommandName() {
        return "clovermobesp";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/clovermobesp [2d|3d|color <color_name>]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            if (EspConfig.mobESPMode.equalsIgnoreCase("off")) {
                EspConfig.mobESPMode = "3d";
            } else {
                EspConfig.mobESPMode = "off";
            }
            sendMessage("\u00A7fMobESP " + (EspConfig.mobESPMode.equalsIgnoreCase("off") ? "\u00A7cdisabled" : "\u00A7aenabled (" + EspConfig.mobESPMode.toUpperCase() + ")"));
            return;
        }

        String arg = args[0].toLowerCase();

        if (arg.equals("2d") || arg.equals("3d")) {
            EspConfig.mobESPMode = arg;
            sendMessage("\u00A7fMobESP set to \u00A7a" + arg.toUpperCase());
            return;
        }

        if (arg.equals("color") && args.length >= 2) {
            int col = EspConfig.parseColor(args[1]);
            if (col != -1) {
                EspConfig.mobESPColor = col;
                sendMessage("\u00A7fMobESP color set to \u00A7a" + args[1]);
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
        sendMessage("\u00A7cUsage: /clovermobESP [2d|3d]");
        sendMessage("\u00A7cUsage: /clovermobESP color <color_name>");
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}