package mods.KBIgravelore.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import mods.KBIgravelore.common.GravelMain;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BucketGravel extends Item {

   int spawnID;
   int meta;
   @SideOnly(Side.CLIENT)
   private Icon[] itemTextures;


   public BucketGravel(int par1) {
      super(par1);
      this.setHasSubtypes(true);
      this.setMaxStackSize(1);
      this.setUnlocalizedName("KBIgravelBucket");
      this.setCreativeTab(CreativeTabs.tabMisc);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IconRegister par1IconRegister) {
      super.itemIcon = par1IconRegister.registerIcon("KBIgravelore:bucketGravel");
   }

   @SideOnly(Side.CLIENT)
   public Icon getIconFromDamage(int par1) {
      return super.itemIcon;
   }

   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
      if(par1ItemStack.getItemDamage() == 0) {
         this.spawnID = Block.gravel.blockID;
         this.meta = 0;
      } else {
         this.spawnID = GravelMain.gravelOreID;
         this.meta = par1ItemStack.getItemDamage() - 1;
      }

      if(this.meta > 15) {
         this.meta = 0;
      }

      int i1 = par3World.getBlockId(par4, par5, par6);
      if(i1 == Block.snow.blockID && (par3World.getBlockMetadata(par4, par5, par6) & 7) < 1) {
         par7 = 1;
      } else if(i1 != Block.vine.blockID && i1 != Block.tallGrass.blockID && i1 != Block.deadBush.blockID) {
         if(par7 == 0) {
            --par5;
         }

         if(par7 == 1) {
            ++par5;
         }

         if(par7 == 2) {
            --par6;
         }

         if(par7 == 3) {
            ++par6;
         }

         if(par7 == 4) {
            --par4;
         }

         if(par7 == 5) {
            ++par4;
         }
      }

      if(!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
         return false;
      } else if(par1ItemStack.stackSize == 0) {
         return false;
      } else {
         if(par3World.canPlaceEntityOnSide(this.spawnID, par4, par5, par6, false, par7, (Entity)null, par1ItemStack)) {
            Block block = Block.blocksList[this.spawnID];
            if(par3World.setBlock(par4, par5, par6, this.spawnID, this.meta, 3)) {
               if(par3World.getBlockId(par4, par5, par6) == this.spawnID) {
                  Block.blocksList[this.spawnID].onBlockPlacedBy(par3World, par4, par5, par6, par2EntityPlayer, par1ItemStack);
                  Block.blocksList[this.spawnID].onPostBlockPlaced(par3World, par4, par5, par6, this.meta);
               }

               par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
               if(!par2EntityPlayer.capabilities.isCreativeMode) {
                  par1ItemStack.itemID = Item.bucketEmpty.itemID;
                  par1ItemStack.setItemDamage(0);
               }
            }
         }

         return true;
      }
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(int par1, CreativeTabs tab, List subItems) {
      for(int ix = 0; ix < 9; ++ix) {
         subItems.add(new ItemStack(this, 1, ix));
      }

   }
}
