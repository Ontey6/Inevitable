package com.ontey.commands;

import com.ontey.api.command.ConfigCommand;
import com.ontey.api.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

import static com.ontey.api.utils.Utils.PLAYER_REGEX;
import static com.ontey.formatting.Format.format;

public class FlyCommand {
   
   private static final ConfigCommand.Defaults DEFAULTS = new ConfigCommand.Defaults(
     "fly",
     "Toggles whether you can fly",
     "/<command> [on|off|toggle] [player]",
     "inevitable.command.fly",
     List.of(),
     Map.of(
       "message", "&aToggled your flight &e%flag"
     )
   );
   
   public static void register() {
      ConfigCommand command = new ConfigCommand("inevitable", "fly", DEFAULTS);
      
      Command cmd = command.cmd;
      
      cmd.setTab(1, List.of("on", "off", "toggle"), "");
      cmd.setRuntimeTab(2, () -> Bukkit.getOnlinePlayers().stream().map(Player::getName).toList(), "on|off|toggle");
      
      cmd.setDefaultExecution((sender, label, args) ->
        sender.sendMessage("§cInvalid Sub-Command")
      );
      
      cmd.setSignature("()|toggle", (sender, label, args) -> {
         if(!(sender instanceof final Player player)) {
            sender.sendMessage("Consoles have to specify action and player!");
            return;
         }
         
         setFlight(player, !player.getAllowFlight(), command);
      });
      
      cmd.setSignature("on|off", (sender, label, args) -> {
         if(!(sender instanceof final Player player)) {
            sender.sendMessage("Consoles have to specify action and player!");
            return;
         }
         
         switch(args[0]) {
            case "on" -> setFlight(player, true, command);
            case "off" -> setFlight(player, false, command);
         }
      });
      
      cmd.setSignature("(on|off|toggle) " + PLAYER_REGEX, (sender, label, args) -> {
         Player player = Bukkit.getPlayer(args[1]);
         
         if(player == null) {
            sender.sendMessage("§eCan't find the specified player!");
            return;
         }
         
         switch(args[0]) {
            case "on" -> setFlight(player, true, command);
            case "off" -> setFlight(player, false, command);
            case "toggle" -> setFlight(player, !player.getAllowFlight(), command);
         }
      });
      
      command.register();
   }
   
   private static void setFlight(Player player, boolean flag, ConfigCommand command) {
      player.setAllowFlight(flag);
      
      if(command.options == null)
         return;
      
      String msg = command.options.getString("message");
      if(msg != null)
         player.sendMessage(format(msg.replace("%flag", flag ? "on" : "off"), player));
   }
}
