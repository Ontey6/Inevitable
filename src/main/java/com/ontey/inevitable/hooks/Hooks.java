package com.ontey.inevitable.hooks;

import com.ontey.inevitable.Main;

public class Hooks {
   
   public static boolean
     PLACEHOLDER_API;
   
   public static void load() {
      PLACEHOLDER_API = Main.pm.isPluginEnabled("PlaceholderAPI");
   }
}
