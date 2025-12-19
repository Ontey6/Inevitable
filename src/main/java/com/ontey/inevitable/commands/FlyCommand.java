package com.ontey.inevitable.commands;

import com.ontey.inevitable.api.brigadier.argument.Arg;
import com.ontey.inevitable.api.brigadier.command.ConfigCommand;
import com.ontey.inevitable.api.brigadier.config.CommandConfiguration;
import com.ontey.inevitable.api.brigadier.config.CommandOptions;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static com.ontey.inevitable.api.brigadier.command.Command.FAIL;
import static com.ontey.inevitable.api.brigadier.command.Command.SUCCESS;
import static com.ontey.inevitable.formatting.Format.format;

public class FlyCommand {
   
   private static final CommandConfiguration defaults = new CommandConfiguration(
     List.of("fly"),
     "Toggles your flight",
     "inevitable.command.fly",
     Map.of("message", "&aToggled your flight &e%flag")
   );
   
   public static void register() {
      var cmd = new ConfigCommand(defaults);
      
      var root =
        Arg.literal("fly")
         .executes(ctx -> setFlight(ctx.getSource(), null, cmd.options))
         
         .then(
           Arg.literal("on")
             .executes(ctx -> setFlight(ctx.getSource(), true, cmd.options))
             
             .then(
               Arg.playersArg("player")
                 .executes(ctx -> setFlight(Arg.getPlayers("player", ctx), true, cmd.options))))
          
         .then(
           Arg.literal("off")
             .executes(ctx -> setFlight(ctx.getSource(), false, cmd.options))
             
             .then(
               Arg.playersArg("player")
                 .executes(ctx -> setFlight(Arg.getPlayers("player", ctx), false, cmd.options))));
      
      cmd
        .setRoot(root)
        .register();
   }
   
   private static int setFlight(@NotNull CommandSourceStack source, @Nullable Boolean flag, @NotNull CommandOptions options) {
      if(!(source.getExecutor() instanceof final Player player)) {
         options.sendMessage(source, "messages.incapable-executor", "The executed on entity can't run this command!");
         return FAIL;
      }
      
      if(flag == null)
         flag = !player.getAllowFlight();
      
      return setFlight(List.of(player), flag, options);
   }
   
   private static int setFlight(@NotNull List<Player> players, boolean flag, @NotNull CommandOptions options) {
      for(Player player : players) {
         player.setAllowFlight(flag);
         
         String msg = options.get("message", null);
         if(msg != null)
            player.sendMessage(format(msg.replace("%flag", flag ? "on" : "off"), player));
      }
      
      return SUCCESS;
   }
}