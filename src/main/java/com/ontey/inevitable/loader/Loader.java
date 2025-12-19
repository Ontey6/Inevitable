package com.ontey.inevitable.loader;

import com.ontey.inevitable.api.classfilterer.ClassFilterer;
import com.ontey.inevitable.api.log.Log;
import com.ontey.inevitable.Main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * Loads all annotated loaders
 */

public class Loader {
   
   public static void load() {
      Set<Method> loadMethods = new HashSet<>();
      
      for(var method : getAnnotatedMethods())
         if(isLoadMethod(method))
            loadMethods.add(method);
      
      loadMethods.stream()
        .sorted((a, b) -> Integer.compare(
          b.getAnnotation(LoadMethod.class).value(),
          a.getAnnotation(LoadMethod.class).value()
        ))
        .forEach(loadMethod -> {
           try {
              loadMethod.invoke(null);
           } catch(IllegalAccessException | InvocationTargetException e) {
              Log.error("Something went wrong in the Loader: " + e.getClass().getSimpleName());
           }
        });
   }
   
   private static Set<Method> getAnnotatedMethods() {
      Set<Method> out = new HashSet<>();
      
      for(var clazz : ClassFilterer.find(Main.class))
         for(var method : clazz.getDeclaredMethods())
            if(method.isAnnotationPresent(LoadMethod.class))
               out.add(method);
      
      return out;
   }
   
   private static boolean isLoadMethod(Method method) {
      if(!Modifier.isPublic(method.getModifiers())) {
         Log.error("load method '" + method.getName() + "' in class '" + method.getDeclaringClass() + "' isn't public!");
         return false;
      }
      
      if(!Modifier.isStatic(method.getModifiers())) {
         Log.error("load method '" + method.getName() + "' in class '" + method.getDeclaringClass() + "' isn't static!");
         return false;
      }
      
      if(method.getParameterCount() != 0) {
         Log.error("load method '" + method.getName() + "' in class '" + method.getDeclaringClass() + "' needs parameters!");
         return false;
      }
      
      return true;
   }
}