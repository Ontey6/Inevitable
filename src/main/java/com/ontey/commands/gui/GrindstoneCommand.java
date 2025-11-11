package com.ontey.commands.gui;

import com.ontey.api.command.ConfigCommand;

import java.util.List;
import java.util.Map;

public class GrindstoneCommand {
   
   private static final ConfigCommand.Defaults DEFAULTS = new ConfigCommand.Defaults(
     "grindstone",
     "Opens a grindstone",
     "/<command> [player]",
     "inevitable.command.grindstone",
     List.of(),
     Map.of()
   );
   
   public static void register() {
      new GuiCommand("grindstone", DEFAULTS, p -> p.openGrindstone(null, true)).register();
   }
}
