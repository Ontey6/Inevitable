package com.ontey.inevitable.annotationfinder;

import com.ontey.api.log.Log;
import com.ontey.inevitable.files.FileLog;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AnnotationFinder {
   
   private static final String CLASS_SUFFIX = ".class";
   
   public static Set<Class<?>> find(Class<?> jarLocationSource, String packageName, Class<? extends Annotation> annotation) {
      return find(jarLocationSource, clazz -> clazz.isAnnotationPresent(annotation) && clazz.getPackageName().startsWith(packageName));
   }
   
   public static Set<Class<?>> find(Class<?> jarLocationSource, Class<? extends Annotation> annotation) {
      return find(jarLocationSource, clazz -> clazz.isAnnotationPresent(annotation));
   }
   
   /**
    *
    * @param source Any class of your project so the jar file can be located. Best practice to use your {@code Main} class
    * @param requirements The requirements
    * @return All classes in the jar of the {@code source} that fit the requirement
    */
   
   public static Set<Class<?>> find(Class<?> source, Function<Class<?>, Boolean> requirements) {
      Set<Class<?>> out = new HashSet<>();
      
      try(JarFile jar = getJar(source)) {
         
         for(JarEntry entry : Collections.list(jar.entries())) {
            String entryName = entry.getName();
            
            if(!entryName.endsWith(CLASS_SUFFIX))
               continue;
            
            Class<?> cls = Class.forName(toClassName(entryName));
            
            if(requirements.apply(cls))
               out.add(cls);
         }
         
      } catch(MalformedURLException | URISyntaxException | ClassNotFoundException e) {
         Log.error("Internal Error (probably): " + e.getClass().getName() + " in the AnnotationFinder");
         FileLog.saveStackTrace(e);
      } catch(IOException e) {
         Log.error("Encountered an IOException in AnnotationFinder");
         FileLog.saveStackTrace(e);
      }
      
      return out;
   }
   
   private static String toClassName(String entryName) {
      return entryName
        .replace('/', '.')
        .substring(0, entryName.length() - CLASS_SUFFIX.length());
   }
   
   private static JarFile getJar(Class<?> source) throws URISyntaxException, IOException {
      URL jarUrl = source.getProtectionDomain().getCodeSource().getLocation();
      JarURLConnection conn = (JarURLConnection) new URI("jar:" + jarUrl + "!/").toURL().openConnection();
      
      return conn.getJarFile();
   }
}
