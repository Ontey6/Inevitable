package com.ontey.commands.gui;

import com.ontey.api.command.Command;
import com.ontey.api.command.ConfigCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

import static com.ontey.api.utils.Utils.PLAYER_REGEX;

public class EnderchestCommand {
   
   private static final ConfigCommand.Defaults DEFAULTS = new ConfigCommand.Defaults(
     "enderchest",
     "Opens the target's enderchest for the player",
     "/<command> [target] [player]",
     "inevitable.command.enderchest",
     List.of("ec"),
     Map.of()
   );
   
   public static void register() {
      ConfigCommand command = new ConfigCommand("inevitable", "enderchest", DEFAULTS);
      
      Command cmd = command.cmd;
      
      cmd.setRuntimeTab(
        1,
        (sender, label, args) -> sender.isOp() ? Command.players() : List.of(),
        ""
      );
      cmd.setRuntimeTab(
        2,
        (sender, label, args) -> sender.isOp() ? Command.players() : List.of(),
        PLAYER_REGEX
      );
      
      cmd.setSignature("", (sender, label, args) -> {
         if(!(sender instanceof final Player player)) {
            sender.sendMessage("§cConsoles have to specify target and player!");
            return;
         }
         
         openEnderchest(player, player);
      });
      
      cmd.setSignature(PLAYER_REGEX, (sender, label, args) -> {
         if(!(sender instanceof final Player player)) {
            sender.sendMessage("§cConsoles have to specify a player!");
            return;
         }
         
         Player target = Bukkit.getPlayer(args[0]);
         
         if(target == null) {
            sender.sendMessage("§cPlayer not found!");
            return;
         }
         
         openEnderchest(target, player);
      });
      
      cmd.setSignature(PLAYER_REGEX + " " + PLAYER_REGEX, (sender, label, args) -> {
         Player target = Bukkit.getPlayer(args[0]);
         Player player = Bukkit.getPlayer(args[1]);
         
         if(target == null) {
            sender.sendMessage("§cTarget not found!");
            return;
         }
         
         if(player == null) {
            sender.sendMessage("§cPlayer not found!");
            return;
         }
      });
      
      command.register();
   }
   
   private static void openEnderchest(Player target, Player player) {
      player.openInventory(target.getEnderChest());
   }
}
