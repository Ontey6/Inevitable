package com.ontey.inevitable.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.ontey.inevitable.api.brigadier.argument.Arg;
import com.ontey.inevitable.api.brigadier.command.ConfigCommand;
import com.ontey.inevitable.api.brigadier.config.CommandConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

import static com.ontey.inevitable.api.brigadier.command.Command.SUCCESS;

public class EnderchestCommand {
   
   private static final CommandConfiguration defaults = new CommandConfiguration(
     List.of("enderchest", "ec"),
     "Opens your enderchest",
     "inevitable.command.enderchest",
     Map.of("message", "Opened your enderchest")
   );
   
   public static void register() {
      ConfigCommand cmd = new ConfigCommand(defaults);
      
      var root =
        Arg.literal("enderchest")
          .executes(ctx -> Arg.requirePlayer(ctx, cmd.options, p -> openEnderchest(p, p)))
      
          .then(
            Arg.playerArg("holder")
              .executes(ctx -> Arg.requirePlayer(ctx, cmd.options, p -> {
                 try {
                    openEnderchest(Arg.getPlayer("holder", ctx), p);
                 } catch(CommandSyntaxException e) { }
              }))
              .requires(src ->
                src.getSender()
                  .hasPermission(cmd.options.get("permissions.open-others", cmd.values.permission() + ".openothers"))
              )
            
              .then(
                Arg.playersArg("target")
                  .executes(ctx -> openEnderchest(Arg.getPlayers("target", ctx), Arg.getPlayer("holder", ctx)))
                  .requires(src ->
                    src.getSender()
                      .hasPermission(cmd.options.get("permissions.target", cmd.values.permission() + ".target"))
                )));
      
      cmd
        .setRoot(root)
        .register();
   }
   
   private static void openEnderchest(Player target, Player player) {
      target.openInventory(player.getEnderChest());
   }
   
   private static int openEnderchest(List<Player> targets, Player player) {
      for(Player target : targets)
         target.openInventory(player.getEnderChest());
      
      return SUCCESS;
   }
}
