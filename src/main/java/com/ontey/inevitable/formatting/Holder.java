package com.ontey.inevitable.formatting;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Holder {
   private static final List<Variable> variables = new ArrayList<>();
   
   private static void load(Player p, boolean extended) {
      variables.clear();
      
      // basic placeholders
      
      variables.addAll(List.of(
        ph("player.name", p.getName()),
        ph("player", p.getName())
      ));
      
      if(!extended)
         return;
      
      // extended placeholders
      
      //noinspection DataFlowIssue
      variables.addAll(List.of(
        ph("player.isOp", p.isOp()),
        ph("player.isSleeping", p.isSleeping()),
        ph("player.hasEmptyInventory", p.getInventory().isEmpty()),
        ph("player.xp-levels", p.getLevel()),
        ph("player.xp-cooldown", p.getExpCooldown()),
        ph("player.xp-to-level", p.getExpToLevel()),
        ph("player.xp-total", p.getTotalExperience()),
        ph("player.xp", p.getExp()),
        ph("player.health", p.getHealth()),
        ph("player.max-health", safe(() -> p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue())),
        ph("player.uuid", p.getUniqueId().toString()),
        ph("player.saturation", p.getSaturation()),
        ph("player.exhaustion", p.getExhaustion()),
        ph("player.world", p.getWorld().getName()),
        ph("player.biome", p.getWorld().getBiome(p.getLocation()).toString()),
        ph("player.flySpeed", p.getFlySpeed()),
        ph("player.flyingSpeed", p.getFlySpeed()),
        ph("player.walkSpeed", p.getWalkSpeed()),
        ph("player.walkingSpeed", p.getWalkSpeed()),
        ph("player.world-uuid", p.getWorld().getUID().toString()),
        ph("player.x-precise", p.getX()),
        ph("player.y-precise", p.getY()),
        ph("player.z-precise", p.getZ()),
        ph("player.x", (int) p.getX()),
        ph("player.y", (int) p.getY()),
        ph("player.z", (int) p.getZ()),
        ph("player.direction", getDirection(p.getYaw())),
        ph("player.yaw", p.getYaw()),
        ph("player.pitch", p.getPitch()),
        ph("player.ping", p.getPing()),
        ph("player.can-fly", p.getAllowFlight()),
        ph("player.client", p.getClientBrandName()),
        ph("player.view-distance", p.getClientViewDistance()),
        ph("player.locale", p.getClientOption(ClientOption.LOCALE)),
        ph("player.body-yaw", p.getBodyYaw())
      ));
   }
   
   public static String apply(String str, Player player) {
      load(player, true);
      
      for(var var : variables)
         str = var.apply(str);
      
      return str;
   }
   
   public static String applyMinimal(String str, Player player) {
      load(player, false);
      
      for(var var : variables)
         str = var.apply(str);
      
      return str;
   }
   
   private static Object safe(Supplier<Double> getter) {
      try {
         return getter.get();
      } catch (Exception e) {
         return "none";
      }
   }
   
   private static String getDirection(float yaw) {
      String[] directions = {"West", "North West", "North", "North East", "East", "South East", "South", "South West"};
      double dir = (yaw - 90) % 360;
      if (dir < 0)
         dir += 360;
      
      int index = (int) ((dir + 22.5) / 45) % 8;
      return directions[index];
   }
   
   private static String onlinePlayers(String delimiter) {
      StringBuilder out = new StringBuilder();
      for (Player player : Bukkit.getOnlinePlayers())
         out.append(delimiter).append(player.getName());
      
      return out.isEmpty() ? "" : out.substring(delimiter.length());
   }
   
   private static Variable ph(String name, Object value) {
      return Variable.ph(name, String.valueOf(value));
   }
}
