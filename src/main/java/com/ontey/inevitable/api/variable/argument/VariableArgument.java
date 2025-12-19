package com.ontey.inevitable.api.variable.argument;

import com.ontey.inevitable.api.variable.argument.type.VariableArgumentType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationTargetException;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VariableArgument<T> {
   
   public final T value;
   
   public static <T> VariableArgument<? extends T> of(String value, Class<T> clazz) throws VariableArgumentTypeException {
      
      if(VariableArgumentType.class.isAssignableFrom(clazz)) {
         try {
            //noinspection unchecked
            return (VariableArgument<T>)
              ((VariableArgumentType<?>) clazz.getDeclaredConstructor().newInstance())
              .parse(value);
         } catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException();
         } catch(ClassCastException e) {
            throw new VariableArgumentTypeException();
         }
      }
      
      if(String.class.isAssignableFrom(clazz))
         //noinspection unchecked
         return (VariableArgument<? extends T>) new VariableArgument<>(value);
      
      throw new VariableArgumentTypeException();
   }
   
   
}
