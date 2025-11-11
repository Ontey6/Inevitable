package com.ontey.api.command;

import com.ontey.files.Commands;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static com.ontey.files.Commands.config;
import static com.ontey.files.Files.*;

public class ConfigCommand {
   
   private ConfigurationSection section;
   
   @Nullable
   public final ConfigurationSection options;
   
   public final Command cmd;
   
   public ConfigCommand(String namespace, String name, Defaults defaults) {
      this.section = config.getConfigurationSection("commands." + name);
      
      if(section == null)
         this.section = Commands.createSection("commands." + name, defaults.toMap());
      
      cmd = new Command(namespace, section.getString("name", name));
      options = section.getConfigurationSection("options");
   }
   
   public void register() {
      if(!section.getBoolean("enabled", false))
         return;
      
      cmd.setDescription(getMessage(section, "description"));
      cmd.setUsage(getMessage(section, "usage"));
      cmd.setPermission(section.getString("permission"));
      cmd.setAliases(getListable(section, "aliases"));
      
      cmd.register();
   }
   
   public record Defaults(String name, String description, String usage, String permission, List<String> aliases, Map<String, ?> options) {
      public Map<String, ?> toMap() {
         return Map.of(
           "enabled", true,
           "name", name,
           "aliases", aliases,
           "permission", permission,
           "description", description,
           "usage", usage,
           "options", options
         );
      }
   }
}
