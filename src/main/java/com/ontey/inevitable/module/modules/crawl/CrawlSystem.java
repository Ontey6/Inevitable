package com.ontey.inevitable.module.modules.crawl;

import com.ontey.inevitable.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.WeakHashMap;

public final class CrawlSystem implements Listener {
   
   private static final WeakHashMap<Player, BukkitTask> crawling = new WeakHashMap<>();
   
   public static void startCrawling(Player player) {
      if(crawling.containsKey(player))
         return;
      
      BukkitTask task = Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
         if(!player.isOnline()) {
            stopCrawling(player);
            return;
         }
         
         ServerPlayer nms = ((CraftPlayer) player).getHandle();
         
         nms.setPose(Pose.SWIMMING);
         
         // Send fake block above player
         Location loc = player.getLocation();
         Block headBlock = loc.add(0, 1.0, 0).getBlock();
         
         nms.connection.send(new ClientboundBlockUpdatePacket(
           net.minecraft.core.BlockPos.containing(headBlock.getX(), headBlock.getY(), headBlock.getZ()),
           Blocks.BARRIER.defaultBlockState() // the fake solid block
         ));
         
         //TODO
         
      }, 0, 1);
      
      crawling.put(player, task);
   }
   
   public static void stopCrawling(Player bp) {
      BukkitTask t = crawling.remove(bp);
      if(t != null)
         t.cancel();
      
      if(!bp.isOnline())
         return;
      
      ServerPlayer nms = ((CraftPlayer) bp).getHandle();
      
      nms.setPose(Pose.STANDING);
      
      // Restore accurate block above head
      Location loc = bp.getLocation();
      Block headBlock = loc.add(0, 1.0, 0).getBlock();
      
      nms.connection.send(new ClientboundBlockUpdatePacket(
        BlockPos.containing(headBlock.getX(), headBlock.getY(), headBlock.getZ()),
        nms.level().getBlockState(BlockPos.containing(headBlock.getX(), headBlock.getY(), headBlock.getZ()))
      ));
   }
   
   public static boolean isCrawling(Player player) {
      return crawling.containsKey(player);
   }
   
   public void onCrawlMove(PlayerMoveEvent event) {
      ServerPlayer nms = ((CraftPlayer) event.getPlayer()).getHandle();
   }
}
