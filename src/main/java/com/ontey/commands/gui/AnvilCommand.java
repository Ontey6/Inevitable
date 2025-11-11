package com.ontey.commands.gui;

import com.ontey.api.command.ConfigCommand;

import java.util.List;
import java.util.Map;

public class AnvilCommand {
   
   private static final ConfigCommand.Defaults DEFAULTS = new ConfigCommand.Defaults(
     "anvil",
     "Opens an anvil",
     "/<command> [player]",
     "inevitable.command.anvil",
     List.of(),
     Map.of()
   );
   
   public static void register() {
      new GuiCommand("anvil", DEFAULTS, p -> p.openAnvil(null, true)).register();
   }
}
