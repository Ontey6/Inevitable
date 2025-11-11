package com.ontey.commands.gui;

import com.ontey.api.command.Command;
import com.ontey.api.command.ConfigCommand;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

import static com.ontey.api.utils.Utils.PLAYER_REGEX;

@AllArgsConstructor
class GuiCommand {
   
   private String name;
   
   private ConfigCommand.Defaults DEFAULTS;
   
   private Consumer<Player> open;
   
   public void register() {
      ConfigCommand command = new ConfigCommand("inevitable", name, DEFAULTS);
      
      Command cmd = command.cmd;
      
      cmd.setRuntimeTab(
        1,
        (sender, label, args) -> sender.isOp() ? Command.players() : List.of(),
        ""
      );
      
      cmd.setSignature("", (sender, label, args) -> {
         if(!(sender instanceof final Player player)) {
            sender.sendMessage("§cConsoles have to specify a player!");
            return;
         }
         
         open.accept(player);
      });
      
      cmd.setSignature(PLAYER_REGEX, (sender, label, args) -> {
         Player player = Bukkit.getPlayer(args[0]);
         
         if(player == null) {
            sender.sendMessage("§cPlayer not found!");
            return;
         }
         
         open.accept(player);
      });
      
      command.register();
   }
}
