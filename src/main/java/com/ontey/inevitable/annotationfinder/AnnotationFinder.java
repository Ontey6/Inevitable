package com.ontey.inevitable.annotationfinder;

import com.ontey.api.log.Log;
import com.ontey.inevitable.files.FileLog;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AnnotationFinder {
   
   private static final String CLASS_SUFFIX = ".class";
   
   public static Set<Class<?>> find(Class<?> jarLocationSource, String packageName, Class<? extends Annotation> annotation) {
      Set<Class<?>> out = new HashSet<>();
      
      try {
         URL jarUrl = jarLocationSource.getProtectionDomain().getCodeSource().getLocation();
         JarURLConnection conn = (JarURLConnection) new URI("jar:" + jarUrl + "!/").toURL().openConnection();
         
         String base = packageName.replace('.', '/');
         
         try(JarFile jar = conn.getJarFile()) {
            for(JarEntry entry : Collections.list(jar.entries())) {
               String entryName = entry.getName();
               
               if(!entryName.startsWith(base) || !entryName.endsWith(CLASS_SUFFIX))
                  continue;
               
               String className = entryName
                 .replace('/', '.')
                 .substring(0, entryName.length() - CLASS_SUFFIX.length());
               
               Class<?> cls = Class.forName(className);
               
               if(cls.isAnnotationPresent(annotation))
                  out.add(cls);
            }
         }
      } catch(MalformedURLException | URISyntaxException | ClassNotFoundException e) {
         Log.error("Internal Error: " + e.getClass().getName() + " in the AnnotationFinder");
         FileLog.saveStackTrace(e);
      } catch(IOException e) {
         Log.error("Encountered an IOException in AnnotationFinder");
         FileLog.saveStackTrace(e);
      }
      
      return out;
   }
}
