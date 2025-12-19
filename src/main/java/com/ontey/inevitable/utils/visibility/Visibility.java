package com.ontey.inevitable.utils.visibility;

import com.ontey.inevitable.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class Visibility {
   
   public static void setVisibility(Player player, Collection<? extends Player> players, boolean flag) {
      for(Player other : players) {
         if(flag)
            other.showPlayer(Main.plugin, player);
         else
            other.hidePlayer(Main.plugin, player);
      }
   }
   
   public static void hidePlayer(Player player, Collection<? extends Player> players) {
      for(Player other : players)
         other.hidePlayer(Main.plugin, player);
   }
   
   public static void showPlayer(Player player, Collection<? extends Player> players) {
      for(Player other : players)
         other.showPlayer(Main.plugin, player);
   }
   
   public static void hidePlayer(Player player) {
      hidePlayer(player, Bukkit.getOnlinePlayers());
   }
   
   public static void showPlayer(Player player) {
      showPlayer(player, Bukkit.getOnlinePlayers());
   }
}
