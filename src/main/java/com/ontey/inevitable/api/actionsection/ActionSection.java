package com.ontey.inevitable.api.actionsection;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.List;

import static com.ontey.inevitable.api.files.Files.*;
import static com.ontey.inevitable.formatting.Format.format;
import static com.ontey.inevitable.formatting.Format.plainFormat;

@AllArgsConstructor
public class ActionSection {
   
   @Nullable
   private String broadcast, message;
   
   @Nullable
   private String actionbar;
   
   @NotNull
   private List<String> commands;
   
   @NotNull
   private FullTitle title;
   
   private static final String
     BROADCAST_PATH = "broadcast",
     MESSAGE_PATH = "message",
     ACTIONBAR_PATH = "actionbar",
     COMMANDS_PATH = "commands",
     TITLE_PATH = "title.title",
     SUBTITLE_PATH = "title.subtitle",
     FADE_IN_PATH = "title.times.fade-in",
     DURATION_PATH = "title.times.duration",
     FADE_OUT_PATH = "title.times.fade-out";
   
   @NotNull
   public static ActionSection of(ConfigurationSection section) {
      return new ActionSection(
        getMessage(section, BROADCAST_PATH),
        getMessage(section, MESSAGE_PATH),
        getMessage(section, ACTIONBAR_PATH),
        getListable(section, COMMANDS_PATH),
        new FullTitle(
          getMessage(section, TITLE_PATH),
          getMessage(section, SUBTITLE_PATH),
          getTimesPart(section, FADE_IN_PATH),
          getTimesPart(section, DURATION_PATH),
          getTimesPart(section, FADE_OUT_PATH)
        )
      );
   }
   
   @NotNull
   private static TimesPart getTimesPart(ConfigurationSection section, String path) {
      return new TimesPart(
        section.getLong(path + ".value", section.getLong(path)),
        TimesUnit.valueOf(section.getString(path + ".type", "SECONDS").toUpperCase())
      );
   }
   
   public void execute(Player player) {
      if(message != null)
         player.sendMessage(format(message, player));
      
      if(broadcast != null)
         for(Player recipient : Bukkit.getOnlinePlayers())
            recipient.sendMessage(format(broadcast, player));
      
      sendTitle(player);
      
      if(actionbar != null)
         player.sendActionBar(format(actionbar, player));
      
      for(String cmd : commands)
         player.performCommand(plainFormat(cmd, player));
   }
   
   private void sendTitle(Player p) {
      p.sendTitlePart(TitlePart.TIMES, title.times());
      
      p.sendTitlePart(TitlePart.TITLE, title.title(p));
      p.sendTitlePart(TitlePart.SUBTITLE, title.subtitle(p));
   }
   
   public record FullTitle(String title, String subtitle, TimesPart fadeIn, TimesPart duration, TimesPart fadeOut) {
      
      public Component title(Player p) {
         return format(title, p);
      }
      
      public Component subtitle(Player p) {
         return format(subtitle, p);
      }
      
      public Title.Times times() {
         return Title.Times.times(
           fadeIn.toDuration(),
           duration.toDuration(),
           fadeOut.toDuration()
         );
      }
   }
   
   public record TimesPart(long duration, TimesUnit unit) {
      
      public Duration toDuration() {
         return switch(unit) {
            case SECONDS -> Duration.ofSeconds(duration);
            case TICKS -> Duration.ofMillis(duration * 50L); // ticks >> ms
         };
      }
   }
   
   public enum TimesUnit { SECONDS, TICKS }
}