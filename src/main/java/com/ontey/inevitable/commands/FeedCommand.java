package com.ontey.inevitable.commands;

import com.ontey.inevitable.api.brigadier.command.TargetableConfigCommand;
import com.ontey.inevitable.api.brigadier.config.CommandConfiguration;

import java.util.List;
import java.util.Map;

public class FeedCommand {
   
   private static final CommandConfiguration defaults = new CommandConfiguration(
     List.of("feed"),
     "Fills your hunger",
     "inevitable.command.feed",
     Map.of()
   );
   
   public static void register() {
      new TargetableConfigCommand(defaults, p -> {
         p.setFoodLevel(20);
         p.setSaturation(20f);
      }).register();
   }
}
