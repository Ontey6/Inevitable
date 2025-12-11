package com.ontey.inevitable.module.modules.chatformat;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static com.ontey.inevitable.module.modules.chatformat.ChatFormatModule.*;

public class ChatListener implements Listener {
   
   @EventHandler
   public void onChat(AsyncChatEvent event) {
      if(!enabled)
         return;
      
      event.renderer((source, sourceDisplayName, message, viewer) ->
        chatFormat.apply(source, sourceDisplayName, viewer, message)
      );
   }
}
