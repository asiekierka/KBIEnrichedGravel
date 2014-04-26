package mods.KBIgravelore.common;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityNetherrock extends EntityThrowable {

   public EntityNetherrock(World par1World) {
      super(par1World);
   }

   public EntityNetherrock(World par1World, EntityLivingBase par2EntityLiving) {
      super(par1World, par2EntityLiving);
   }

   public EntityNetherrock(World par1World, double par2, double par4, double par6) {
      super(par1World, par2, par4, par6);
   }

   protected void onImpact(MovingObjectPosition movingobjectposition) {
      if(movingobjectposition.entityHit != null) {
         byte i = 3;
         if(movingobjectposition.entityHit instanceof EntityLiving && ((EntityLiving)movingobjectposition.entityHit).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
            movingobjectposition.entityHit.setFire(2 * super.worldObj.difficultySetting);
         }

         movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), i);
      }

      for(int var3 = 0; var3 < 8; ++var3) {
         ;
      }

      if(!super.worldObj.isRemote) {
         this.setDead();
      }

   }
}
