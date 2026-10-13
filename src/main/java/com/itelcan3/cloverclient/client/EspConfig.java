package com.itelcan3.cloverclient.client;

public class EspConfig {

    public static boolean storageESPEnabled = false;
    public static String storageDrawMode = "outline";
    

    public static boolean showChest = true;
    public static boolean showTrappedChest = true;
    public static boolean showFurnace = true;
    public static boolean showEnderChest = true;


    public static int colorChest = 0xFF0679;
    public static int colorTrappedChest = 0xFF0000;
    public static int colorFurnace = 0xC0C0C0; 
    public static int colorEnderChest = 0x4B0082;

    public static String mobESPMode = "off";
    public static int mobESPColor = 0xFF5555;

    public static int playerESPColor = 0x00AA00;

    public static int parseColor(String name) {
        if (name == null) return -1;
        String lower = name.toLowerCase();

        if (lower.equals("red")) return 0xFF5555;
        if (lower.equals("darkred")) return 0xAA0000;
        if (lower.equals("green")) return 0x55FF55;
        if (lower.equals("darkgreen")) return 0x00AA00;
        if (lower.equals("blue")) return 0x5555FF;
        if (lower.equals("darkblue")) return 0x0000AA;
        if (lower.equals("aqua")) return 0x55FFFF;
        if (lower.equals("darkaqua")) return 0x00AAAA;
        if (lower.equals("yellow")) return 0xFFFF55;
        if (lower.equals("gold")) return 0xFFAA00;
        if (lower.equals("orange")) return 0xFF7608;
        if (lower.equals("pink")) return 0xFF55FF;
        if (lower.equals("purple")) return 0xAA00AA;
        if (lower.equals("white")) return 0xFFFFFF;
        if (lower.equals("gray")) return 0xC0C0C0;
        if (lower.equals("darkgray")) return 0x555555;
        if (lower.equals("black")) return 0x000000;

        return -1;
    }
}