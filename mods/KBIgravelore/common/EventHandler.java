package mods.KBIgravelore.common;

import mods.KBIgravelore.common.GravelMain;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class EventHandler {

   @ForgeSubscribe
   public void gravelBucket(FillBucketEvent event) {
      if(event.current.itemID == Item.bucketEmpty.itemID && GravelMain.enableBuckets) {
         int bid = event.world.getBlockId(event.target.blockX, event.target.blockY, event.target.blockZ);
         if(bid == Block.gravel.blockID) {
            event.result = new ItemStack(GravelMain.bucket, 1, 0);
            event.setResult(Result.ALLOW);
            event.world.setBlockToAir(event.target.blockX, event.target.blockY, event.target.blockZ);
         } else if(GravelMain.moduleEnabled && GravelMain.enableEnriched && bid == GravelMain.gravelOreID) {
            event.result = new ItemStack(GravelMain.bucket, 1, event.world.getBlockMetadata(event.target.blockX, event.target.blockY, event.target.blockZ) + 1);
            event.setResult(Result.ALLOW);
            event.world.setBlockToAir(event.target.blockX, event.target.blockY, event.target.blockZ);
         }
      }

   }
}
