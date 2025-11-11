package com.ontey;

import com.ontey.commands.gui.*;
import com.ontey.commands.FlyCommand;
import com.ontey.commands.MainCommand;
import com.ontey.files.Commands;
import com.ontey.files.Config;
import com.ontey.listener.JoinQuitListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
   
   public static Main plugin;
   public static PluginManager pm;
   
   public static final String version = "1.0";
   
   @Override
   public void onEnable() {
      plugin = this;
      pm = getServer().getPluginManager();
      
      Config.load();
      Commands.load();
      registerEvents();
      registerCommands();
   }
   
   private void registerEvents() {
      pm.registerEvents(new JoinQuitListener(), plugin);
   }
   
   private void registerCommands() {
      MainCommand.register();
      
      FlyCommand.register();
      
      CraftingCommand.register();
      EnchantingCommand.register();
      CartographyCommand.register();
      LoomCommand.register();
      GrindstoneCommand.register();
      AnvilCommand.register();
      StonecutterCommand.register();
      SmithingCommand.register();
      EnderchestCommand.register();
   }
   
   public static void disablePlugin() {
      pm.disablePlugin(plugin);
   }
}