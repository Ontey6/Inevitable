package com.ontey.commands.gui;

import com.ontey.api.command.ConfigCommand;

import java.util.List;
import java.util.Map;

public class EnchantingCommand {
   
   private static final ConfigCommand.Defaults DEFAULTS = new ConfigCommand.Defaults(
     "enchanting",
     "Opens an enchanting table",
     "/<command> [player]",
     "inevitable.command.enchanting",
     List.of(),
     Map.of()
   );
   
   public static void register() {
      new GuiCommand("enchanting", DEFAULTS, p -> p.openEnchanting(null, true)).register();
   }
}
