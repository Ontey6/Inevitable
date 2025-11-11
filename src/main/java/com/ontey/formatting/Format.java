package com.ontey.formatting;

import com.ontey.api.color.Color;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class Format {
   public static Component format(String str, Player player) {
      if(str == null)
         return Component.empty();
      
      str = plainFormat(str, player);
      return Color.colorize(str);
   }
   
   public static String plainFormat(String str, Player player) {
      str = Holder.apply(str, player);
      str = PlaceholderAPI.setPlaceholders(player, str);
      
      return str;
   }
}
