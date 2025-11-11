package com.ontey.files;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Files {
   
   public static List<String> getList(ConfigurationSection config, String path) {
      List<?> list = config.getList(path);
      if (list == null || list.isEmpty())
         return new ArrayList<>();
      
      List<String> out = new ArrayList<>();
      for (Object obj : list)
         out.add(obj == null ? "" : obj.toString());
      return out;
   }
   
   public static List<String> getListable(ConfigurationSection config, String path) {
      return config.isSet(path) && !config.isList(path)
        ? Config.singletonList(config.getString(path, ""))
        : getList(config, path);
   }
   
   public static List<String> getListable(ConfigurationSection config, String path, List<String> fallback) {
      if(!config.isSet(path))
         return fallback;
      return config.isString(path)
        ? Config.singletonList(config.getString(path, ""))
        : getList(config, path);
   }
   
   @Contract("_, _, !null -> !null")
   public static <T> T getOrDefault(ConfigurationSection config, String path, T fallback) {
      try {
         if(config.get(path) == null)
            return fallback;
         // noinspection unchecked
         return (T) config.get(path);
      } catch(ClassCastException e) {
         return fallback;
      }
   }
   
   @Nullable
   public static String getMessage(ConfigurationSection config, String path) {
      return getMessage(config, path, null);
   }
   
   @Contract("_, _, !null -> !null")
   public static String getMessage(ConfigurationSection config, String path, String def) {
      List<String> listable = Files.getListable(config, path, null);
      
      if(listable == null)
         return def;
      
      return String.join("\n", Files.getListable(config, path, null));
   }
}