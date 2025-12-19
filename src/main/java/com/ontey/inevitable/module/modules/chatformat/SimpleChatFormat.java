package com.ontey.inevitable.module.modules.chatformat;

import com.ontey.inevitable.api.color.Color;
import com.ontey.inevitable.formatting.Placeholder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;

import java.util.List;

import static com.ontey.inevitable.formatting.Format.format;
import static com.ontey.inevitable.module.modules.chatformat.ChatFormatModule.format;

public class SimpleChatFormat {
   
   public Component apply(Player sender, Component senderDisplayName, Audience recipient, Component message) {
      final List<Placeholder> vars = List.of(
        Placeholder.ph("player", sender.getName()),
        Placeholder.ph("player.display-name", str(senderDisplayName)),
        Placeholder.ph("recipient", recipient instanceof Player p ? p.getName() : "console"),
        Placeholder.ph("message", PlainTextComponentSerializer.plainText().serialize(message))
      );
      
      String out = format;
      
      for(var var : vars)
         out = var.apply(out);
      
      return format(out, sender);
   }
   
   public String str(Component cmp) {
      return Color.mm.serializeOrNull(cmp);
   }
}
