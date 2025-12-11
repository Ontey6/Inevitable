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

public class ChatFile {
   
   public static File file;
   
   public static YamlConfiguration config;
   
   private ChatFile() { }
   
   public static void load() {
      file = new File(plugin.getDataFolder(), "chat.yml");
      
      if(!file.exists())
         plugin.saveResource("chat.yml", false);
      
      config = new YamlConfiguration();
      config.options().parseComments(true);
      
      try {
         config.load(file);
      } catch(Exception e) {
         Main.disablePlugin("Couldn't load the chat file.");
         FileLog.saveStackTrace(e);
      }
   }
   
   public static void save() {
      try {
         config.save(file);
      } catch(IOException e) {
         Log.error("Couldn't save the chat file.");
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
      return Files.getMessage(config, path);
   }
   
   public static <T> List<T> singletonList(T t) {
      List<T> out = new ArrayList<>(1);
      out.add(t);
      return out;
   }
}