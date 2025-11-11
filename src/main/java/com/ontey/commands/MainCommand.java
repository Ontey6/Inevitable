package com.ontey.commands;

import com.ontey.api.command.Command;
import com.ontey.files.Config;

import java.util.List;

public class MainCommand {
   
   public static void register() {
      Command cmd = new Command("inevitable", "inevitable");
      
      cmd.setDescription("The main command of the inevitable plugin");
      cmd.setPermission("inevitable.command.inevitable");
      cmd.setUsage("/<command> reload");
      cmd.setAliases("in");
      
      cmd.setTab(1, List.of("reload"), "");
      
      cmd.setDefaultExecution((sender, label, args) ->
        sender.sendMessage("§cInvalid Sub-Command")
      );
      
      cmd.setSignature("reload|rel", (sender, label, args) -> {
         Config.load();
         sender.sendMessage("§aReloaded Inevitable");
      });
      
      cmd.register();
   }
}
