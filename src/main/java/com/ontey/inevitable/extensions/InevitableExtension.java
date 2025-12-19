package com.ontey.inevitable.extensions;

import com.ontey.inevitable.Main;

public interface InevitableExtension {
   
   /**
    * Executed before the plugin is loaded (To make modifications...)
    * <p>
    * Already initialized fields (In plugin Main):
    * {@link Main#plugin plugin},
    * {@link Main#pm plugin manager},
    * {@link Main#sm service manager},
    * {@link Main#version version},
    * {@link Main#debugSnapshot debug snapshot},
    * {@link Main#debugVersion debug version},
    */
   
   default void onPreload() { }
   
   /**
    * Executed when the plugin is fully loaded
    */
   
   default void onEnable() { }
   
   /**
    * Executed when disabling the plugin
    */
   
   default void onDisable() { }
}
