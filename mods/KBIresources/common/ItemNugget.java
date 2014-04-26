package mods.KBIresources.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import mods.KBIgravelore.common.EntityNetherrock;
import mods.KBIgravelore.common.EntityObsidian;
import mods.KBIgravelore.common.EntityRedRock;
import mods.KBIgravelore.common.EntityRock;
import mods.KBIgravelore.common.GravelMain;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemNugget extends Item {

   private static final String[] subNames = new String[]{"IronNugget", "RedstoneChunk", "LapisChunk", "ClayChunk", "StoneChunk", "DirtChunk", "SandPile", "NetherrackChunk", "SoulSandPile", "DiamondChip", "EmeraldChip", "CopperNugget", "TinNugget", "SilverNugget", "LeadNugget", "AluminumNugget", "NickelNugget", "PlatinumNugget", "RubyChip", "SapphireChip", "GreenSapphireChip", "ApatiteChip", "TitaniumNugget", "SulfurPile", "SaltpeterPile", "ZincNugget", "CoalChunk", "ObsidianChunk", "CobaltNugget", "ArditeNugget", "PeatChunk", "RedRockChunk", "NetherQuartzChunk", "Amber", "JadeChip", "MudChunk", "CrackedSandPile"};
   @SideOnly(Side.CLIENT)
   private Icon[] itemTextures;


   public ItemNugget(int id) {
      super(id);
      this.setHasSubtypes(true);
      this.setUnlocalizedName("KBImetalNugget");
      this.setCreativeTab(CreativeTabs.tabMaterials);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IconRegister par1IconRegister) {
      this.itemTextures = new Icon[37];

      for(int i = 0; i < 37; ++i) {
         this.itemTextures[i] = par1IconRegister.registerIcon("KBIresources:nugget_" + i);
      }

      super.itemIcon = this.itemTextures[0];
   }

   @SideOnly(Side.CLIENT)
   public Icon getIconFromDamage(int par1) {
      return this.itemTextures[par1];
   }

   public String getUnlocalizedName(ItemStack itemstack) {
      return "KBI" + subNames[itemstack.getItemDamage()];
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if(GravelMain.moduleEnabled && GravelMain.throwRocks) {
         if(par1ItemStack.getItemDamage() == 4) {
            if(!par3EntityPlayer.capabilities.isCreativeMode) {
               --par1ItemStack.stackSize;
            }

            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (Item.itemRand.nextFloat() * 0.4F + 0.8F));
            if(!par2World.isRemote) {
               par2World.spawnEntityInWorld(new EntityRock(par2World, par3EntityPlayer));
            }
         } else if(par1ItemStack.getItemDamage() == 7) {
            if(!par3EntityPlayer.capabilities.isCreativeMode) {
               --par1ItemStack.stackSize;
            }

            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (Item.itemRand.nextFloat() * 0.4F + 0.8F));
            if(!par2World.isRemote) {
               par2World.spawnEntityInWorld(new EntityNetherrock(par2World, par3EntityPlayer));
            }
         } else if(par1ItemStack.getItemDamage() == 27) {
            if(!par3EntityPlayer.capabilities.isCreativeMode) {
               --par1ItemStack.stackSize;
            }

            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (Item.itemRand.nextFloat() * 0.4F + 0.8F));
            if(!par2World.isRemote) {
               par2World.spawnEntityInWorld(new EntityObsidian(par2World, par3EntityPlayer));
            }
         } else if(par1ItemStack.getItemDamage() == 31) {
            if(!par3EntityPlayer.capabilities.isCreativeMode) {
               --par1ItemStack.stackSize;
            }

            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (Item.itemRand.nextFloat() * 0.4F + 0.8F));
            if(!par2World.isRemote) {
               par2World.spawnEntityInWorld(new EntityRedRock(par2World, par3EntityPlayer));
            }
         }
      }

      return par1ItemStack;
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(int par1, CreativeTabs tab, List subItems) {
      for(int ix = 0; ix < 37; ++ix) {
         subItems.add(new ItemStack(this, 1, ix));
      }

   }

}
