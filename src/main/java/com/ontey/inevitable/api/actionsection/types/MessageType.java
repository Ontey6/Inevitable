package com.ontey.inevitable.api.actionsection.types;

import com.ontey.inevitable.formatting.Format;
import com.ontey.inevitable.formatting.Variable;
import de.themoep.minedown.adventure.MineDown;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class MessageType {
   
   public static Component formatMessage(String msg, Player player) {
      if(msg.startsWith(Variable.phFormat("minedown")) || msg.startsWith(Variable.phFormat("md")))
         return MineDown.parse(Format.plainFormat(msg, player));
      
      return Format.format(msg, player);
   }
}
