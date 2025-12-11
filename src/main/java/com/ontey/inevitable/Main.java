package com.ontey.inevitable;

import com.ontey.api.brigadier.registry.CommandRegistry;
import com.ontey.api.log.Log;
import com.ontey.inevitable.commands.MainCommand;
import com.ontey.inevitable.files.*;
import com.ontey.inevitable.module.ModuleLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
   
   public static Main plugin;
   public static PluginManager pm;
   
   public static final String version = "1.1-alpha";
   public static final int debugSnapshot = 1;
   public static final String debugVersion = version + "$" + debugSnapshot;
   
   @Override
   public void onEnable() {
      plugin = this;
      pm = getServer().getPluginManager();
      
      loadFiles();
      ModuleLoader.loadModules();
      loadCommands();
      
      Log.debug("Enabled plugin");
      Log.debug("Running version " + debugVersion);
   }
   
   private void loadFiles() {
      FileLog.load();
      Config.load();
      Commands.load();
      ChatFile.load();
      EventsFile.load();
   }
   
   private void loadCommands() {
      MainCommand.register();
      Commands.loadCommands();
      CommandRegistry.load(plugin);
   }
   
   /**
    * @deprecated should specify reason to disable plugin
    * @see Main#disablePlugin(String)
    */
   
   @Deprecated
   public static void disablePlugin() {
      Log.error("Disabling plugin");
      pm.disablePlugin(plugin);
   }
   
   public static void disablePlugin(String reason) {
      Log.error("Disabling plugin: " + reason);
      pm.disablePlugin(plugin);
   }
}