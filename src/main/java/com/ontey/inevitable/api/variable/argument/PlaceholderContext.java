package com.ontey.inevitable.api.variable.argument;

import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.NoSuchElementException;

@AllArgsConstructor
public class PlaceholderContext {
   
   public Map<String, VariableArgument<?>> arguments;
   
   public <T> T getArg(String name, Class<T> clazz) {
      Object arg = arguments.get(name);
      
      if(arg == null)
         throw new NoSuchElementException("No such argument found");
      
      if(!arg.getClass().isAssignableFrom(clazz))
         throw new IllegalStateException("Argument's class doesn't match class specified");
      
      //noinspection unchecked
      return (T) arg;
   }
}
