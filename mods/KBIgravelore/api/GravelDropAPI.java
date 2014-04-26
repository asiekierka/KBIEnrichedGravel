package mods.KBIgravelore.api;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GravelDropAPI {

   private static ArrayList drops = new ArrayList(16);


   public GravelDropAPI() {
      drops.ensureCapacity(16);

      for(int q = 0; q < 16; ++q) {
         drops.add(q, new ArrayList(0));
      }

   }

   public void spawnItemstack(EntityItem entityitem, World world) {
      if(!world.isRemote) {
         entityitem.delayBeforeCanPickup = 10;
         entityitem.motionX = 0.0D;
         entityitem.motionY = 0.0D;
         entityitem.motionZ = 0.0D;
         world.spawnEntityInWorld(entityitem);
      }

   }

   public static ItemStack getRandomDrop(World world, int x, int y, int z) {
      try {
         int e = world.getBlockMetadata(x, y, z);
         return getRandomDrop(e);
      } catch (Exception var5) {
         System.out.println("Error in getting drop!");
         return new ItemStack(Item.flint, 1, 0);
      }
   }

   public static ItemStack getRandomDrop(int meta) {
      try {
         Random e = new Random();
         ArrayList drop = (ArrayList)drops.get(meta);
         ItemStack itemstack = (ItemStack)drop.get(e.nextInt(drop.size()));
         return itemstack.copy();
      } catch (Exception var4) {
         System.out.println("Error in getting drop!");
         return new ItemStack(Item.flint, 1, 0);
      }
   }

   public static void addItemToList(int meta, ItemStack item) {
      ((ArrayList)drops.get(meta)).add(item);
   }

   public static void removeItemFromList(int meta, ItemStack item) {
      ((ArrayList)drops.get(meta)).remove(item);
   }

}
