package com.itelcan3.cloverclient.client;

import com.itelcan3.cloverclient.client.AutoClicker;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandAutoClicker extends CommandBase {

    @Override
    public String getCommandName() {
        return "cloverautoclicker";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/cloverautoclicker";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        AutoClicker.enabled = !AutoClicker.enabled;

        String state = AutoClicker.enabled
                ? "\u00A7aenabled"
                : "\u00A7cdisabled";

        if (Minecraft.getMinecraft().thePlayer != null) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText("\u00A7fAutoClicker " + state)
            );
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
