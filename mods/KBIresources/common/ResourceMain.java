package mods.KBIresources.common;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import java.io.File;
import java.util.ArrayList;
import mods.KBIresources.common.CommonProxy;
import mods.KBIresources.common.ItemNugget;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@Mod(
   modid = "KBIresources",
   name = "KBI Resources",
   version = "1.1.0",
   dependencies = "after:karuberu-mudMod;after:TC3;after:Thaumcraft;after:Thaumcraft3;after:IC2;after:GregTech;after:Forestry;after:EBXL;after:BiomesOPlenty;before:KBIgravelore;"
)
@NetworkMod(
   clientSideRequired = true,
   serverSideRequired = false
)
public class ResourceMain {

   @Instance("KBIresources")
   public static ResourceMain instance;
   @SidedProxy(
      clientSide = "mods.KBIresources.client.ClientProxy",
      serverSide = "mods.KBIresources.common.CommonProxy"
   )
   public static CommonProxy proxy;
   public static Item nuggets;
   public static int nuggetsID;
   public static boolean copper;
   public static boolean tin;
   public static boolean silver;
   public static boolean lead;
   public static boolean aluminum;
   public static boolean nickel;
   public static boolean platinum;
   public static boolean ruby;
   public static boolean sapphire;
   public static boolean greenSapphire;
   public static boolean apatite;
   public static boolean titanium;
   public static boolean sulfur;
   public static boolean saltpeter;
   public static boolean zinc;
   public static boolean cobalt;
   public static boolean ardite;
   public static boolean peat;
   public static boolean redrock;
   public static boolean amber;
   public static boolean jade;
   public static boolean mud;
   public static boolean crackedSand_config;
   public static boolean moduleEnabled;
   public static boolean reverse;
   public static boolean easyLapis;
   private static final String[] nuggetNames = new String[]{"Iron Nugget", "Redstone Chunk", "Lapis Chunk", "Clay Chunk", "Stone Chunk", "Dirt Chunk", "Sand Pile", "Netherrack Chunk", "Soul Sand Pile", "Diamond Chip", "Emerald Chip", "Copper Nugget", "Tin Nugget", "Silver Nugget", "Lead Nugget", "Aluminum Nugget", "Nickel Nugget", "Platinum Nugget", "Ruby Chip", "Sapphire Chip", "Green Sapphire Chip", "Apatite Chip", "Titanium Nugget", "Sulfur Pile", "Saltpeter Pile", "Zinc Nugget", "Coal Chunk", "Obsidian Chunk", "Cobalt Nugget", "Ardite Nugget", "Peat Chunk", "Red Rock Chunk", "Nether Quartz Chunk", "Amber", "Jade Chip", "Mud", "Cracked Sand Pile"};


   @PreInit
   public void preInit(FMLPreInitializationEvent event) {
      File sector = new File(proxy.getMinecraftDir() + "/config/KBI");
      if(!sector.exists()) {
         sector.mkdirs();
      }

      Configuration main = new Configuration(new File(sector.getPath() + "/Main.cfg"));
      main.load();
      moduleEnabled = main.get("Modules", "Resources", true).getBoolean(true);
      main.save();
      if(moduleEnabled) {
         Configuration config = new Configuration(new File(sector.getPath() + "/Resources.cfg"));
         config.load();
         nuggetsID = config.getItem("Nuggets and Chunks and Chips", 4744).getInt();
         copper = config.get("Content", "Copper", true).getBoolean(true);
         tin = config.get("Content", "Tin", true).getBoolean(true);
         silver = config.get("Content", "Silver", true).getBoolean(true);
         lead = config.get("Content", "Lead", true).getBoolean(true);
         aluminum = config.get("Content", "Aluminum", true).getBoolean(true);
         nickel = config.get("Content", "Nickel", true).getBoolean(true);
         platinum = config.get("Content", "Platinum", true).getBoolean(true);
         ruby = config.get("Content", "Ruby", true).getBoolean(true);
         sapphire = config.get("Content", "Sapphire", true).getBoolean(true);
         greenSapphire = config.get("Content", "Green Sapphire", true).getBoolean(true);
         apatite = config.get("Content", "Apatite", true).getBoolean(true);
         titanium = config.get("Content", "Titanium", true).getBoolean(true);
         sulfur = config.get("Content", "Sulfur", true).getBoolean(true);
         saltpeter = config.get("Content", "Saltpeter", true).getBoolean(true);
         zinc = config.get("Content", "Zinc", true).getBoolean(true);
         cobalt = config.get("Content", "Cobalt", true).getBoolean(true);
         ardite = config.get("Content", "Ardite", true).getBoolean(true);
         peat = config.get("Content", "Peat", true).getBoolean(true);
         amber = config.get("Content", "Amber", true).getBoolean(true);
         jade = config.get("Content", "Jade", true).getBoolean(true);
         mud = config.get("Content", "Mud", true).getBoolean(true);
         redrock = config.get("Content", "Red Rock", true).getBoolean(true);
         crackedSand_config = config.get("Content", "Cracked Sand", true).getBoolean(true);
         reverse = config.get("Crafting", "Enable crafting resources into nuggets/chunks", true).getBoolean(true);
         easyLapis = config.get("Crafting", "Enable 4x4 Lapis Lazuli Recipe", true).getBoolean(true);
         config.save();
      }

   }

