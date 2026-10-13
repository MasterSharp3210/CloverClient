package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandFreecam extends CommandBase {

    @Override
    public String getCommandName() {
        return "cloverfreecam";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/cloverfreecam";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        Freecam.toggle();
        String state = Freecam.enabled ? "\u00A7aenabled" : "\u00A7cdisabled";
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7fFreecam " + state));
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}