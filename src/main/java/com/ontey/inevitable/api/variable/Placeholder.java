package com.ontey.inevitable.api.variable;

import com.ontey.inevitable.api.variable.argument.VariableArgument;
import com.ontey.inevitable.api.variable.argument.PlaceholderContext;
import com.ontey.inevitable.files.Config;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class Placeholder extends Variable {
   public final String replacement;
   
   public final PlaceholderContext ctx;
   
   public Placeholder(String name, String replacement, VariableFormat format) {
      this(name, replacement, format, Map.of());
   }
   
   public Placeholder(String name, String replacement, VariableFormat format, Map<String, VariableArgument<?>> ctx) {
      this(name, replacement, format, new PlaceholderContext(ctx));
   }
   
   public Placeholder(String name, String replacement, VariableFormat format, PlaceholderContext ctx) {
      super(name, format);
      
      Objects.requireNonNull(replacement);
      Objects.requireNonNull(ctx);
      
      this.replacement = replacement;
      this.ctx = ctx;
   }
   
   public String apply(String str) {
      String formatted = this.format();
      
      return str
        .replaceAll("(?<!\\\\)" + Pattern.quote(formatted), replacement)
        .replace("\\" + formatted, formatted);
   }
   
   public static String placeholder(String name) {
      return Config.PLACEHOLDER_FORMAT.format(name);
   }
}
