package com.ontey.api.brigadier.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ontey.api.brigadier.config.CommandConfiguration;
import com.ontey.api.brigadier.config.CommandOptions;
import com.ontey.api.files.Files;
import com.ontey.inevitable.files.Commands;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import lombok.SneakyThrows;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ConfigCommand extends Command {
   
   public final CommandConfiguration defaults, values;
   
   public final CommandOptions options;
   
   public ConfigCommand(@NotNull CommandConfiguration defaults) {
      // defaults should always have more than 1 name anyway
      super(defaults.names().getFirst()); // why can't I just use java 22...
      
      String name = defaults.names().getFirst();
      
      var section = Commands.config.getConfigurationSection(name);
      
      if(section == null)
         section = Commands.createSection(name, defaults.serialize());
      
      this.defaults = defaults;
      
      this.values = new CommandConfiguration(
        Files.getListable(section, "names"),
        Files.getMessage(section, "description", defaults.description()),
        section.getString("permission", defaults.permission()),
        options(section)
      );
      
      this.options = new CommandOptions(section.getConfigurationSection("options"));
      
      if(!getNames().isEmpty())
         getNames().remove(getName());
      
      super.getNames().clear();
      
      super
        .setDescription(values.description())
        .setPermission(values.permission())
        .addAliases(values.names());
   }
   
   @Override
   @SneakyThrows
   public ConfigCommand setRoot(LiteralArgumentBuilder<CommandSourceStack> root) {
      if(values.names().isEmpty())
         return this;
      
      final LiteralCommandNode<CommandSourceStack> node = new LiteralCommandNode<>(
        values.names().getFirst(),
        root.getCommand(),
        root.getRequirement(),
        root.getRedirect(),
        root.getRedirectModifier(),
        root.isFork()
      );
      
      for(var arg : root.getArguments())
         node.addChild(arg);
      
      this.root = node;
      
      return this;
   }
   
   private static Map<String, Object> options(ConfigurationSection section) {
      var opt = section.getConfigurationSection("options");
      
      return opt == null ? Map.of() : opt.getValues(false);
   }
}
