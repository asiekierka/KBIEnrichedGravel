package mods.KBIgravelore.common;

import cpw.mods.fml.common.IWorldGenerator;
import java.util.Random;
import mods.KBIgravelore.common.GravelMain;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class GravelGenerator implements IWorldGenerator {

   public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
      if(world.provider.dimensionId == -1 && GravelMain.netherGen) {
         this.generateNether(world, random, chunkX * 16, chunkZ * 16);
      }

      if(world.provider.dimensionId == 1) {
         this.generateEnd(world, random, chunkX * 16, chunkZ * 16);
      }

      if(world.provider.dimensionId == 0 && GravelMain.overworldGen) {
         this.generateSurface(world, random, chunkX * 16, chunkZ * 16);
      }

      if(world.provider.dimensionId == 7 && GravelMain.twilightForestGen) {
         this.generateSurface(world, random, chunkX * 16, chunkZ * 16);
      }

      if((world.provider.dimensionId > 1 || world.provider.dimensionId < -1) && GravelMain.mystGen) {
         this.generateSurface(world, random, chunkX * 16, chunkZ * 16);
      }

   }

   private void generateEnd(World world, Random rand, int chunkX, int chunkZ) {}

   private void generateSurface(World world, Random rand, int chunkX, int chunkZ) {
      boolean generatedSlightTop = false;
      boolean generatedSlightBottom = false;
      boolean generatedAverageTop = false;
      boolean generatedAverageBottom = false;
      boolean generatedHighTop = false;
      boolean generatedHighBottom = false;

      int kt;
      int firstX;
      int firstZ;
      int k;
      Block block2;
      Block block;
      for(kt = 0; kt < 10; ++kt) {
         firstX = chunkX + rand.nextInt(16);
         firstZ = chunkZ + rand.nextInt(16);

         for(k = 1; k < 100; ++k) {
            block = Block.blocksList[world.getBlockId(firstX, k, firstZ)];
            if(block != null && (!generatedAverageBottom || rand.nextInt(9) == 0)) {
               if(block.blockID == Block.gravel.blockID) {
                  this.generateGravelVein(rand.nextInt(4) + 2, firstX, k, firstZ, rand, world);
                  generatedAverageBottom = true;
               } else if(block.blockID != Block.stone.blockID && block.blockID == Block.netherrack.blockID) {
                  ;
               }
            }

            block2 = Block.blocksList[world.getBlockId(firstX, 101 - k, firstZ)];
            if(block2 != null && (!generatedAverageTop || rand.nextInt(9) == 0)) {
               if(block2.blockID == Block.gravel.blockID) {
                  this.generateGravelVein(rand.nextInt(4) + 2, firstX, 101 - k, firstZ, rand, world);
                  generatedAverageTop = true;
               } else if(block2.blockID != Block.stone.blockID && block2.blockID == Block.netherrack.blockID) {
                  ;
               }
            }
         }
      }

      for(kt = 0; kt < 16; ++kt) {
         firstX = chunkX + rand.nextInt(16);
         firstZ = chunkZ + rand.nextInt(16);

         for(k = 1; k < 100; ++k) {
            block = Block.blocksList[world.getBlockId(firstX, k, firstZ)];
            if(block != null && (!generatedSlightBottom || rand.nextInt(9) == 0)) {
               if(block.blockID == Block.gravel.blockID) {
                  this.generateGravelVein(rand.nextInt(1), firstX, k, firstZ, rand, world);
                  generatedSlightBottom = true;
               } else if(block.blockID != Block.stone.blockID && block.blockID == Block.netherrack.blockID) {
                  ;
               }
            }

            block2 = Block.blocksList[world.getBlockId(firstX, 101 - k, firstZ)];
            if(block2 != null && (!generatedSlightTop || rand.nextInt(9) == 0)) {
               if(block2.blockID == Block.gravel.blockID) {
                  this.generateGravelVein(rand.nextInt(1), firstX, 101 - k, firstZ, rand, world);
                  generatedSlightTop = true;
               } else if(block2.blockID != Block.stone.blockID && block2.blockID == Block.netherrack.blockID) {
                  ;
               }
            }
         }
      }

      for(kt = 0; kt < 10; ++kt) {
         firstX = chunkX + rand.nextInt(16);
         firstZ = chunkZ + rand.nextInt(16);

         for(k = 1; k < 100; ++k) {
            block = Block.blocksList[world.getBlockId(firstX, k, firstZ)];
            if(block != null && (!generatedHighBottom || rand.nextInt(9) == 0)) {
               if(block.blockID == Block.gravel.blockID) {
                  this.generateGravelVein(rand.nextInt(2) + 5, firstX, k, firstZ, rand, world);
                  generatedHighBottom = true;
               } else if(block.blockID != Block.stone.blockID && block.blockID == Block.netherrack.blockID) {
                  ;
               }
            }

            block2 = Block.blocksList[world.getBlockId(firstX, 101 - k, firstZ)];
            if(block2 != null && (!generatedHighTop || rand.nextInt(9) == 0)) {
               if(block2.blockID == Block.gravel.blockID) {
                  this.generateGravelVein(rand.nextInt(2) + 5, firstX, 101 - k, firstZ, rand, world);
                  generatedHighTop = true;
               } else if(block2.blockID != Block.stone.blockID && block2.blockID == Block.netherrack.blockID) {
                  ;
               }
            }
         }
      }

   }

   private void generateGravelVein(int meta, int x, int y, int z, Random rand, World world) {
      --x;
      --y;
      --z;

      for(int ecks = 0; ecks < 3; ++ecks) {
         for(int why = 0; why < 3; ++why) {
            for(int zee = 0; zee < 3; ++zee) {
               int replaceblock = rand.nextInt(2);
               if(replaceblock != 0) {
                  Block block = Block.blocksList[world.getBlockId(x + ecks, y + why, z + zee)];
                  if(block != null && block.blockID == Block.gravel.blockID) {
                     world.setBlock(x + ecks, y + why, z + zee, GravelMain.gravelOre.blockID, meta, 2);
                  }
               }
            }
         }
      }

   }

   private void generateNether(World world, Random rand, int chunkX, int chunkZ) {
      boolean generatedSlightTop = false;
      boolean generatedSlightBottom = false;
      boolean generatedAverageTop = false;
      boolean generatedAverageBottom = false;
      boolean generatedHighTop = false;
      boolean generatedHighBottom = false;

      int kt;
      int firstX;
      int firstZ;
      int k;
      Block block2;
      Block block;
      for(kt = 0; kt < 8; ++kt) {
         firstX = chunkX + rand.nextInt(16);
         firstZ = chunkZ + rand.nextInt(16);

         for(k = 1; k < 100; ++k) {
            block = Block.blocksList[world.getBlockId(firstX, k, firstZ)];
            if(block != null && (!generatedAverageBottom || rand.nextInt(9) == 0)) {
               if(block.blockID == Block.gravel.blockID) {
                  this.generateGravelVein(rand.nextInt(4) + 2, firstX, k, firstZ, rand, world);
                  generatedAverageBottom = true;
               } else if(block.blockID != Block.stone.blockID && block.blockID == Block.netherrack.blockID) {
                  ;
               }
            }

            block2 = Block.blocksList[world.getBlockId(firstX, 101 - k, firstZ)];
            if(block2 != null && (!generatedAverageTop || rand.nextInt(9) == 0)) {
               if(block2.blockID == Block.gravel.blockID) {
                  this.generateGravelVein(rand.nextInt(4) + 2, firstX, 101 - k, firstZ, rand, world);
                  generatedAverageTop = true;
               } else if(block2.blockID != Block.stone.blockID && block2.blockID == Block.netherrack.blockID) {
                  ;
               }
            }
         }
      }

      for(kt = 0; kt < 14; ++kt) {
         firstX = chunkX + rand.nextInt(16);
         firstZ = chunkZ + rand.nextInt(16);

         for(k = 1; k < 100; ++k) {
            block = Block.blocksList[world.getBlockId(firstX, k, firstZ)];
            if(block != null && (!generatedSlightBottom || rand.nextInt(9) == 0)) {
               if(block.blockID == Block.gravel.blockID) {
                  this.generateGravelVein(rand.nextInt(1), firstX, k, firstZ, rand, world);
                  generatedSlightBottom = true;
               } else if(block.blockID != Block.stone.blockID && block.blockID == Block.netherrack.blockID) {
                  ;
               }
            }

            block2 = Block.blocksList[world.getBlockId(firstX, 101 - k, firstZ)];
            if(block2 != null && (!generatedSlightTop || rand.nextInt(9) == 0)) {
               if(block2.blockID == Block.gravel.blockID) {
                  this.generateGravelVein(rand.nextInt(1), firstX, 101 - k, firstZ, rand, world);
                  generatedSlightTop = true;
               } else if(block2.blockID != Block.stone.blockID && block2.blockID == Block.netherrack.blockID) {
                  ;
               }
            }
         }
      }

      for(kt = 0; kt < 16; ++kt) {
         firstX = chunkX + rand.nextInt(16);
         firstZ = chunkZ + rand.nextInt(16);

         for(k = 1; k < 100; ++k) {
            block = Block.blocksList[world.getBlockId(firstX, k, firstZ)];
            if(block != null && (!generatedHighBottom || rand.nextInt(9) == 0)) {
               if(block.blockID == Block.gravel.blockID) {
                  this.generateGravelVein(rand.nextInt(2) + 5, firstX, k, firstZ, rand, world);
                  generatedHighBottom = true;
               } else if(block.blockID != Block.stone.blockID && block.blockID == Block.netherrack.blockID) {
                  ;
               }
            }

            block2 = Block.blocksList[world.getBlockId(firstX, 101 - k, firstZ)];
            if(block2 != null && (!generatedHighTop || rand.nextInt(9) == 0)) {
               if(block2.blockID == Block.gravel.blockID) {
                  this.generateGravelVein(rand.nextInt(2) + 5, firstX, 101 - k, firstZ, rand, world);
                  generatedHighTop = true;
               } else if(block2.blockID != Block.stone.blockID && block2.blockID == Block.netherrack.blockID) {
                  ;
               }
            }
         }
      }

   }
}
