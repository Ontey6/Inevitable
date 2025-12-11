package com.ontey.inevitable.module.modules.joinquit;

import com.ontey.api.actionsection.ActionSection;
import com.ontey.inevitable.Main;
import com.ontey.inevitable.files.Config;
import com.ontey.inevitable.files.EventsFile;
import com.ontey.inevitable.module.Module;
import org.jetbrains.annotations.Nullable;

@Module
public class JoinQuitModule {
   static boolean joinEnabled, quitEnabled;
   
   @Nullable
   static String joinMessage, quitMessage;
   
   @Nullable
   static ActionSection onJoin, onQuit;
   
   public static void load() {
      joinEnabled = Config.config.getBoolean("join-message.enabled", false);
      
      quitEnabled = Config.config.getBoolean("quit-message.enabled", false);
      
      if(joinEnabled)
         joinMessage = Config.config.getString("join-message.message", null);
      
      if(quitEnabled)
         quitMessage = Config.config.getString("quit-message.message", null);
      
      Main.pm.registerEvents(new JoinQuitListener(), Main.plugin);
      
      var joinSection = EventsFile.config.getConfigurationSection("on-join");
      var quitSection = EventsFile.config.getConfigurationSection("on-quit");
      
      if(joinSection != null)
         onJoin = ActionSection.of(joinSection);
      
      if(quitSection != null)
         onQuit = ActionSection.of(quitSection);
   }
}