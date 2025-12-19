package com.ontey.inevitable.formatting;

import com.ontey.inevitable.api.variable.VariableFormat;
import com.ontey.inevitable.files.Config;

import java.util.regex.Pattern;

public class Placeholder extends Variable {
   
   public String replacement;
   
   private Placeholder(String name, String replacement, VariableFormat format) {
      super(name, format);
      this.replacement = replacement;
   }
   
   // for extendability (e.g. normal variables)
   
   public static Placeholder ph(String name, String replacement) {
      return new Placeholder(name, replacement, Config.PLACEHOLDER_FORMAT);
   }
   
   public String apply(String str) {
      String formatted = format.format(name);
      return str
        .replaceAll("(?<!\\\\)" + Pattern.quote(formatted), replacement)
        .replace("\\" + formatted, formatted);
   }
}