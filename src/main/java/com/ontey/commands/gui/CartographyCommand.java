package com.ontey.commands.gui;

import com.ontey.api.command.ConfigCommand;

import java.util.List;
import java.util.Map;

public class CartographyCommand {
   
   private static final ConfigCommand.Defaults DEFAULTS = new ConfigCommand.Defaults(
     "cartography",
     "Opens a cartography table",
     "/<command> [player]",
     "inevitable.command.cartography",
     List.of(),
     Map.of()
   );
   
   public static void register() {
      new GuiCommand("cartography", DEFAULTS, p -> p.openCartographyTable(null, true)).register();
   }
}
