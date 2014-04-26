package mods.KBIgravelore.common;

import mods.KBIgravelore.common.EntityNetherrock;
import mods.KBIgravelore.common.EntityObsidian;
import mods.KBIgravelore.common.EntityRedRock;
import mods.KBIgravelore.common.EntityRock;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

final class DispenserBehaviorRocks extends BehaviorProjectileDispense {

   private int typeToDispense;


   public DispenserBehaviorRocks(int type) {
      this.typeToDispense = type;
   }

   protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition) {
      return (IProjectile)(this.typeToDispense == 1?new EntityNetherrock(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ()):(this.typeToDispense == 2?new EntityObsidian(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ()):(this.typeToDispense == 3?new EntityRedRock(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ()):new EntityRock(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ()))));
   }
}
