package mods.KBIgravelore.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.KBIgravelore.api.GravelDropAPI;
import mods.KBIgravelore.common.GravelMain;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSifter extends Item {

   public ItemSifter(int id) {
      super(id);
      this.setCreativeTab(CreativeTabs.tabTools);
      this.setMaxDamage(64);
      this.setMaxStackSize(1);
   }

   public ItemSifter(int id, int damage) {
      super(id);
      this.setCreativeTab(CreativeTabs.tabTools);
      this.setMaxDamage(damage);
      this.setMaxStackSize(1);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IconRegister par1IconRegister) {
      super.itemIcon = par1IconRegister.registerIcon("KBIgravelore:sifter_" + this.getMaxDamage());
   }

   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
      return false;
   }

   public boolean onBlockStartBreak(ItemStack par1ItemStack, int par4, int par5, int par6, EntityPlayer par2EntityPlayer) {
      World par3World = par2EntityPlayer.worldObj;
      if(!par3World.isRemote) {
         int i1 = par3World.getBlockId(par4, par5, par6);
         if(i1 == Block.gravel.blockID) {
            this.spawnItemstack(new EntityItem(par3World, (double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, new ItemStack(Item.flint, 1, 0)), par3World);
            par3World.setBlockToAir(par4, par5, par6);
            par1ItemStack.damageItem(1, par2EntityPlayer);
            return true;
         }

         if(GravelMain.moduleEnabled && GravelMain.enableEnriched && i1 == GravelMain.gravelOreID) {
            this.spawnItemstack(new EntityItem(par3World, (double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, GravelDropAPI.getRandomDrop(par3World, par4, par5, par6)), par3World);
            this.spawnItemstack(new EntityItem(par3World, (double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, GravelDropAPI.getRandomDrop(par3World, par4, par5, par6)), par3World);
            par3World.setBlockToAir(par4, par5, par6);
            par1ItemStack.damageItem(1, par2EntityPlayer);
            int meta = (par3World.getBlockMetadata(par4, par5, par6) + 1) * 5;

            while(meta > 0) {
               int i2 = EntityXPOrb.getXPSplit(meta);
               meta -= i2;
               par3World.spawnEntityInWorld(new EntityXPOrb(par3World, (double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, i2));
            }

            return true;
         }
      }

      return false;
   }

   public void spawnItemstack(EntityItem entityitem, World world) {
      entityitem.delayBeforeCanPickup = 10;
      entityitem.motionX = 0.0D;
      entityitem.motionY = 0.0D;
      entityitem.motionZ = 0.0D;
      world.spawnEntityInWorld(entityitem);
   }
}
