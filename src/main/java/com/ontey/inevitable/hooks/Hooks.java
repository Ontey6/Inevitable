package com.ontey.inevitable.hooks;

import com.ontey.inevitable.Main;
import com.ontey.inevitable.api.log.Log;

public class Hooks {
   
   public static boolean
     VAULT,
     PLACEHOLDER_API;
   
   public static void load() {
      VAULT = isEnabled("Vault");
      PLACEHOLDER_API = isEnabled("PlaceholderAPI");
   }
   
   private static boolean isEnabled(String name) {
      if(!Main.pm.isPluginEnabled(name))
         return false;
      
      Log.debug("Hooked into plugin " + name);
      return true;
   }
}