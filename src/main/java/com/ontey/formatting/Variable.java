package com.ontey.formatting;

import com.ontey.files.Config;

import java.util.regex.Pattern;

public class Variable {
   
   public String name, replacement, format;
   
   private Variable(String name, String replacement, String format) {
      this.name = name;
      this.replacement = replacement;
      this.format = format;
   }
   
   // for extendability (e.g. normal variables)
   
   public static Variable ph(String name, String replacement) {
      return new Variable(name, replacement, Config.PLACEHOLDER_FORMAT);
   }
   
   public String apply(String str) {
      String formatted = format.replace("%ph", name);
      return str
        .replaceAll("(?<!\\\\)" + Pattern.quote(formatted), replacement)
        .replace("\\" + formatted, formatted);
   }
}