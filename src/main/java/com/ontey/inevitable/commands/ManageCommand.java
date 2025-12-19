package com.ontey.inevitable.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.ontey.inevitable.api.brigadier.argument.Arg;
import com.ontey.inevitable.api.brigadier.command.ConfigCommand;
import com.ontey.inevitable.api.brigadier.config.CommandConfiguration;
import com.ontey.inevitable.api.brigadier.config.CommandOptions;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.ontey.inevitable.api.brigadier.command.Command.SUCCESS;
import static com.ontey.inevitable.formatting.Format.format;

public class ManageCommand {
   
   private static final CommandConfiguration defaults = new CommandConfiguration(
     List.of("manage"),
     "Manage a player",
     "inevitable.command.manage",
     Map.of()
   );
   
   public static void register() {
      ConfigCommand cmd = new ConfigCommand(defaults);
      
      var root =
        Arg.literal("manage")
          .then(
            Arg.playerArg("target")
              .then(
                Arg.literal("set")
                  .then(
                    Arg.literal("health")
                      .then(Arg.doubleArg("value", 0)
                        .executes(ctx -> operation(ctx, cmd.options, "health", Player::setHealth))))
                  .then(
                    Arg.literal("saturation")
                      .then(Arg.doubleArg("value")
                        .executes(ctx -> operation(ctx, cmd.options, "saturation", Player::setSaturation))))
                  .then(
                    Arg.literal("food_level")
                      .then(
                        Arg.intArg("value")
                          .executes(ctx -> operation(ctx, cmd.options, "food_level", Player::setFoodLevel))))
              )
              .then(
                Arg.literal("query")
                  .then(
                    Arg.literal("health")
                      .executes(ctx -> (int) Arg.getPlayer("target", ctx).getHealth()))
                  .then(
                    Arg.literal("saturation")
                      .executes(ctx -> (int) Arg.getPlayer("target", ctx).getSaturation()))
                  .then(
                    Arg.literal("food_level")
                      .executes(ctx -> Arg.getPlayer("target", ctx).getFoodLevel()))
              )
              .then(
                Arg.literal("get")
                  .then(
                    Arg.literal("health")
                      .executes(ctx -> get(ctx, cmd.options, "health", Player::getHealth)))
                  .then(
                    Arg.literal("saturation")
                      .executes(ctx -> get(ctx, cmd.options, "saturation", Player::getSaturation)))
                  .then(
                    Arg.literal("food_level")
                      .executes(ctx -> get(ctx, cmd.options, "food level", Player::getFoodLevel)))
              )
          );
      
      cmd
        .setRoot(root)
        .register();
   }
   
   private static int get(CommandContext<CommandSourceStack> ctx, CommandOptions options, String operation, Function<Player, Object> action) throws CommandSyntaxException {
      CommandSender receiver = ctx.getSource().getSender();
      Player target = Arg.getPlayer("target", ctx);
      Object value = action.apply(Arg.getPlayer("target", ctx));
      
      sendGetterMessage(receiver, options, target, operation, value);
      return SUCCESS;
   }
   
   private static int operation(CommandContext<CommandSourceStack> ctx, CommandOptions options, String operation, BiConsumer<Player, Integer> action) throws CommandSyntaxException {
      Player target = Arg.getPlayer("target", ctx);
      int value = ctx.getArgument("value", int.class);
      
      action.accept(target, value);
      sendSetterMessage(options, target, operation, value);
      
      return SUCCESS;
   }
   
   private static void sendSetterMessage(CommandOptions options, Player target, String operation, Object value) {
      sendMessage("messages.setter", "Set &e%target's&r %operation&r to &e%value&r", target, options, target, operation, value);
   }
   
   private static void sendGetterMessage(CommandSender sender, CommandOptions options, Player target, String operation, Object value) {
      sendMessage("messages.getter", "Value of &e%target's&r %operation&r is &e%value&r", sender, options, target, operation, value);
   }
   
   private static void sendMessage(String path, String def, CommandSender sendTo, CommandOptions options, Player target, String operation, Object value) {
      String msg = options.getMessage(path, def);
      
      if(msg != null)
         sendTo.sendMessage(format(msg
           .replace("%target", target.getName())
           .replace("%operation", operation)
           .replace("%value", value.toString()), target));
   }
}
