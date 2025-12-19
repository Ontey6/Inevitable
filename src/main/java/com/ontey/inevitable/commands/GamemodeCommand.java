package com.ontey.inevitable.commands;

import com.mojang.brigadier.context.CommandContext;
import com.ontey.inevitable.api.brigadier.argument.Arg;
import com.ontey.inevitable.api.brigadier.command.ConfigCommand;
import com.ontey.inevitable.api.brigadier.config.CommandConfiguration;
import com.ontey.inevitable.api.brigadier.config.CommandOptions;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

import static com.ontey.inevitable.api.brigadier.command.Command.SUCCESS;
import static com.ontey.inevitable.formatting.Format.format;

public class GamemodeCommand {
   
   private static final CommandConfiguration defaults = new CommandConfiguration(
     List.of("gamemode", "gm"),
     "Change your gamemode easily",
     "inevitable.command.gamemode",
     Map.of()
   );
   
   public static void register() {
      ConfigCommand cmd = new ConfigCommand(defaults);
      
      var root =
        Arg.literal("gamemode")
          .then(
            Arg.gamemodeArg("gamemode")
              .executes(ctx ->
                Arg.requirePlayer(ctx, cmd.options, p -> setGamemode(List.of(p), ctx, cmd.options)))
              .then(
                Arg.playersArg("target")
                  .executes(ctx -> setGamemode(Arg.getPlayers("target", ctx), ctx, cmd.options))
              )
          );
      
      cmd
        .setRoot(root)
        .register();
   }
   
   private static int setGamemode(List<Player> players, CommandContext<CommandSourceStack> ctx, CommandOptions options) {
      for(Player player : players) {
         GameMode gm = ctx.getArgument("gamemode", GameMode.class);
         player.setGameMode(gm);
         
         String msg = options.getMessage("message", "Set your gamemode to &e" + gm.toString().toLowerCase());
         
         if(msg != null)
            player.sendMessage(format(msg.replace("%gm", gm.toString().toLowerCase()), player));
      }
      
      return SUCCESS;
   }
}
