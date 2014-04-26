package mods.KBIgravelore.common;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class GravelOreItem extends ItemBlock {

   private static final String[] subNames = new String[]{"SlightlyEnrichedGravel", "SomewhatEnrichedGravel", "ReasonablyEnrichedGravel", "EnrichedGravel", "GenerouslyEnrichedGravel", "HighlyEnrichedGravel", "HeavilyEnrichedGravel", "VeryEnrichedGravel", "Gravel", "Gravel", "Gravel", "Gravel", "Gravel", "Gravel", "Gravel", "Gravel"};


   public GravelOreItem(int id) {
      super(id);
      this.setHasSubtypes(true);
      this.setUnlocalizedName("KBIgravelOre");
   }

   public int getMetadata(int damageValue) {
      return damageValue;
   }

   public String getUnlocalizedName(ItemStack itemstack) {
      return "KBI" + subNames[itemstack.getItemDamage()];
   }

}
