package com.ontey.commands.gui;

import com.ontey.api.command.ConfigCommand;

import java.util.List;
import java.util.Map;

public class SmithingCommand {
   
   private static final ConfigCommand.Defaults DEFAULTS = new ConfigCommand.Defaults(
     "smithing",
     "Opens a smithing table",
     "/<command> [player]",
     "inevitable.command.smithing",
     List.of(),
     Map.of()
   );
   
   public static void register() {
      new GuiCommand("smithing", DEFAULTS, p -> p.openSmithingTable(null, true)).register();
   }
}
