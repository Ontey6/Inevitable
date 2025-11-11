package com.ontey.files;

import com.ontey.Main;
import com.ontey.api.log.Log;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.ontey.Main.plugin;

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
         Log.error(
           "+-+-+-+-+-+-+-+-+-+-+-+-Inevitable-+-+-+-+-+-+-+-+-+-+-+-+-+",
           "  Couldn't load the config file.",
           "  Look at the stack-trace below, so you can identify the error.",
           "  There is probably a syntax error in the yml.",
           "  Fix the error, then restart the server and it will work again.",
           "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+"
         );
         //noinspection CallToPrintStackTrace
         e.printStackTrace();
         Main.disablePlugin();
      }
   }
   
   public static void save() {
      try {
         config.save(file);
      } catch(IOException e) {
         Log.error(
           "+-+-+-+-+-+-+-+-+-+-+-+-Inevitable-+-+-+-+-+-+-+-+-+-+-+-+-+",
           "  Couldn't save the config file.",
           "  Look at the stack-trace below, so you can identify the error.",
           "  There is probably a syntax error in the yml.",
           "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+"
         );
         
         //noinspection CallToPrintStackTrace
         e.printStackTrace();
      }
   }
   
   public static ConfigurationSection createSection(String path, Map<String, ?> values) {
      var section = config.createSection(path, values);
      save();
      return section;
   }
}
