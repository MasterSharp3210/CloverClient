package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandSprint extends CommandBase {

    @Override
    public String getCommandName() {
        return "cloversprint";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/cloversprint";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        Sprint.enabled = !Sprint.enabled;
        String state = Sprint.enabled ? "\u00A7aenabled" : "\u00A7cdisabled";
        if (Minecraft.getMinecraft().thePlayer != null) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7fSprint " + state));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}