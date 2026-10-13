package com.itelcan3.cloverclient.command;

import com.itelcan3.cloverclient.client.Chams;
import com.itelcan3.cloverclient.client.EspConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandChams extends CommandBase {

    @Override
    public String getCommandName() {
        return "cloverchams";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/cloverchams [color|invis] <color_name>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            Chams.enabled = !Chams.enabled;
            sendMessage("\u00A7fChams " + (Chams.enabled ? "\u00A7aenabled" : "\u00A7cdisabled"));
            return;
        }


        if (args[0].equalsIgnoreCase("color") && args.length >= 2) {
            int parsedColor = EspConfig.parseColor(args[1]);
            if (parsedColor != -1) {
                Chams.color = parsedColor;
                Chams.enabled = true;
                sendMessage("\u00A7fChams color set to \u00A7a" + args[1]);
            } else {
                sendMessage("\u00A7cInvalid color name!");
            }
            return;
        }


        if (args[0].equalsIgnoreCase("invis") && args.length >= 2) {
            int parsedColor = EspConfig.parseColor(args[1]);
            if (parsedColor != -1) {
                Chams.invisibleColor = parsedColor;
                Chams.enabled = true;
                sendMessage("\u00A7fChams invisible color set to \u00A7a" + args[1]);
            } else {
                sendMessage("\u00A7cInvalid color name!");
            }
            return;
        }


        int parsedColor = EspConfig.parseColor(args[0]);
        if (parsedColor != -1) {
            Chams.color = parsedColor;
            Chams.enabled = true;
            sendMessage("\u00A7fChams color set to \u00A7a" + args[0]);
        } else {
            sendMessage("\u00A7cUsage: /cloverchams [color|invis] <color_name>");
        }
    }

    private void sendMessage(String text) {
        if (Minecraft.getMinecraft().thePlayer != null) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
