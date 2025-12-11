package com.ontey.inevitable.files;

import com.ontey.api.log.Log;
import com.ontey.inevitable.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

public class FileLog {
   
   public static File directory;
   
   private FileLog() { }
   
   public static void load() {
      Log.debug("Loading FileLog");
      
      directory = new File(Main.plugin.getDataFolder(), "logs");
      
      if(directory.exists() && !directory.isDirectory())
         Main.disablePlugin("FileLog directory already exists, but as a file: " + directory.getPath());
      
      if(!directory.exists())
         if(!directory.mkdirs())
            Main.disablePlugin("Could not generate the logs directory, disabling plugin"); //TODO better solution
      
      Log.debug("Loaded FileLog");
   }
   
   public static void saveStackTrace(Throwable throwable) {
      for(int i = 0; i < 5; i++) {
         String name = throwable.getClass().getName() + "-" + UUID.randomUUID() + ".log";
         File file = new File(directory, name);
         
         if(!file.exists()) {
            writeStackTrace(throwable, file, name);
            Log.warning("Saved stack-trace to " + name);
            return;
         }
      }
      
      Log.warning("Could not find a name for the FileLog " + throwable.getClass().getName());
   }
   
   private static void writeStackTrace(Throwable throwable, File file, String name) {
      try(PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
         throwable.printStackTrace(pw);
      } catch(IOException ex) {
         Log.error("Could not write to error log '" + name + "'");
      }
   }
}
