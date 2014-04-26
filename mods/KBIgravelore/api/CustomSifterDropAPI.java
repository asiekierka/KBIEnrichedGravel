package mods.KBIgravelore.api;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CustomSifterDropAPI {

   private static ArrayList blocks = new ArrayList(4096);
   private static ArrayList constant = new ArrayList(4096);


   public CustomSifterDropAPI() {
      for(int w = 0; w < 4096; ++w) {
         ArrayList drops = new ArrayList(4096);
         blocks.add(w, drops);

         for(int constants = 0; constants < 16; ++constants) {
            drops.add(constants, new ArrayList(0));
         }

         ArrayList var5 = new ArrayList(16);

         for(int q = 0; q < 16; ++q) {
            var5.add(q, new ItemStack(0, 1, 0));
         }

         constant.add(w, var5);
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
         int e = world.getBlockId(x, y, z);
         int meta = world.getBlockMetadata(x, y, z);
         return getRandomDrop(e, meta);
      } catch (Exception var6) {
         System.out.println("Error in getting drop!");
         return null;
      }
   }

   public static ItemStack getRandomDrop(int id, int meta) {
      try {
         Random e = new Random();
         ArrayList drops = (ArrayList)blocks.get(id);
         ArrayList drop = (ArrayList)drops.get(meta);
         ItemStack itemstack = (ItemStack)drop.get(e.nextInt(drop.size()));
         return itemstack.copy();
      } catch (Exception var6) {
         System.out.println("Error in getting drop!");
         return null;
      }
   }

   public static ItemStack getConstantDrop(World world, int x, int y, int z) {
      try {
         int e = world.getBlockId(x, y, z);
         int meta = world.getBlockMetadata(x, y, z);
         return getConstantDrop(e, meta);
      } catch (Exception var6) {
         System.out.println("Error in getting drop!");
         return null;
      }
   }

   public static ItemStack getConstantDrop(int id, int meta) {
      try {
         ArrayList e = (ArrayList)constant.get(id);
         ItemStack itemstack = (ItemStack)e.get(meta);
         return itemstack.copy();
      } catch (Exception var4) {
         System.out.println("Error in getting drop!");
         return null;
      }
   }

   public static void addItemToList(int id, int meta, ItemStack item) {
      ((ArrayList)((ArrayList)blocks.get(id)).get(meta)).add(item);
   }

   public static void removeItemFromList(int id, int meta, ItemStack item) {
      ((ArrayList)((ArrayList)blocks.get(id)).get(meta)).remove(item);
   }

   public static void setConstant(int id, int meta, ItemStack item) {
      ((ArrayList)constant.get(id)).set(meta, item);
   }

}
