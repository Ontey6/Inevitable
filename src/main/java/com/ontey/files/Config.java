package com.ontey.files;

import com.ontey.Main;
import com.ontey.api.actionsection.ActionSection;
import com.ontey.api.log.Log;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ontey.Main.plugin;

public class Config {
   
   public static File file;
   
   public static YamlConfiguration config;
   
   private Config() { }
   
   public static void load() {
      file = new File(plugin.getDataFolder(), "config.yml");
      
      if(!file.exists())
         plugin.saveResource("config.yml", false);
      
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
         return;
      }
      
      loadFields();
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
   
   public static void set(String path, Object value) {
      config.set(path, value);
      save();
   }
   
   public static ConfigurationSection createSection(String path) {
      var section = config.createSection(path);
      save();
      return section;
   }
   
   public static ConfigurationSection createSection(String path, Map<String, ?> values) {
      var section = config.createSection(path, values);
      save();
      return section;
   }
   
   // Helpers
   
   @Contract("_, !null -> !null")
   public static <T> T getOrDefault(String path, T fallback) {
      return Files.getOrDefault(config, path, fallback);
   }
   
   @Nullable
   public static String getMessage(String path) {
      List<String> listable = Files.getListable(config, path, null);
      
      if(listable == null)
         return null;
      
      return String.join("\n", Files.getListable(config, path, null));
   }
   
   public static <T> List<T> singletonList(T t) {
      List<T> out = new ArrayList<>(1);
      out.add(t);
      return out;
   }
   
   // Fields
   
   private static void loadFields() {
      PREFIX = getOrDefault("format.prefix", "[Inevitable]");
      PLACEHOLDER_FORMAT = getOrDefault("format.placeholder-format", "<%ph>");
      JOIN_MESSAGE = getMessage("join-message");
      QUIT_MESSAGE = getMessage("quit-message");
      ON_JOIN_ACTION = ActionSection.of(config.getConfigurationSection("on-join"));
      ON_QUIT_ACTION = ActionSection.of(config.getConfigurationSection("on-quit"));
   }
   
   public static String PREFIX, PLACEHOLDER_FORMAT;
   
   @Nullable
   public static String JOIN_MESSAGE, QUIT_MESSAGE;
   
   public static ActionSection ON_JOIN_ACTION, ON_QUIT_ACTION;
}
