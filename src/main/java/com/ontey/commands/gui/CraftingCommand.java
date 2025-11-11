package com.ontey.commands.gui;

import com.ontey.api.command.ConfigCommand;

import java.util.List;
import java.util.Map;

public class CraftingCommand {
   
   private static final ConfigCommand.Defaults DEFAULTS = new ConfigCommand.Defaults(
     "crafting",
     "Opens a crafting table",
     "/<command> [player]",
     "inevitable.command.craft",
     List.of("workbench", "wb", "craft"),
     Map.of()
   );
   
   public static void register() {
      new GuiCommand("crafting", DEFAULTS, p -> p.openWorkbench(null, true)).register();
   }
}