package com.ontey.inevitable.module.modules.chatformat;

import com.ontey.inevitable.Main;
import com.ontey.inevitable.files.ChatFile;
import com.ontey.inevitable.module.Module;
import org.bukkit.Bukkit;

@Module
public class ChatFormatModule {
   
   static boolean enabled = false;
   
   static String format;
   
   static SimpleChatFormat chatFormat;
   
   public static void load() {
      enabled = ChatFile.config.getBoolean("enabled", false);
      
      format = ChatFile.config.getString("format");
      
      chatFormat = new SimpleChatFormat();
      
      Bukkit.getPluginManager().registerEvents(new ChatListener(), Main.plugin);
   }
}
