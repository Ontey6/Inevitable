package com.ontey.commands.gui;

import com.ontey.api.command.ConfigCommand;

import java.util.List;
import java.util.Map;

public class StonecutterCommand {
   
   private static final ConfigCommand.Defaults DEFAULTS = new ConfigCommand.Defaults(
     "stonecutter",
     "Opens a stonecutter",
     "/<command> [player]",
     "inevitable.command.stonecutter",
     List.of(),
     Map.of()
   );
   
   public static void register() {
      new GuiCommand("stonecutter", DEFAULTS, p -> p.openStonecutter(null, true)).register();
   }
}
