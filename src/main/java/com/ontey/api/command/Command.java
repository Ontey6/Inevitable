package com.ontey.api.command;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class Command {
   
   private static CommandMap commandMap;
   
   private CommandExecution defaultExecution = (a, b, c) -> { };
   private final Map<Pattern, CommandExecution> signatures = new LinkedHashMap<>();
   
   // now supports multiple rules per argument index
   private final Map<Integer, List<TabRule>> tabCompletions = new HashMap<>();
   
   @Getter private final List<String> aliases = new ArrayList<>();
   @Getter @Setter private String permission, usage, description;
   @Getter private final String name, namespace;
   
   private final BukkitCommand command;
   
   @Runtime(initializationTime = "tab complete")
   private CommandSender sender;
   
   @Runtime(initializationTime = "tab complete")
   private String label;
   
   @Runtime(initializationTime = "tab complete")
   private String[] args;
   
   private static final String DEV_FAULT = ". This is the developer's fault, so report the issue and use an older version if this problem persists.";
   
   public Command(String namespace, String name) {
      if (commandMap == null)
         commandMap = getCommandMap();
      
      this.command = new BukkitCommand(name) {
         
         @Override
         public boolean execute(@NotNull CommandSender sender, @NotNull String label, String[] args) {
            String joinedArgs = String.join(" ", args);
            CommandExecution exe = defaultExecution;
            
            for (Map.Entry<Pattern, CommandExecution> entry : signatures.entrySet()) {
               if (entry.getKey().matcher(joinedArgs).matches()) {
                  exe = entry.getValue();
                  break;
               }
            }
            exe.execute(sender, label, args);
            return true;
         }
         
         @Override
         public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
            Command.this.sender = sender;
            Command.this.label = label;
            Command.this.args = args;
            
            int pos = args.length - 1;
            List<TabRule> rules = tabCompletions.get(pos);
            if (rules == null) return Collections.emptyList();
            
            List<String> results = new ArrayList<>();
            for (TabRule rule : rules) {
               if (!rule.condition.test(args)) continue;
               String current = args[pos].toLowerCase();
               for (String option : rule.completions.get())
                  if (option.toLowerCase().startsWith(current))
                     results.add(option);
            }
            return results;
         }
      };
      
      this.name = name;
      this.namespace = namespace;
   }
   
   public static CommandMap getCommandMap() {
      try {
         return (CommandMap) Bukkit.getServer().getClass().getMethod("getCommandMap").invoke(Bukkit.getServer());
      } catch (Exception e) {
         throw new RuntimeException("Ontey's Command API couldn't fetch CommandMap, disabling plugin", e);
      }
   }
   
   public void setDefaultExecution(CommandExecution exe) {
      Objects.requireNonNull(exe);
      this.defaultExecution = exe;
   }
   
   public void setSignature(String argsMatcher, CommandExecution exe) {
      Objects.requireNonNull(exe);
      Objects.requireNonNull(argsMatcher);
      signatures.put(Pattern.compile("^" + argsMatcher + "$", Pattern.CASE_INSENSITIVE), exe);
   }
   
   public void setAliases(String... aliases) {
      this.aliases.clear();
      Collections.addAll(this.aliases, aliases);
   }
   
   public void setAliases(List<String> aliases) {
      this.aliases.clear();
      this.aliases.addAll(aliases);
   }
   
   /**
    * Sets a tab with a constant value
    * @param pos The position of this tab completion. Starting from 1
    * @param completions The tab completion to be shown
    * @param signatures The signature that has to be typed before. Regex compatible
    */
   
   public void setTab(int pos, List<String> completions, String... signatures) {
      if (pos < 1)
         throw new InternalError("The argument position of an internal command is 0 or negative" + DEV_FAULT);
      
      Predicate<String[]> condition = args -> {
         String joinedArgs = String.join(" ", cutLast(args));
         for (String signature : signatures)
            if (joinedArgs.matches(signature))
               return true;
         return false;
      };
      
      tabCompletions.computeIfAbsent(pos - 1, k -> new ArrayList<>()).add(new TabRule(() -> completions, condition));
   }
   
   public void setRuntimeTab(int pos, Supplier<List<String>> completions, String... signatures) {
      if (pos < 1)
         throw new InternalError("The argument position of an internal command is 0 or negative" + DEV_FAULT);
      
      Predicate<String[]> condition = args -> {
         String joinedArgs = String.join(" ", cutLast(args));
         for (String signature : signatures)
            if (joinedArgs.matches(signature))
               return true;
         return false;
      };
      
      tabCompletions.computeIfAbsent(pos - 1, k -> new ArrayList<>())
        .add(new TabRule(completions, condition));
   }
   
   public void setRuntimeTab(int pos, TabExecution completions, String... signatures) {
      if (pos < 1)
         throw new InternalError("The argument position of an internal command is 0 or negative" + DEV_FAULT);
      
      Predicate<String[]> condition = args -> {
         String joinedArgs = String.join(" ", cutLast(args));
         for (String signature : signatures)
            if (joinedArgs.matches(signature))
               return true;
         return false;
      };
      
      tabCompletions.computeIfAbsent(pos - 1, k -> new ArrayList<>())
        .add(new TabRule(() -> completions.execute(sender, label, args), condition));
   }
   
   private String[] cutLast(String[] args) {
      if (args.length == 0)
         return args;
      return Arrays.copyOf(args, args.length - 1);
   }
   
   public void register() {
      command.setUsage(usage)
        .setDescription(description)
        .setAliases(aliases)
        .setPermission(permission);
      
      var cmd = commandMap.getCommand(command.getName());
      if (cmd != null)
         cmd.unregister(commandMap);
      
      commandMap.register(namespace, command);
   }
   
   public static List<String> players() {
      return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
   }
   
   private record TabRule(Supplier<List<String>> completions, Predicate<String[]> condition) { }
   
   /**
    * A Tri-Function with execution time data that determines the tab completions
    */
   
   @FunctionalInterface
   public interface TabExecution {
      List<String> execute(CommandSender sender, String label, String[] args);
   }
   
   /**
    * A Tri-Consumer with execution time data
    */
   
   @FunctionalInterface
   public interface CommandExecution {
      void execute(CommandSender sender, String label, String[] args);
   }
   
   /**
    * Marks that the annotated object is only
    * initialized at runtime when it's needed
    */
   
   private @interface Runtime {
      String initializationTime();
   }
}