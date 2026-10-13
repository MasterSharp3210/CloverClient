package com.itelcan3.cloverclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandStorageESP extends CommandBase {

    @Override
    public String getCommandName() {
        return "cloverstorageesp";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/cloverstorageesp [fill|outline|both|<chest|trapped|furnace|enderchest> [color]]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        if (args.length == 0) {
            EspConfig.storageESPEnabled = !EspConfig.storageESPEnabled;
            sendMessage("\u00A7fStorageesp " + (EspConfig.storageESPEnabled ? "\u00A7aenabled" : "\u00A7cdisabled"));
            return;
        }

        String arg = args[0].toLowerCase();


        if (arg.equals("fill") || arg.equals("outline") || arg.equals("both")) {
            EspConfig.storageESPEnabled = true;
            EspConfig.storageDrawMode = arg;
            sendMessage("\u00A7fStorageesp style set to \u00A7a" + arg.toUpperCase());
            return;
        }

        boolean isChest = arg.equals("chest");
        boolean isTrapped = arg.equals("trapped");
        boolean isFurnace = arg.equals("furnace");
        boolean isEnder = arg.equals("enderchest") || arg.equals("ender");

        if (isChest || isTrapped || isFurnace || isEnder) {
            EspConfig.storageESPEnabled = true;

            if (args.length == 1) {
                if (isChest) {
                    EspConfig.showChest = !EspConfig.showChest;
                    sendMessage("\u00A7fChest ESP " + (EspConfig.showChest ? "\u00A7aenabled" : "\u00A7cdisabled"));
                } else if (isTrapped) {
                    EspConfig.showTrappedChest = !EspConfig.showTrappedChest;
                    sendMessage("\u00A7fTrapped Chest ESP " + (EspConfig.showTrappedChest ? "\u00A7aenabled" : "\u00A7cdisabled"));
                } else if (isFurnace) {
                    EspConfig.showFurnace = !EspConfig.showFurnace;
                    sendMessage("\u00A7fFurnace ESP " + (EspConfig.showFurnace ? "\u00A7aenabled" : "\u00A7cdisabled"));
                } else if (isEnder) {
                    EspConfig.showEnderChest = !EspConfig.showEnderChest;
                    sendMessage("\u00A7fEnder Chest ESP " + (EspConfig.showEnderChest ? "\u00A7aenabled" : "\u00A7cdisabled"));
                }
                return;
            }

            if (args.length >= 2) {
                int col = EspConfig.parseColor(args[1]);
                if (col == -1) {
                    sendMessage("\u00A7cInvalid color name!");
                    return;
                }

                if (isChest) {
                    EspConfig.showChest = true;
                    EspConfig.colorChest = col;
                    sendMessage("\u00A7fChest color set to \u00A7a" + args[1]);
                } else if (isTrapped) {
                    EspConfig.showTrappedChest = true;
                    EspConfig.colorTrappedChest = col;
                    sendMessage("\u00A7fTrapped Chest color set to \u00A7a" + args[1]);
                } else if (isFurnace) {
                    EspConfig.showFurnace = true;
                    EspConfig.colorFurnace = col;
                    sendMessage("\u00A7fFurnace color set to \u00A7a" + args[1]);
                } else if (isEnder) {
                    EspConfig.showEnderChest = true;
                    EspConfig.colorEnderChest = col;
                    sendMessage("\u00A7fEnder Chest color set to \u00A7a" + args[1]);
                }
                return;
            }
        }

        sendUsage();
    }

    private void sendMessage(String text) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
    }

    private void sendUsage() {
        sendMessage("\u00A7cUsage: /cloverstorageesp [fill|outline|both]");
        sendMessage("\u00A7cUsage: /cloverstorageesp <chest|trapped|furnace|enderchest> [color_name]");
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}