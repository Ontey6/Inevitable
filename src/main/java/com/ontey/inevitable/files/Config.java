package com.ontey.inevitable.files;

import com.ontey.inevitable.Main;
import com.ontey.api.files.Files;
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

import static com.ontey.inevitable.Main.plugin;

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
         Main.disablePlugin("Couldn't load the config file.");
         FileLog.saveStackTrace(e);
         return;
      }
      
      loadFields();
   }
   
   public static void save() {
      try {
         config.save(file);
      } catch(IOException e) {
         Log.error("Couldn't save the config file.");
         FileLog.saveStackTrace(e);
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
      PLACEHOLDER_FORMAT = getOrDefault("format.placeholder-format", "<%ph>");
      DEBUG = getOrDefault("debug", false);
   }
   
   public static final String PREFIX = "[Inevitable]";
   public static String PLACEHOLDER_FORMAT;
   
   public static boolean DEBUG;
}