   @Init
   public void init(FMLInitializationEvent event) {
      if(moduleEnabled) {
         nuggets = new ItemNugget(nuggetsID);
         LanguageRegistry.addName(nuggets, "Metallic Nugget");

         for(int r = 0; r < 37; ++r) {
            ItemStack nuggetStack = new ItemStack(nuggets, 1, r);
            LanguageRegistry.addName(nuggetStack, nuggetNames[nuggetStack.getItemDamage()]);
         }

         this.doOreDictionary();
      }

   }

   @PostInit
   public static void postInit(FMLPostInitializationEvent event) {
      if(moduleEnabled) {
         doOreDictionaryPartTwo();
      }

   }

   public void doOreDictionary() {
      OreDictionary.registerOre("nuggetIron", new ItemStack(nuggets, 1, 0));
      OreDictionary.registerOre("nuggetRedstone", new ItemStack(nuggets, 1, 1));
      OreDictionary.registerOre("chunkRedstone", new ItemStack(nuggets, 1, 1));
      OreDictionary.registerOre("dustSmallRedstone", new ItemStack(nuggets, 1, 1));
      OreDictionary.registerOre("nuggetLapis", new ItemStack(nuggets, 1, 2));
      OreDictionary.registerOre("chunkLapis", new ItemStack(nuggets, 1, 2));
      OreDictionary.registerOre("chunkClay", new ItemStack(nuggets, 1, 3));
      OreDictionary.registerOre("chunkStone", new ItemStack(nuggets, 1, 4));
      OreDictionary.registerOre("chunkDirt", new ItemStack(nuggets, 1, 5));
      OreDictionary.registerOre("pileDirt", new ItemStack(nuggets, 1, 5));
      OreDictionary.registerOre("pileSand", new ItemStack(nuggets, 1, 6));
      OreDictionary.registerOre("chunkNetherrack", new ItemStack(nuggets, 1, 7));
      OreDictionary.registerOre("pileSoulSand", new ItemStack(nuggets, 1, 8));
      OreDictionary.registerOre("nuggetDiamond", new ItemStack(nuggets, 1, 9));
      OreDictionary.registerOre("chipDiamond", new ItemStack(nuggets, 1, 9));
      OreDictionary.registerOre("nuggetEmerald", new ItemStack(nuggets, 1, 10));
      OreDictionary.registerOre("chipEmerald", new ItemStack(nuggets, 1, 10));
      if(copper) {
         OreDictionary.registerOre("nuggetCopper", new ItemStack(nuggets, 1, 11));
      }

      if(tin) {
         OreDictionary.registerOre("nuggetTin", new ItemStack(nuggets, 1, 12));
      }

      if(silver) {
         OreDictionary.registerOre("nuggetSilver", new ItemStack(nuggets, 1, 13));
      }

      if(lead) {
         OreDictionary.registerOre("nuggetLead", new ItemStack(nuggets, 1, 14));
      }

      if(aluminum) {
         OreDictionary.registerOre("nuggetAluminum", new ItemStack(nuggets, 1, 15));
         OreDictionary.registerOre("nuggetAluminium", new ItemStack(nuggets, 1, 15));
      }

      if(nickel) {
         OreDictionary.registerOre("nuggetNickel", new ItemStack(nuggets, 1, 16));
      }

      if(platinum) {
         OreDictionary.registerOre("nuggetPlatinum", new ItemStack(nuggets, 1, 17));
      }

      if(ruby) {
         OreDictionary.registerOre("chipRuby", new ItemStack(nuggets, 1, 18));
         OreDictionary.registerOre("nuggetRuby", new ItemStack(nuggets, 1, 18));
      }

      if(sapphire) {
         OreDictionary.registerOre("chipSapphire", new ItemStack(nuggets, 1, 19));
         OreDictionary.registerOre("nuggetSapphire", new ItemStack(nuggets, 1, 19));
      }

      if(greenSapphire) {
         OreDictionary.registerOre("chipGreenSapphire", new ItemStack(nuggets, 1, 20));
         OreDictionary.registerOre("nuggetGreenSapphire", new ItemStack(nuggets, 1, 20));
         OreDictionary.registerOre("chipSapphireGreen", new ItemStack(nuggets, 1, 20));
         OreDictionary.registerOre("nuggetSapphireGreen", new ItemStack(nuggets, 1, 20));
      }

      if(apatite) {
         OreDictionary.registerOre("chipApatite", new ItemStack(nuggets, 1, 21));
         OreDictionary.registerOre("nuggetApatite", new ItemStack(nuggets, 1, 21));
      }

      if(titanium) {
         OreDictionary.registerOre("nuggetTitanium", new ItemStack(nuggets, 1, 22));
      }

      if(sulfur) {
         OreDictionary.registerOre("dustSmallSulfur", new ItemStack(nuggets, 1, 23));
      }

      if(saltpeter) {
         OreDictionary.registerOre("dustSmallSaltpeter", new ItemStack(nuggets, 1, 24));
      }

      if(zinc) {
         OreDictionary.registerOre("nuggetZinc", new ItemStack(nuggets, 1, 25));
      }

      OreDictionary.registerOre("chunkCoal", new ItemStack(nuggets, 1, 26));
      OreDictionary.registerOre("chunkObsidian", new ItemStack(nuggets, 1, 27));
      if(cobalt) {
         OreDictionary.registerOre("nuggetCobalt", new ItemStack(nuggets, 1, 28));
      }

      if(ardite) {
         OreDictionary.registerOre("nuggetArdite", new ItemStack(nuggets, 1, 29));
      }

      String itemClass;
      Object e;
      if(peat) {
         OreDictionary.registerOre("chunkPeat", new ItemStack(nuggets, 1, 30));
         itemClass = "karuberu.mods.mudmod.MudMod";

         try {
            e = Class.forName(itemClass).getField("peatBrick").get((Object)null);
            if((Item)e != null) {
               FMLLog.info("[KBI] Found Karuberu\'s Mud Mod; adding peat to brickPeat", new Object[0]);
               OreDictionary.registerOre("brickPeat", new ItemStack((Item)e, 1, 0));
            } else {
               FMLLog.info("[KBI] Found Karuberu\'s Mud Mod, but could not add to chunkMud", new Object[0]);
            }
         } catch (Exception var10) {
            FMLLog.warning("[KBI] Could not find Karuberu\'s Mud Mod, not adding to chunkMud", new Object[0]);
         }
      }

      if(redrock) {
         OreDictionary.registerOre("chunkRedRock", new ItemStack(nuggets, 1, 31));
         itemClass = "extrabiomes.lib.Element";

         try {
            e = Class.forName(itemClass).getMethod("get", new Class[0]).invoke(Class.forName(itemClass).getField("RED_COBBLE").get((Object)null), new Object[0]);
            if((ItemStack)e != null) {
               FMLLog.info("[KBI] Found EBXL; adding redrock to stoneRedRock", new Object[0]);
               OreDictionary.registerOre("stoneRedRock", (ItemStack)e);
            } else {
               FMLLog.info("[KBI] Found EBXL, but could not redrock to stoneRedRock", new Object[0]);
            }
         } catch (Exception var9) {
            FMLLog.warning("[KBI] Could not find EBXL, not adding redrock to stoneRedRock", new Object[0]);
         }

         String e2 = "biomesoplenty.api.BlockReferences";

         try {
            Object e1 = Class.forName(e2).getMethod("getBlockItemStack", new Class[]{String.class}).invoke((Object)null, new Object[]{"redRockCobble"});
            if((ItemStack)e1 != null) {
               OreDictionary.registerOre("stoneRedRock", (ItemStack)e1);
               FMLLog.info("[KBI] Found BoP API; adding redrock to stoneRedRock", new Object[0]);
            } else {
               FMLLog.info("[KBI] Found BoP API, not adding redrock to stoneRedRock", new Object[0]);
            }
         } catch (Exception var8) {
            FMLLog.warning("[KBI] Could not find BoP API; not adding redrock to stoneRedRock", new Object[0]);
         }
      }

      OreDictionary.registerOre("chunkNetherQuartz", new ItemStack(nuggets, 1, 32));
      if(amber) {
         OreDictionary.registerOre("gemAmber", new ItemStack(nuggets, 1, 33));
         itemClass = "thaumcraft.api.ItemApi";

         try {
            e = Class.forName(itemClass).getMethod("getItem", new Class[]{String.class, Integer.TYPE}).invoke((Object)null, new Object[]{"itemResource", Integer.valueOf(6)});
            if((ItemStack)e != null) {
               OreDictionary.registerOre("gemAmber", (ItemStack)e);
               GameRegistry.addRecipe(new ShapelessOreRecipe((ItemStack)e, new Object[]{new ItemStack(nuggets, 1, 33)}));
               GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 1, 33), new Object[]{(ItemStack)e}));
               FMLLog.info("[KBI] Found ThaumCraft 3 API; adding TC3 Amber to gemAmber", new Object[0]);
            } else {
               FMLLog.info("[KBI] Found ThaumCraft 3 API, but could not add TC3 Amber to gemAmber", new Object[0]);
            }
         } catch (Exception var7) {
            FMLLog.warning("[KBI] Could not find ThaumCraft 3 API; not adding TC3 Amber to gemAmber", new Object[0]);
         }
      }

      if(jade) {
         OreDictionary.registerOre("nuggetJade", new ItemStack(nuggets, 1, 34));
         OreDictionary.registerOre("chipJade", new ItemStack(nuggets, 1, 34));
      }

      if(mud) {
         OreDictionary.registerOre("chunkMud", new ItemStack(nuggets, 1, 35));
         itemClass = "karuberu.mods.mudmod.MudMod";

         try {
            e = Class.forName(itemClass).getField("mudBlob").get((Object)null);
            if((Item)e != null) {
               FMLLog.info("[KBI] Found Karuberu\'s Mud Mod; adding to chunkMud", new Object[0]);
               OreDictionary.registerOre("chunkMud", new ItemStack((Item)e, 1, 0));
               GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack((Item)e, 1, 0), new Object[]{new ItemStack(nuggets, 1, 35)}));
               GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 1, 35), new Object[]{new ItemStack((Item)e, 1, 0)}));
            } else {
               FMLLog.info("[KBI] Found Karuberu\'s Mud Mod, but could not add to chunkMud", new Object[0]);
            }
         } catch (Exception var6) {
            FMLLog.warning("[KBI] Could not find Karuberu\'s Mud Mod, not adding to chunkMud", new Object[0]);
         }
      }

      if(crackedSand_config) {
         OreDictionary.registerOre("pileCrackedSand", new ItemStack(nuggets, 1, 36));
         itemClass = "extrabiomes.lib.Element";

         try {
            e = Class.forName(itemClass).getMethod("get", new Class[0]).invoke(Class.forName(itemClass).getField("CRACKEDSAND").get((Object)null), new Object[0]);
            if((ItemStack)e != null) {
               FMLLog.info("[KBI] Found EBXL; adding cracked sand recipes", new Object[0]);
               OreDictionary.registerOre("sandCracked", (ItemStack)e);
            } else {
               FMLLog.info("[KBI] Found EBXL, but could not add cracked sand recipes", new Object[0]);
            }
         } catch (Exception var5) {
            FMLLog.warning("[KBI] Could not find EBXL, not adding cracked sand recipes", new Object[0]);
         }
      }

      if(reverse) {
         GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 0), new Object[]{Item.ingotIron}));
         GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 4, 1), new Object[]{Item.redstone}));
         GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 4, 4), new Object[]{Block.cobblestone}));
         GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 4, 5), new Object[]{Block.dirt}));
         GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 4, 6), new Object[]{Block.sand}));
         GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 4, 7), new Object[]{Block.netherrack}));
         GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 4, 8), new Object[]{Block.slowSand}));
         GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 9), new Object[]{Item.diamond}));
         GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 10), new Object[]{Item.emerald}));
         if(copper) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 11), new Object[]{"ingotCopper"}));
         }

         if(tin) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 12), new Object[]{"ingotTin"}));
         }

         if(silver) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 13), new Object[]{"ingotSilver"}));
         }

         if(lead) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 14), new Object[]{"ingotLead"}));
         }

         if(aluminum) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 15), new Object[]{"ingotAluminum"}));
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 15), new Object[]{"ingotAluminium"}));
         }

         if(nickel) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 16), new Object[]{"ingotNickel"}));
         }

         if(platinum) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 17), new Object[]{"ingotPlatinum"}));
         }

         if(ruby) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 18), new Object[]{"gemRuby"}));
         }

         if(sapphire) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 19), new Object[]{"gemSapphire"}));
         }

         if(greenSapphire) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 20), new Object[]{"gemSapphireGreen"}));
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 20), new Object[]{"gemGreenSapphire"}));
         }

         if(apatite) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 21), new Object[]{"gemApatite"}));
         }

         if(titanium) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 22), new Object[]{"ingotTitanium"}));
         }

         if(sulfur) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 4, 23), new Object[]{"dustSulfur"}));
            itemClass = "thermalexpansion.api.item.ItemRegistry";

            try {
               e = Class.forName(itemClass).getMethod("getItem", new Class[]{String.class, Integer.TYPE}).invoke((Object)null, new Object[]{"crystalSulfur", Integer.valueOf(1)});
               if((ItemStack)e != null) {
                  OreDictionary.registerOre("crystalSulfur", (ItemStack)e);
                  GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 4, 23), new Object[]{(ItemStack)e}));
                  FMLLog.info("[KBI] Found Thermal Expansion API; adding Sulfur Crystal to dust recipe", new Object[0]);
               } else {
                  FMLLog.info("[KBI] Found Thermal Expansion API, but could not add Sulfur Crystal to dust recipe", new Object[0]);
               }
            } catch (Exception var4) {
               FMLLog.warning("[KBI] Could not find Thermal Expansion API; not adding Sulfur Crystal to dust recipe", new Object[0]);
            }
         }

         if(saltpeter) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 4, 24), new Object[]{"dustSaltpeter"}));
         }

         if(zinc) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 25), new Object[]{"ingotZinc"}));
         }

         GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 4, 26), new Object[]{Item.coal}));
         GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 4, 27), new Object[]{Block.obsidian}));
         if(cobalt) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 28), new Object[]{"ingotCobalt"}));
         }

         if(ardite) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 29), new Object[]{"ingotArdite"}));
         }

         if(peat) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 4, 30), new Object[]{"brickPeat"}));
         }

         GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 4, 32), new Object[]{Item.netherQuartz}));
         if(jade) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 4, 34), new Object[]{"gemJade"}));
         }

         if(redrock) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 4, 31), new Object[]{"stoneRedRock"}));
         }

         if(crackedSand_config) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 4, 36), new Object[]{"sandCracked"}));
         }
      }

      GameRegistry.addRecipe(new ShapedOreRecipe(Item.ingotIron, new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetIron"}}));
      GameRegistry.addRecipe(new ShapedOreRecipe(Item.redstone, new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "nuggetRedstone"}}));
      GameRegistry.addRecipe(new ShapedOreRecipe(Item.redstone, new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "chunkRedstone"}}));
      GameRegistry.addRecipe(new ShapedOreRecipe(Item.redstone, new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "dustSmallRedstone"}}));
      if(!easyLapis) {
         GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 1, 4), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "chunkLapis"}}));
         if(reverse) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 9, 2), new Object[]{new ItemStack(Item.dyePowder, 1, 4)}));
         }
      } else {
         GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 2, 4), new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "chunkLapis"}}));
         if(reverse) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggets, 2, 2), new Object[]{new ItemStack(Item.dyePowder, 1, 4)}));
         }
      }

      GameRegistry.addRecipe(new ShapedOreRecipe(Item.clay, new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "chunkClay"}}));
      GameRegistry.addRecipe(new ShapedOreRecipe(Block.cobblestone, new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "chunkStone"}}));
      GameRegistry.addRecipe(new ShapedOreRecipe(Block.dirt, new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "chunkDirt"}}));
      GameRegistry.addRecipe(new ShapedOreRecipe(Block.dirt, new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "pileDirt"}}));
      GameRegistry.addRecipe(new ShapedOreRecipe(Block.sand, new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "pileSand"}}));
      GameRegistry.addRecipe(new ShapedOreRecipe(Block.netherrack, new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "chunkNetherrack"}}));
      GameRegistry.addRecipe(new ShapedOreRecipe(Block.slowSand, new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "pileSoulSand"}}));
      GameRegistry.addRecipe(new ShapedOreRecipe(Item.diamond, new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetDiamond"}}));
      GameRegistry.addRecipe(new ShapedOreRecipe(Item.diamond, new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "chipDiamond"}}));
      GameRegistry.addRecipe(new ShapedOreRecipe(Item.emerald, new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetEmerald"}}));
      GameRegistry.addRecipe(new ShapedOreRecipe(Item.emerald, new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "chipEmerald"}}));
      GameRegistry.addRecipe(new ShapedOreRecipe(Item.coal, new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "chunkCoal"}}));
      GameRegistry.addRecipe(new ShapedOreRecipe(Block.obsidian, new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "chunkObsidian"}}));
      GameRegistry.addRecipe(new ShapedOreRecipe(Item.netherQuartz, new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "chunkNetherQuartz"}}));
   }

   public static void doOreDictionaryPartTwo() {
      ArrayList crackedSand;
      if(copper) {
         crackedSand = OreDictionary.getOres("ingotCopper");
         if(crackedSand.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetCopper"}}));
         }
      }

      if(tin) {
         crackedSand = OreDictionary.getOres("ingotTin");
         if(crackedSand.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetTin"}}));
         }
      }

      if(silver) {
         crackedSand = OreDictionary.getOres("ingotSilver");
         if(crackedSand.size() > 0) {
            System.out.println("Registering silver recipe for KBIGravel nuggets");
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetSilver"}}));
         }
      }

      if(lead) {
         crackedSand = OreDictionary.getOres("ingotLead");
         if(crackedSand.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetLead"}}));
         }
      }

      ArrayList itemClass;
      if(aluminum) {
         crackedSand = OreDictionary.getOres("ingotAluminum");
         itemClass = OreDictionary.getOres("ingotAluminium");
         if(crackedSand.size() > 0 && itemClass.size() <= 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetAluminum"}}));
         } else if(itemClass.size() > 0 && crackedSand.size() <= 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)itemClass.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetAluminium"}}));
         } else if(crackedSand.size() > 0 && itemClass.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetAluminium"}}));
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)itemClass.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"F", Character.valueOf('F'), "ingotAluminum"}}));
         }
      }

      if(nickel) {
         crackedSand = OreDictionary.getOres("ingotNickel");
         if(crackedSand.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetNickel"}}));
         }
      }

      if(platinum) {
         crackedSand = OreDictionary.getOres("ingotPlatinum");
         if(crackedSand.size() > 0) {
            System.out.println("Registering platinum recipe for KBIGravel nuggets");
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetPlatinum"}}));
         }
      }

      if(ruby) {
         crackedSand = OreDictionary.getOres("gemRuby");
         if(crackedSand.size() > 0) {
            System.out.println("Registering ruby recipe for KBIGravel nuggets");
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "chipRuby"}}));
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetRuby"}}));
         }
      }

      if(sapphire) {
         crackedSand = OreDictionary.getOres("gemSapphire");
         if(crackedSand.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "chipSapphire"}}));
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetSapphire"}}));
         }
      }

      if(greenSapphire) {
         crackedSand = OreDictionary.getOres("gemSapphireGreen");
         if(crackedSand.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "chipSapphireGreen"}}));
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetSapphireGreen"}}));
         }

         itemClass = OreDictionary.getOres("gemGreenSapphire");
         if(itemClass.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)itemClass.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "chipGreenSapphire"}}));
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)itemClass.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetGreenSapphire"}}));
         }
      }

      if(apatite) {
         crackedSand = OreDictionary.getOres("gemApatite");
         if(crackedSand.size() > 0) {
            System.out.println("Registering apatite recipe for KBIGravel nuggets");
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "chipApatite"}}));
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetApatite"}}));
         }
      }

      if(titanium) {
         crackedSand = OreDictionary.getOres("ingotTitanium");
         if(crackedSand.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetTitanium"}}));
         }
      }

      if(sulfur) {
         crackedSand = OreDictionary.getOres("dustSulfur");
         if(crackedSand.size() > 0) {
            System.out.println("Registering sulfur recipe for KBIGravel piles");
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "dustSmallSulfur"}}));
         }

         String itemClass1 = "thermalexpansion.api.item.ItemRegistry";

         try {
            Object e = Class.forName(itemClass1).getMethod("getItem", new Class[]{String.class, Integer.TYPE}).invoke((Object)null, new Object[]{"crystalSulfur", Integer.valueOf(1)});
            if((ItemStack)e != null) {
               OreDictionary.registerOre("crystalSulfur", (ItemStack)e);
               GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)e, new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "dustSmallSulfur"}}));
               FMLLog.info("[KBI] Found Thermal Expansion API; adding dust to Sulfur Crystal recipe", new Object[0]);
            } else {
               FMLLog.info("[KBI] Found Thermal Expansion API, but could not add dust to Sulfur Crystal recipe", new Object[0]);
            }
         } catch (Exception var3) {
            FMLLog.warning("[KBI] Could not find Thermal Expansion API; not adding dust to Sulfur Crystal recipe", new Object[0]);
         }
      }

      if(saltpeter) {
         crackedSand = OreDictionary.getOres("dustSaltpeter");
         if(crackedSand.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "dustSmallSaltpeter"}}));
         }
      }

      if(zinc) {
         crackedSand = OreDictionary.getOres("ingotZinc");
         if(crackedSand.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetZinc"}}));
         }
      }

      if(cobalt) {
         crackedSand = OreDictionary.getOres("ingotCobalt");
         if(crackedSand.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetCobalt"}}));
         }
      }

      if(ardite) {
         crackedSand = OreDictionary.getOres("ingotArdite");
         if(crackedSand.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", "FFF", Character.valueOf('F'), "nuggetArdite"}}));
         }
      }

      if(peat) {
         crackedSand = OreDictionary.getOres("brickPeat");
         if(crackedSand.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "chunkPeat"}}));
         }
      }

      if(redrock) {
         crackedSand = OreDictionary.getOres("stoneRedRock");
         if(crackedSand.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "chunkRedRock"}}));
         }
      }

      if(jade) {
         crackedSand = OreDictionary.getOres("gemJade");
         if(crackedSand.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", Character.valueOf('F'), "nuggetJade"}}));
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FFF", "FFF", Character.valueOf('F'), "chipJade"}}));
         }
      }

      if(crackedSand_config) {
         crackedSand = OreDictionary.getOres("sandCracked");
         if(crackedSand.size() > 0) {
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack)crackedSand.get(0), new Object[]{Boolean.valueOf(true), new Object[]{"FF", "FF", Character.valueOf('F'), "pileCrackedSand"}}));
         }
      }

   }

}
