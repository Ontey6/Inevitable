package com.ontey.inevitable.hooks.vault;

import com.ontey.inevitable.api.log.Log;
import com.ontey.inevitable.Main;
import com.ontey.inevitable.hooks.Hooks;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.jetbrains.annotations.UnknownNullability;

public final class VaultHook {
   
   @UnknownNullability
   public static Economy economy;
   
   @UnknownNullability
   public static Permission permissions;
   
   @UnknownNullability
   public static Chat chat;
   
   private VaultHook() { }
   
   public static void load() {
      if(!Hooks.VAULT)
         return;
      
      Log.debug("Hooked into Vault");
      
      {
         var provider = Main.sm.getRegistration(Economy.class);
         if(provider != null)
            economy = provider.getProvider();
      }
      
      {
         var provider = Main.sm.getRegistration(Permission.class);
         if(provider != null)
            permissions = provider.getProvider();
      }
      
      {
         var provider = Main.sm.getRegistration(Chat.class);
         if(provider != null)
            chat = provider.getProvider();
      }
   }
   
   public static boolean hasEconomy() {
      return economy != null;
   }
   
   public static boolean hasPermissions() {
      return permissions != null;
   }
   
   public static boolean hasChat() {
      return chat != null;
   }
}
