package com.ontey.api.log;

import com.ontey.api.color.JavaColor;
import com.ontey.inevitable.files.Config;

import java.util.logging.*;

import static com.ontey.inevitable.files.Config.PREFIX;

public class Log {
   
   private static final Logger log = Logger.getLogger("Minecraft");
   
   public static void info(String message) {
      log.info(PREFIX + " " + message + JavaColor.RESET);
   }
   
   public static void info(String... messages) {
      for(String msg : messages)
         info(msg);
   }
   
   public static void warning(String message) {
      log.warning(PREFIX + " " + message + JavaColor.RESET);
   }
   
   public static void warning(String... messages) {
      for(String msg : messages)
         warning(msg);
   }
   
   public static void error(String message) {
      log.severe(PREFIX + " " + message + JavaColor.RESET);
   }
   
   public static void error(String... messages) {
      for(String msg : messages)
         error(msg);
   }
   
   public static void debug(String message) {
      if(Config.DEBUG)
         log.info(JavaColor.YELLOW + PREFIX + " " + message + JavaColor.RESET);
   }
}