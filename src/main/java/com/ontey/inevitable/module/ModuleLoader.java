package com.ontey.inevitable.module;

import com.ontey.inevitable.api.classfilterer.ClassFilterer;
import com.ontey.inevitable.api.color.JavaColor;
import com.ontey.inevitable.api.log.Log;
import com.ontey.inevitable.Main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class ModuleLoader {
   
   public static void loadModules() {
      Log.info(JavaColor.colorize("&7------------------&r Starting Module Loader &7------------------"));
      Log.info("");
      
      ClassFilterer
        .find(Main.class, "com.ontey.inevitable.module.modules", Module.class)
        .forEach(ModuleLoader::loadModule);
      
      Log.info("");
      Log.info(JavaColor.colorize("&7------------------&r Stopping Module Loader &7------------------"));
   }
   
   
   private static void loadModule(Class<?> clazz) {
      try {
         Method load = clazz.getDeclaredMethod("load");
         
         if(!Modifier.isStatic(load.getModifiers())) {
            ModuleExceptions.handleNonStaticLoad(clazz);
            return;
         }
         
         if(!Modifier.isPublic(load.getModifiers())) {
            ModuleExceptions.handleNonPublicLoad(clazz);
            return;
         }
         
         load.invoke(null);
         
         String name = clazz.getName();
         
         Log.info(JavaColor.colorize("• Module was loaded: " + name.substring(name.lastIndexOf('.') + 1)));
         
      } catch(NoSuchMethodException e) {
         ModuleExceptions.handleNoSuchMethod(clazz, e);
         
      } catch(IllegalAccessException e) {
         ModuleExceptions.handleIllegalAccess(clazz, e);
         
      } catch(IllegalArgumentException e) {
         ModuleExceptions.handleIllegalArgument(clazz, e);
         
      } catch(InvocationTargetException e) {
         ModuleExceptions.handleInvocation(clazz, e);
         
      } catch(SecurityException e) {
         ModuleExceptions.handleSecurity(clazz, e);
      }
   }
   
   private static class ModuleExceptions {
      
      @SuppressWarnings("CallToPrintStackTrace")
      private static void handleOutput(Throwable e) {
         e.printStackTrace();
      }
      
      public static void handleNonStaticLoad(Class<?> clazz) {
         Log.error(
           "Module '" + clazz.getName() + "'s load() method is not static.",
           "Load methods must be declared static.",
           "Open an issue on GitHub "
         );
      }
      
      public static void handleNonPublicLoad(Class<?> clazz) {
         Log.error(
           "Module '" + clazz.getName() + "'s load() method is not public.",
           "Load methods must be publicly accessible.",
           "Fix the module's method visibility."
         );
      }
      
      public static void handleNoSuchMethod(Class<?> clazz, NoSuchMethodException e) {
         Log.error(
           "Module '" + clazz.getName() + "' does not declare a load() method.",
           "Modules must implement a public static load() method.",
           "Module will not be loaded."
         );
         handleOutput(e);
      }
      
      public static void handleIllegalAccess(Class<?> clazz, IllegalAccessException e) {
         Log.error(
           "Module '" + clazz.getName() + "' has an inaccessible load() method.",
           "The method exists but cannot be accessed reflectively.",
           "Ensure load() is public and static."
         );
         handleOutput(e);
      }
      
      public static void handleIllegalArgument(Class<?> clazz, IllegalArgumentException e) {
         Log.error(
           "Module '" + clazz.getName() + "' has an invalid load() method signature.",
           "Static invocation was attempted but the method requires parameters or an instance.",
           "Ensure load() is public static with no parameters."
         );
         handleOutput(e);
      }
      
      public static void handleInvocation(Class<?> clazz, InvocationTargetException e) {
         Throwable cause = e.getCause();
         Log.error(
           "Module '" + clazz.getName() + "' threw an exception inside its load method.",
           "Cause: " + (cause != null ? cause.getClass().getName() + ": " + cause.getMessage() : "Unknown"),
           "Open an Issue "
         );
         handleOutput(e);
      }
      
      public static void handleSecurity(Class<?> clazz, SecurityException e) {
         Log.error(
           "Reflection access to module '" + clazz.getName() + "' was blocked.",
           "A security manager prevented reflective access.",
           "Loading aborted."
         );
         handleOutput(e);
      }
   }
}
