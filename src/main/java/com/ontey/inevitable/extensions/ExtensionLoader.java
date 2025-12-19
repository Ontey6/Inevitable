package com.ontey.inevitable.extensions;

import com.ontey.inevitable.api.log.Log;
import com.ontey.inevitable.Main;
import com.ontey.inevitable.files.FileLog;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;

public final class ExtensionLoader {
   
   private static final Set<InevitableExtension> loaded = new HashSet<>();
   
   public static void loadAll() {
      File dir = new File(Main.plugin.getDataFolder(), "extensions");
      
      if(!dir.exists())
         dir.mkdirs();
      
      File[] jars = dir.listFiles(f -> f.isFile() && f.getName().endsWith(".jar"));
      if(jars == null)
         return;
      
      for(File jar : jars) {
         try {
            URLClassLoader cl = new URLClassLoader(new URL[]{ jar.toURI().toURL() }, Main.plugin.getClass().getClassLoader());
            
            try(JarFile jf = new JarFile(jar)) {
               String main = jf.getManifest()
                 .getMainAttributes()
                 .getValue("Extension-Main");
               if(main == null)
                  continue;
               
               Class<?> clazz = Class.forName(main, true, cl);
               
               if(!InevitableExtension.class.isAssignableFrom(clazz))
                  throw new IllegalStateException(
                    "Extension main class " + main + " in " + jar.getName() + " does not implement InevitableExpansion"
                  );
               
               InevitableExtension extension = (InevitableExtension) clazz.getDeclaredConstructor().newInstance();
               loaded.add(extension);
            }
         
         } catch(Exception e) {
            Log.error("Couldn't load extension from file '" + jar.getName() + "': " + e.getClass().getSimpleName());
            FileLog.saveStackTrace(e);
         }
      }
   }
   
   public static void preloadAll() {
      for(InevitableExtension extension : loaded) {
         Log.info("Pre-Loaded extension " + extension.getClass().getSimpleName());
         extension.onPreload();
      }
   }
   
   public static void enableAll() {
      for(InevitableExtension extension : loaded) {
         Log.info("Enabling extension " + extension.getClass().getSimpleName());
         extension.onEnable();
      }
   }
   
   public static void disableAll() {
      for(InevitableExtension extension : loaded) {
         Log.info("Disabling extension " + extension.getClass().getSimpleName());
         extension.onDisable();
      }
   }
}