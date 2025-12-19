package com.ontey.inevitable.commands;

import com.ontey.inevitable.Main;
import com.ontey.inevitable.api.brigadier.argument.Arg;
import com.ontey.inevitable.api.brigadier.command.ConfigCommand;
import com.ontey.inevitable.api.brigadier.config.CommandConfiguration;
import com.ontey.inevitable.api.brigadier.registry.CommandRegistry;
import com.ontey.inevitable.files.Commands;
import com.ontey.inevitable.files.Config;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

import static com.ontey.inevitable.api.brigadier.command.Command.SUCCESS;
import static com.ontey.inevitable.api.color.Color.colorize;

public class MainCommand {
   
   private static final CommandConfiguration defaults = new CommandConfiguration(
     List.of("inevitable", "in"),
     "The main command for the Inevitable plugin",
     "inevitable.command.inevitable",
     Map.of()
   );
   
   public static void register() {
      ConfigCommand cmd = new ConfigCommand(defaults);
      
      var root = Arg.literal("inevitable")
          .executes(ctx -> {
             if(ctx.getSource().getExecutor() instanceof final CommandSender sender) {
                sender.sendMessage(colorize("<gradient:yellow:dark_purple>-------------------------------------"));
                sender.sendMessage(colorize("&bRunning Inevitable &3v" + Main.version));
                sender.sendMessage(colorize("<gradient:dark_purple:yellow>-------------------------------------"));
             }
             return SUCCESS;
          })
            .then(
              Arg.literal("reload")
                .executes(ctx -> {
                   Config.load();
                   Commands.load();
                   
                   Commands.unloadCommands();
                   Commands.loadCommands();
                   CommandRegistry.reload();
                   
                   if(ctx.getSource().getExecutor() instanceof final CommandSender sender) {
                      sender.sendMessage(colorize("&aReloaded Inevitable &bv" + Main.version));
                      sender.sendMessage(colorize("&cCurrently on v1.1, Reloading doesn't fully work. Reload or restart the server to apply changes"));
                   }
                   
                   return SUCCESS;
                })
            );
      
      cmd.setRoot(root);
      
      cmd.register();
   }
}