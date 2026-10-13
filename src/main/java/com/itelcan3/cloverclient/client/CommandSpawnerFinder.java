package com.itelcan3.cloverclient.command;

import com.itelcan3.cloverclient.client.EspConfig;
import com.itelcan3.cloverclient.client.SpawnerFinder;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandSpawnerFinder extends CommandBase {

    @Override
    public String getCommandName() {
        return "cloverswfinder";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/cloverswfinder [color] <color_name>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Minecraft mc = Minecraft.getMinecraft();

        if (args.length == 0) {
            SpawnerFinder.enabled = !SpawnerFinder.enabled;
            String status = SpawnerFinder.enabled ? "\u00A7aenabled" : "\u00A7cdisabled";
            if (mc.thePlayer != null) {
                mc.thePlayer.addChatMessage(new ChatComponentText("\u00A7fSpawnerFinder " + status));
            }
            return;
        }

        if (args[0].equalsIgnoreCase("color") && args.length >= 2) {
            int parsedColor = EspConfig.parseColor(args[1]);
            if (parsedColor != -1) {
                SpawnerFinder.color = parsedColor;
                SpawnerFinder.enabled = true;
                if (mc.thePlayer != null) {
                    mc.thePlayer.addChatMessage(new ChatComponentText("\u00A7fBoxes color set to \u00A7a" + args[1]));
                }
            } else {
                if (mc.thePlayer != null) {
                    mc.thePlayer.addChatMessage(new ChatComponentText("\u00A7cInvalid color name!"));
                }
            }
        }
    }
}