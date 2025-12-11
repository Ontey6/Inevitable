package com.ontey.inevitable.commands;

import com.ontey.api.brigadier.command.TargetableConfigCommand;
import com.ontey.api.brigadier.config.CommandConfiguration;

import java.util.List;
import java.util.Map;

public class HealCommand {
   
   private static final CommandConfiguration defaults = new CommandConfiguration(
     List.of("heal"),
     "Heals you",
     "inevitable.command.heal",
     Map.of()
   );
   
   public static void register() {
      new TargetableConfigCommand(defaults, p -> p.setHealth(20)).register();
   }
}
