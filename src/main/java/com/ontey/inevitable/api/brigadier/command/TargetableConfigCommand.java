package com.ontey.inevitable.api.brigadier.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.ontey.inevitable.api.brigadier.argument.Arg;
import com.ontey.inevitable.api.brigadier.config.CommandConfiguration;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * A util to easily make commands like {@code /kill} (kills yourself in-game) targetable.
 * <p>
 * That just means, you could also run {@code /kill <player>} to kill another player.
 * <p>
 * It is easy to set up, usually just one line:
 *
 * <blockquote>For example,
 * <pre>
 * {@code
 *    new BrigadierTargetableConfigCommand(defaults, p -> p.setHealth(0)).register();
 *    // A command just like described above
 * }
 * </pre>
 * </blockquote>
 */

public class TargetableConfigCommand extends ConfigCommand {
   
   @SneakyThrows
   public TargetableConfigCommand(@NotNull CommandConfiguration defaults, @NotNull Consumer<Player> action) {
      super(defaults);
      
      super.setRoot(makeRoot(action));
   }
   
   private LiteralArgumentBuilder<CommandSourceStack> makeRoot(Consumer<Player> action) {
      if(values.names().isEmpty())
         return null;
      
      return
        Arg.literal(values.names().getFirst())
         .executes(ctx -> Arg.requirePlayer(ctx, options, action))
         
         .then(
           Arg.playersArg("player")
             .executes(ctx -> Arg.runForPlayers(Arg.getPlayers("player", ctx), action))
             .requires(src -> src.getSender().hasPermission(options.get("target-permission", values.permission() + ".target"))));
   }
}
