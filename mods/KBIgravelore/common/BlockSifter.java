package mods.KBIgravelore.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import mods.KBIgravelore.api.CustomSifterDropAPI;
import mods.KBIgravelore.api.GravelDropAPI;
import mods.KBIgravelore.common.GravelMain;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockSifter extends Block {

   @SideOnly(Side.CLIENT)
   private Icon[] blockTextures;


   public BlockSifter(int id) {
      super(id, Material.iron);
      this.setUnlocalizedName("KBIsifterBlock");
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setHardness(1.0F);
      this.setBlockBounds(0.0F, 0.8F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IconRegister par1IconRegister) {
      this.blockTextures = new Icon[6];

      for(int i = 0; i < 6; ++i) {
         this.blockTextures[i] = par1IconRegister.registerIcon("KBIgravelore:sifter_" + i);
      }

      super.blockIcon = this.blockTextures[0];
   }

   public Icon getBlockTextureFromSideAndMetadata(int blockSide, int blockMeta) {
      return this.blockTextures[blockSide];
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return GravelMain.sifter2ID + 256;
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      if(!par1World.isRemote) {
         int id = par1World.getBlockId(par2, par3 + 1, par4);
         if(id == Block.gravel.blockID) {
            this.spawnItemstack(new EntityItem(par1World, (double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, new ItemStack(Item.flint, 1, 0)), par1World);
            Random constantdrop = new Random();
            int randomdrop = constantdrop.nextInt(200);
            int dropped = constantdrop.nextInt(4) + 1;
            int highrand = constantdrop.nextInt(3) + 4;
            if(randomdrop < 10) {
               this.spawnItemstack(new EntityItem(par1World, (double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, GravelDropAPI.getRandomDrop(0)), par1World);
            } else if(randomdrop >= 10 && randomdrop < 15) {
               this.spawnItemstack(new EntityItem(par1World, (double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, GravelDropAPI.getRandomDrop(dropped)), par1World);
            } else if(randomdrop >= 15 && randomdrop < 18) {
               this.spawnItemstack(new EntityItem(par1World, (double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, GravelDropAPI.getRandomDrop(highrand)), par1World);
            }

            par1World.setBlockToAir(par2, par3 + 1, par4);
            par1World.playSoundEffect((double)par2, (double)par3, (double)par4, "step.gravel", 1.0F, 1.0F);
         } else if(GravelMain.moduleEnabled && GravelMain.enableEnriched && id == GravelMain.gravelOreID) {
            this.spawnItemstack(new EntityItem(par1World, (double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, GravelDropAPI.getRandomDrop(par1World, par2, par3 + 1, par4)), par1World);
            this.spawnItemstack(new EntityItem(par1World, (double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, GravelDropAPI.getRandomDrop(par1World, par2, par3 + 1, par4)), par1World);
            par1World.setBlockToAir(par2, par3 + 1, par4);
            par1World.playSoundEffect((double)par2, (double)par3, (double)par4, "step.gravel", 1.0F, 1.0F);
         } else {
            ItemStack constantdrop1 = CustomSifterDropAPI.getConstantDrop(par1World, par2, par3 + 1, par4);
            ItemStack randomdrop1 = CustomSifterDropAPI.getRandomDrop(par1World, par2, par3 + 1, par4);
            boolean dropped1 = false;
            if(constantdrop1 != null && constantdrop1.itemID != 0) {
               this.spawnItemstack(new EntityItem(par1World, (double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, constantdrop1), par1World);
               dropped1 = true;
            }

            if(randomdrop1 != null) {
               if(randomdrop1.itemID != 0) {
                  this.spawnItemstack(new EntityItem(par1World, (double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, randomdrop1), par1World);
               }

               dropped1 = true;
            }

            if(dropped1) {
               par1World.setBlockToAir(par2, par3 + 1, par4);
               par1World.playSoundEffect((double)par2, (double)par3, (double)par4, "step.gravel", 1.0F, 1.0F);
            }
         }
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
}
