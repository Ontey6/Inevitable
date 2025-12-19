package com.ontey.inevitable.api.regstr;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record RegStr(String str) {
   public String replaceAll(String target, String replacementFormat, Function<String, String> replacement) {
      Matcher m = Pattern.compile(target).matcher(str);
      StringBuilder sb = new StringBuilder();
      
      while(m.find()) {
         int count = m.groupCount();
         String[] groups = new String[count + 1];
         for(int i = 0; i <= count; i++)
            groups[i] = m.group(i);
         String replaced = replacementFormat;
         for(int i = 0; i < groups.length; i++)
            if(groups[i] != null)
               replaced = replaced.replace("$" + i, groups[i]);
         
         m.appendReplacement(sb, Matcher.quoteReplacement(replacement.apply(replaced)));
      }
      m.appendTail(sb);
      return sb.toString();
   }
}