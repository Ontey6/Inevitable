package com.ontey.commands.gui;

import com.ontey.api.command.ConfigCommand;

import java.util.List;
import java.util.Map;

public class LoomCommand {
   
   private static final ConfigCommand.Defaults DEFAULTS = new ConfigCommand.Defaults(
     "loom",
     "Opens a loom",
     "/<command> [player]",
     "inevitable.command.loom",
     List.of(),
     Map.of()
   );
   
   public static void register() {
      new GuiCommand("loom", DEFAULTS, p -> p.openLoom(null, true)).register();
   }
}
