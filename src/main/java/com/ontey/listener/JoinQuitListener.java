package com.ontey.listener;

import com.ontey.files.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.ontey.formatting.Format.format;

public class JoinQuitListener implements Listener {
   
   @EventHandler
   public void onJoin(PlayerJoinEvent event) {
      event.joinMessage(format(Config.JOIN_MESSAGE, event.getPlayer()));
      Config.ON_JOIN_ACTION.execute(event.getPlayer());
   }
   
   @EventHandler
   public void onQuit(PlayerQuitEvent event) {
      event.quitMessage(format(Config.QUIT_MESSAGE, event.getPlayer()));
      Config.ON_QUIT_ACTION.execute(event.getPlayer());
   }
}
