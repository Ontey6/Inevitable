package com.ontey.inevitable.module.modules.joinquit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.ontey.inevitable.module.modules.joinquit.JoinQuitModule.*;

import static com.ontey.inevitable.formatting.Format.format;

public class JoinQuitListener implements Listener {
   
   @EventHandler
   public void onJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      
      if(joinEnabled)
         event.joinMessage(format(joinMessage, player));
      
      if(onJoin != null)
         onJoin.execute(player);
   }
   
   @EventHandler
   public void onQuit(PlayerQuitEvent event) {
      Player player = event.getPlayer();
      
      if(quitEnabled)
         event.quitMessage(format(quitMessage, player));
      
      if(onQuit != null)
         onQuit.execute(player);
   }
}