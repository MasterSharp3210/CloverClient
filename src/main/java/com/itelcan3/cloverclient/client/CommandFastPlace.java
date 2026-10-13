package com.itelcan3.cloverclient.command;

import com.itelcan3.cloverclient.client.FastPlace;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.Arrays;
import java.util.List;

public class CommandFastPlace extends CommandBase {

    @Override
    public String getCommandName() {
        return "cloverfastplace";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/cloverfastplace";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        FastPlace.toggle();
        String state = FastPlace.enabled ? "\u00A7aenabled" : "\u00A7cdisabled";
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7fFastPlace " + state));
    }
}
