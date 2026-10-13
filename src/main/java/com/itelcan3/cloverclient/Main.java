package com.itelcan3.cloverclient;

import com.itelcan3.cloverclient.client.*;
import com.itelcan3.cloverclient.command.CommandChams;
import com.itelcan3.cloverclient.command.CommandFastPlace;
import com.itelcan3.cloverclient.command.CommandSpawnerFinder;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.lwjgl.opengl.Display;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {
    public static final String MODID = "cloverclient";
    public static final String NAME = "Clover Client";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        Display.setTitle("Clover Client - Minecraft 1.8.9");

        MinecraftForge.EVENT_BUS.register(new AutoBlock());
        MinecraftForge.EVENT_BUS.register(new AutoClicker());
        MinecraftForge.EVENT_BUS.register(new CapeHandler());
        MinecraftForge.EVENT_BUS.register(new Chams());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.register(new ESP());
        MinecraftForge.EVENT_BUS.register(new FastPlace());
        MinecraftForge.EVENT_BUS.register(new Freecam());
        MinecraftForge.EVENT_BUS.register(new HudRenderer());
        MinecraftForge.EVENT_BUS.register(new MobESP());
        MinecraftForge.EVENT_BUS.register(new SpawnerFinder());
        MinecraftForge.EVENT_BUS.register(new Sprint());
        MinecraftForge.EVENT_BUS.register(new StorageESP());


        ClientCommandHandler.instance.registerCommand(new CommandAutoBlock());
        ClientCommandHandler.instance.registerCommand(new CommandAutoClicker());
        ClientCommandHandler.instance.registerCommand(new CommandChams());
        ClientCommandHandler.instance.registerCommand(new CommandESP());
        ClientCommandHandler.instance.registerCommand(new CommandFastPlace());
        ClientCommandHandler.instance.registerCommand(new CommandFreecam());
        ClientCommandHandler.instance.registerCommand(new CommandHud());
        ClientCommandHandler.instance.registerCommand(new CommandMobESP());
        ClientCommandHandler.instance.registerCommand(new CommandSpawnerFinder());
        ClientCommandHandler.instance.registerCommand(new CommandSprint());
        ClientCommandHandler.instance.registerCommand(new CommandStorageESP());
        
    }
}