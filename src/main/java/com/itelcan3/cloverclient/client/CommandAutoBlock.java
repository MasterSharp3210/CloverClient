package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandAutoBlock extends CommandBase {

    @Override
    public String getCommandName() {
        return "cloverautoblock";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/cloverautoblock";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        AutoBlock.enabled = !AutoBlock.enabled;
        String state = AutoBlock.enabled ? "\u00A7aenabled" : "\u00A7cdisabled";
        if (Minecraft.getMinecraft().thePlayer != null) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7fAutoBlock " + state));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}