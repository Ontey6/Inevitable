package com.ontey.inevitable.files;

import com.ontey.inevitable.Main;
import com.ontey.api.log.Log;
import com.ontey.inevitable.commands.*;
import com.ontey.inevitable.commands.*;
import com.ontey.inevitable.commands.GuiCommands.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.ontey.inevitable.Main.plugin;

public class Commands {
   
   public static File file;
   
   public static YamlConfiguration config;
   
   private Commands() { }
   
   public static void load() {
      file = new File(plugin.getDataFolder(), "commands.yml");
      
      if(!file.exists())
         plugin.saveResource("commands.yml", false);
      
      config = new YamlConfiguration();
      config.options().parseComments(true);
      
      try {
         config.load(file);
      } catch(Exception e) {
         Main.disablePlugin("Couldn't load the commands file.");
         FileLog.saveStackTrace(e);
      }
   }
   
   public static void save() {
      try {
         config.save(file);
      } catch(IOException e) {
         Log.error("Couldn't save the commands file.");
         FileLog.saveStackTrace(e);
      }
   }
   
   public static ConfigurationSection createSection(String path, Map<String, ?> values) {
      var section = config.createSection(path, values);
      save();
      return section;
   }
   
   public static void loadCommands() {
      FlyCommand.register();
      FeedCommand.register();
      HealCommand.register();
      
      CraftingCommand.register();
      EnchantingCommand.register();
      CartographyCommand.register();
      LoomCommand.register();
      GrindstoneCommand.register();
      AnvilCommand.register();
      StonecutterCommand.register();
      SmithingCommand.register();
      EnderchestCommand.register();
      
      GamemodeCommand.register();
      ManageCommand.register();
      // not quite perfected
      //TODO v1.2
      //CrawlCommand.register();
   }
   
   public static void unloadCommands() {
      //TODO v1.2
   }
}
