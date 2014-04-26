package mods.KBIgravelore.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import mods.KBIgravelore.api.GravelDropAPI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class OreGravel extends BlockSand {

   public static OreGravel instance;
   @SideOnly(Side.CLIENT)
   private Icon[] blockTextures;


   public OreGravel(int id) {
      super(id, Material.sand);
      this.setUnlocalizedName("KBIgravelOre");
      this.setCreativeTab(CreativeTabs.tabBlock);
      this.setHardness(0.6F);
      instance = this;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IconRegister par1IconRegister) {
      this.blockTextures = new Icon[16];

      for(int i = 0; i < 16; ++i) {
         this.blockTextures[i] = par1IconRegister.registerIcon("KBIgravelore:gravel_" + i);
      }

      super.blockIcon = this.blockTextures[0];
   }

   public Icon getBlockTextureFromSideAndMetadata(int blockSide, int blockMeta) {
      return this.blockTextures[blockMeta];
   }

   public int quantityDropped(Random par1Random) {
      return 1;
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Block.gravel.blockID;
   }

   public void dropXP(World world, int x, int y, int z, int meta) {
      super.dropXpOnBlockBreak(world, x, y, z, meta);
   }

   public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
      new Random();

      try {
         ItemStack e = GravelDropAPI.getRandomDrop(meta);
         if(e != null) {
            this.spawnItemstack(new EntityItem(world, (double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, e.copy()), world);
         }
      } catch (Exception var8) {
         System.out.println("Error in dropping items!");
      }

      super.dropXpOnBlockBreak(world, x, y, z, (meta + 1) * 2);
      super.onBlockDestroyedByPlayer(world, x, y, z, meta);
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

   @SideOnly(Side.CLIENT)
   public void getSubBlocks(int par1, CreativeTabs tab, List subItems) {
      for(int ix = 0; ix < 8; ++ix) {
         subItems.add(new ItemStack(this, 1, ix));
      }

   }
}
