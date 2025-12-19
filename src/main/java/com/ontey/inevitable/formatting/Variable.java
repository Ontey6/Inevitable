package com.ontey.inevitable.formatting;

import com.ontey.inevitable.api.variable.VariableFormat;
import com.ontey.inevitable.files.Config;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Variable {
   
   public final String name;
   
   public final VariableFormat format;
   
   public static String phFormat(String name) {
      return Config.PLACEHOLDER_FORMAT.format(name);
   }
}