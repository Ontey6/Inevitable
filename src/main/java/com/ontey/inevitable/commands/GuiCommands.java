package com.ontey.inevitable.commands;

import com.ontey.inevitable.api.brigadier.command.TargetableConfigCommand;
import com.ontey.inevitable.api.brigadier.config.CommandConfiguration;

import java.util.List;
import java.util.Map;

/**
 * A class that holds all targetable GUI commands
 */

public class GuiCommands {
   
   public static class AnvilCommand {
      
      private static final CommandConfiguration config = new CommandConfiguration(
        List.of("anvil"),
        "Opens an anvil",
        "inevitable.command.anvil",
        Map.of()
      );
      
      public static void register() {
         new TargetableConfigCommand(config, p -> p.openAnvil(null, true)).register();
      }
   }
   
   public static class CartographyCommand {
      
      private static final CommandConfiguration config = new CommandConfiguration(
        List.of("cartography"),
        "Opens a cartography table",
        "inevitable.command.cartography",
        Map.of()
      );
      
      public static void register() {
         new TargetableConfigCommand(config, p -> p.openCartographyTable(null, true)).register();
      }
   }
   
   public static class CraftingCommand {
      
      private static final CommandConfiguration config = new CommandConfiguration(
        List.of("crafting", "workbench", "wb", "craft"),
        "Opens a crafting table",
        "inevitable.command.craft",
        Map.of()
      );
      
      public static void register() {
         new TargetableConfigCommand(config, p -> p.openWorkbench(null, true)).register();
      }
   }
   
   public static class EnchantingCommand {
      
      private static final CommandConfiguration config = new CommandConfiguration(
        List.of("enchanting"),
        "Opens an enchanting table",
        "inevitable.command.enchanting",
        Map.of()
      );
      
      public static void register() {
         new TargetableConfigCommand(config, p -> p.openEnchanting(null, true)).register();
      }
   }
   
   public static class GrindstoneCommand {
      
      private static final CommandConfiguration config = new CommandConfiguration(
        List.of("grindstone"),
        "Opens a grindstone",
        "inevitable.command.grindstone",
        Map.of()
      );
      
      public static void register() {
         new TargetableConfigCommand(config, p -> p.openGrindstone(null, true)).register();
      }
   }
   
   public static class LoomCommand {
      
      private static final CommandConfiguration config = new CommandConfiguration(
        List.of("loom"),
        "Opens a loom",
        "inevitable.command.loom",
        Map.of()
      );
      
      public static void register() {
         new TargetableConfigCommand(config, p -> p.openLoom(null, true)).register();
      }
   }
   
   public static class SmithingCommand {
      
      private static final CommandConfiguration config = new CommandConfiguration(
        List.of("smithing"),
        "Opens a smithing table",
        "inevitable.command.smithing",
        Map.of()
      );
      
      public static void register() {
         new TargetableConfigCommand(config, p -> p.openSmithingTable(null, true)).register();
      }
   }
   
   public static class StonecutterCommand {
      
      private static final CommandConfiguration config = new CommandConfiguration(
        List.of("stonecutter"),
        "Opens a stonecutter",
        "inevitable.command.stonecutter",
        Map.of()
      );
      
      public static void register() {
         new TargetableConfigCommand(config, p -> p.openStonecutter(null, true)).register();
      }
   }
}
