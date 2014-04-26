package mods.KBIgravelore.common;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import java.io.File;
import mods.KBIgravelore.api.CustomSifterDropAPI;
import mods.KBIgravelore.api.GravelDropAPI;
import mods.KBIgravelore.common.BlockSifter;
import mods.KBIgravelore.common.BucketGravel;
import mods.KBIgravelore.common.CommonProxy;
import mods.KBIgravelore.common.EntityNetherrock;
import mods.KBIgravelore.common.EntityObsidian;
import mods.KBIgravelore.common.EntityRedRock;
import mods.KBIgravelore.common.EntityRock;
import mods.KBIgravelore.common.EventHandler;
import mods.KBIgravelore.common.GravelGenerator;
import mods.KBIgravelore.common.GravelOreItem;
import mods.KBIgravelore.common.ItemSifter;
import mods.KBIgravelore.common.OreGravel;
import mods.KBIresources.common.ResourceMain;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@Mod(
   modid = "KBIgravelore",
   name = "KBI Enriched Gravel",
   version = "1.3.0",
   dependencies = "after:KBIresources"
)
@NetworkMod(
   clientSideRequired = true,
   serverSideRequired = false
)
public class GravelMain {

   @Instance("KBIgravelore")
   public static GravelMain instance;
   @SidedProxy(
      clientSide = "mods.KBIgravelore.client.ClientProxy",
      serverSide = "mods.KBIgravelore.common.CommonProxy"
   )
   public static CommonProxy proxy;
   public static boolean moduleEnabled = false;
   public static Block gravelOre;
   public static Block blockSifter;
   public static Item nuggets = Item.flint;
   public static Item sifter;
   public static Item sifter2;
   public static Item bucket;
   public static int gravelOreID;
   public static int blockSifterID;
   public static int sifterID;
   public static int sifter2ID;
   public static int bucketID;
   public static boolean diamondShards;
   public static boolean netherMats;
   public static boolean copper;
   public static boolean tin;
   public static boolean silver;
   public static boolean lead;
   public static boolean aluminum;
   public static boolean emeraldShards;
   public static boolean titanium;
   public static boolean dusts;
   public static boolean zinc;
   public static boolean redstone;
   public static boolean lapis;
   public static boolean coal;
   public static boolean iron;
   public static boolean gold;
   public static boolean nickel;
   public static boolean platinum;
   public static boolean obsidian;
   public static boolean gems;
   public static boolean forestry;
   public static boolean cobaltArdite;
   public static boolean amber;
   public static boolean jade;
   public static boolean mud;
   public static boolean redrock;
   public static boolean crackedSand;
   public static boolean gunPowder;
   public static boolean enableSifterBlock;
   public static boolean enableEnriched;
   public static boolean enableBuckets;
   public static boolean enableHandheldSifters;
   public static boolean aluminumSifter;
   public static boolean throwRocks;
   public static boolean overworldGen;
   public static boolean netherGen;
   public static boolean twilightForestGen;
   public static boolean mystGen;
   private static final String[] oreNames = new String[]{"Slightly Enriched Gravel", "Somewhat Enriched Gravel", "Reasonably Enriched Gravel", "Enriched Gravel", "Generously Enriched Gravel", "Highly Enriched Gravel", "Heavily Enriched Gravel", "Very Enriched Gravel", "Gravel", "Gravel", "Gravel", "Gravel", "Gravel", "Gravel", "Gravel", "Gravel"};


   @PreInit
   public void preInit(FMLPreInitializationEvent event) {
      File sector = new File(proxy.getMinecraftDir() + "/config/KBI");
      if(!sector.exists()) {
         sector.mkdirs();
      }

      Configuration main = new Configuration(new File(sector.getPath() + "/Main.cfg"));
      main.load();
      moduleEnabled = main.get("Modules", "Enriched Gravel", true).getBoolean(true);
      main.save();
      if(moduleEnabled) {
         Configuration config = new Configuration(new File(sector.getPath() + "/Enriched Gravel.cfg"));
         config.load();
         gravelOreID = config.getBlock("Gravel Ore", 1550).getInt();
         blockSifterID = config.getBlock("Sifter Block", 1551).getInt();
         sifterID = config.getItem("Sifter", 4745).getInt();
         sifter2ID = config.getItem("Sturdy Sifter", 4746).getInt();
         bucketID = config.getItem("Bucket of Gravel", 4747).getInt();
         netherGen = config.get("Generation", "Generate Enriched Gravel in the Nether", true).getBoolean(true);
         overworldGen = config.get("Generation", "Generate Enriched Gravel in the Overworld", true).getBoolean(true);
         twilightForestGen = config.get("Generation", "Generate Enriched Gravel in the Twilight Forest", true).getBoolean(true);
         mystGen = config.get("Generation", "Generate Enriched Gravel in MystCraft/Other Dimensions", true).getBoolean(true);
         diamondShards = config.get("Drops", "Drop Diamond Chips", true).getBoolean(true);
         emeraldShards = config.get("Drops", "Drop Emerald Chips", true).getBoolean(true);
         coal = config.get("Drops", "Drop Coal Chunks", true).getBoolean(true);
         iron = config.get("Drops", "Drop Iron Nuggets", true).getBoolean(true);
         gold = config.get("Drops", "Drop Gold Nuggets", true).getBoolean(true);
         lapis = config.get("Drops", "Drop Lapis Chunks", true).getBoolean(true);
         redstone = config.get("Drops", "Drop Redstone Chunks", true).getBoolean(true);
         netherMats = config.get("Drops", "Drop Nether-related Resources", true).getBoolean(true);
         cobaltArdite = config.get("Drops", "Drop Cobalt and Ardite Nuggets", true).getBoolean(true);
         copper = config.get("Drops", "Drop Copper Nuggets", true).getBoolean(true);
         tin = config.get("Drops", "Drop Tin Nuggets", true).getBoolean(true);
         silver = config.get("Drops", "Drop Silver Nuggets", true).getBoolean(true);
         lead = config.get("Drops", "Drop Lead Nuggets", true).getBoolean(true);
         aluminum = config.get("Drops", "Drop Aluminum Nuggets", true).getBoolean(true);
         titanium = config.get("Drops", "Drop Titanium Nuggets", true).getBoolean(true);
         zinc = config.get("Drops", "Drop Zinc Nuggets", true).getBoolean(true);
         nickel = config.get("Drops", "Drop Nickel Nuggets", true).getBoolean(true);
         platinum = config.get("Drops", "Drop Platinum Nuggets", true).getBoolean(true);
         dusts = config.get("Drops", "Drop Saltpeter and Sulfur", true).getBoolean(true);
         obsidian = config.get("Drops", "Drop Obsidian Chunks", true).getBoolean(true);
         gems = config.get("Drops", "Drop non-vanilla Gem Chips", true).getBoolean(true);
         forestry = config.get("Drops", "Drop Peat Chunks and Apatite Chips", true).getBoolean(true);
         amber = config.get("Drops", "Drop Amber", true).getBoolean(true);
         jade = config.get("Drops", "Drop Jade", true).getBoolean(true);
         mud = config.get("Drops", "Drop Mud", true).getBoolean(true);
         redrock = config.get("Drops", "Drop Redrock Chunks", true).getBoolean(true);
         crackedSand = config.get("Drops", "Drop Cracked Sand Piles", true).getBoolean(true);
         enableEnriched = config.get("Content", "Enable Enriched Gravel Blocks", true).getBoolean(true);
         enableSifterBlock = config.get("Content", "Enable Sifter Block", true).getBoolean(true);
         enableBuckets = config.get("Content", "Enable Buckets of Gravel", true).getBoolean(true);
         enableHandheldSifters = config.get("Content", "Enable Handheld Sifters", true).getBoolean(true);
         gunPowder = config.get("Crafting", "Enable crafting of Gunpowder", true).getBoolean(true);
         aluminumSifter = config.get("Crafting", "Enable aluminum nuggets in Sifter Block recipe", true).getBoolean(true);
         throwRocks = config.get("Content", "Enable Throwing Rock Chunks", true).getBoolean(true);
         config.save();
         new GravelDropAPI();
         new CustomSifterDropAPI();
      }

   }

   @Init
   public void init(FMLInitializationEvent event) {
      if(moduleEnabled) {
         if(enableEnriched) {
            gravelOre = (new OreGravel(gravelOreID)).setStepSound(Block.soundGravelFootstep);
            LanguageRegistry.addName(gravelOre, "Gravel");
            MinecraftForge.setBlockHarvestLevel(gravelOre, "shovel", 0);
            GameRegistry.registerBlock(gravelOre, GravelOreItem.class);
         }

         if(enableSifterBlock) {
            blockSifter = new BlockSifter(blockSifterID);
            LanguageRegistry.addName(blockSifter, "Sifter Block");
            MinecraftForge.setBlockHarvestLevel(blockSifter, "pickaxe", 1);
            GameRegistry.registerBlock(blockSifter);
         }

         if(ResourceMain.moduleEnabled) {
            nuggets = ResourceMain.nuggets;
         }

         if(enableHandheldSifters) {
            sifter = (new ItemSifter(sifterID, 20)).setUnlocalizedName("KBIgravelsifter");
            LanguageRegistry.addName(sifter, "Gravel Sifter");
            sifter2 = (new ItemSifter(sifter2ID, 64)).setUnlocalizedName("KBIsturdysifter");
            LanguageRegistry.addName(sifter2, "Sturdy Sifter");
         }

         if(enableBuckets) {
            bucket = (new BucketGravel(bucketID)).setContainerItem(Item.bucketEmpty);
            LanguageRegistry.addName(bucket, "Bucket of Gravel");
         }

         if(enableEnriched) {
            for(int re = 0; re < 16; ++re) {
               ItemStack oreStack = new ItemStack(gravelOre, 1, re);
               LanguageRegistry.addName(oreStack, oreNames[oreStack.getItemDamage()]);
            }

            GameRegistry.registerWorldGenerator(new GravelGenerator());
         }

         if(throwRocks) {
            EntityRegistry.registerModEntity(EntityRock.class, "Rock", 1, this, 80, 1, true);
            EntityRegistry.registerModEntity(EntityNetherrock.class, "Netherrock", 2, this, 80, 1, true);
            EntityRegistry.registerModEntity(EntityObsidian.class, "Obsidian", 3, this, 80, 1, true);
            EntityRegistry.registerModEntity(EntityRedRock.class, "RedRock", 4, this, 80, 1, true);
            LanguageRegistry.instance().addStringLocalization("entity.KBIgravelore.Rock.name", "Thrown Rock");
            LanguageRegistry.instance().addStringLocalization("entity.KBIgravelore.Netherrock.name", "Thrown Netherrock");
            LanguageRegistry.instance().addStringLocalization("entity.KBIgravelore.Obsidian.name", "Thrown Obsidian");
            LanguageRegistry.instance().addStringLocalization("entity.KBIgravelore.RedRock.name", "Thrown Red Rock");
         }

         MinecraftForge.EVENT_BUS.register(new EventHandler());
         proxy.initiate();
         doOreDictionary();
         doAddDrops();
      }

   }

   public static void doOreDictionary() {
      if(enableHandheldSifters) {
         GameRegistry.addRecipe(new ShapedOreRecipe(sifter, new Object[]{Boolean.valueOf(true), new Object[]{"STS", "STS", "STS", Character.valueOf('S'), "stickWood", Character.valueOf('T'), Item.silk}}));
         GameRegistry.addRecipe(new ShapedOreRecipe(sifter2, new Object[]{Boolean.valueOf(true), new Object[]{"ISI", "GSG", "ISI", Character.valueOf('S'), sifter, Character.valueOf('I'), "nuggetIron", Character.valueOf('G'), Item.goldNugget}}));
      }

      if(enableSifterBlock) {
         if(enableHandheldSifters) {
            if(aluminumSifter) {
               GameRegistry.addRecipe(new ShapedOreRecipe(blockSifter, new Object[]{Boolean.valueOf(true), new Object[]{"IAI", "ASA", "IAI", Character.valueOf('S'), sifter2, Character.valueOf('I'), Item.ingotIron, Character.valueOf('A'), "nuggetAluminum"}}));
               GameRegistry.addRecipe(new ShapedOreRecipe(blockSifter, new Object[]{Boolean.valueOf(true), new Object[]{"IAI", "ASA", "IAI", Character.valueOf('S'), sifter2, Character.valueOf('I'), Item.ingotIron, Character.valueOf('A'), "nuggetAluminium"}}));
            } else {
               GameRegistry.addRecipe(new ShapedOreRecipe(blockSifter, new Object[]{Boolean.valueOf(true), new Object[]{"IAI", "ASA", "IAI", Character.valueOf('S'), sifter2, Character.valueOf('I'), Item.ingotIron, Character.valueOf('A'), "nuggetIron"}}));
               if(!ResourceMain.moduleEnabled) {
                  GameRegistry.addRecipe(new ShapedOreRecipe(blockSifter, new Object[]{Boolean.valueOf(true), new Object[]{"IAI", "ASA", "IAI", Character.valueOf('S'), sifter2, Character.valueOf('I'), Item.ingotIron, Character.valueOf('A'), Item.goldNugget}}));
               }
            }
         } else if(aluminumSifter) {
            GameRegistry.addRecipe(new ShapedOreRecipe(blockSifter, new Object[]{Boolean.valueOf(true), new Object[]{"IAI", "ASA", "IAI", Character.valueOf('S'), Block.cloth, Character.valueOf('I'), Item.ingotIron, Character.valueOf('A'), "nuggetAluminum"}}));
            GameRegistry.addRecipe(new ShapedOreRecipe(blockSifter, new Object[]{Boolean.valueOf(true), new Object[]{"IAI", "ASA", "IAI", Character.valueOf('S'), Block.cloth, Character.valueOf('I'), Item.ingotIron, Character.valueOf('A'), "nuggetAluminium"}}));
         } else {
            GameRegistry.addRecipe(new ShapedOreRecipe(blockSifter, new Object[]{Boolean.valueOf(true), new Object[]{"IAI", "ASA", "IAI", Character.valueOf('S'), Block.cloth, Character.valueOf('I'), Item.ingotIron, Character.valueOf('A'), "nuggetIron"}}));
         }
      }

      if(gunPowder) {
         GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Item.gunpowder, 2, 0), new Object[]{Item.coal, "dustSmallSulfur", "dustSmallSulfur", "dustSmallSulfur", "dustSmallSulfur", "dustSmallSaltpeter", "dustSmallSaltpeter", "dustSmallSaltpeter", "dustSmallSaltpeter"}));
      }

   }

   public static void doAddDrops() {
      int qi;
      for(qi = 0; qi < 5; ++qi) {
         for(int itemClass = 0; itemClass < 9; ++itemClass) {
            CustomSifterDropAPI.addItemToList(Block.sand.blockID, 0, new ItemStack(nuggets, 1, 6));
         }

         CustomSifterDropAPI.addItemToList(Block.sand.blockID, 0, new ItemStack(Item.clay, 1, 0));
      }

      if(netherMats) {
         CustomSifterDropAPI.addItemToList(Block.sand.blockID, 0, new ItemStack(nuggets, 1, 7));
      }

      CustomSifterDropAPI.setConstant(Block.sand.blockID, 0, new ItemStack(nuggets, 3, 6));

      for(qi = 0; qi < 8; ++qi) {
         GravelDropAPI.addItemToList(qi, new ItemStack(Item.flint, 2, 0));
         GravelDropAPI.addItemToList(qi, new ItemStack(Item.clay, 1, 0));
         GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 4));
         GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 5));
         GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 6));
      }

      for(qi = 1; qi < 5; ++qi) {
         if(redstone) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 1));
         }

         if(forestry && ResourceMain.apatite) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 21));
         }

         if(zinc && ResourceMain.zinc) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 25));
         }

         if(coal) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 26));
         }

         if(redrock && ResourceMain.redrock) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 31));
         }

         if(mud) {
            boolean var6 = false;
            String e = "karuberu.mods.mudmod.MudMod";

            try {
               Object e1 = Class.forName(e).getField("mudBlob").get((Object)null);
               if((Item)e1 != null) {
                  GravelDropAPI.addItemToList(qi, new ItemStack((Item)e1, 1, 0));
                  FMLLog.info("[KBI] Found Karuberu\'s Mud Mod; adding Mud to Enriched Gravel drops", new Object[0]);
                  var6 = true;
               } else {
                  FMLLog.info("[KBI] Found Karuberu\'s Mud Mod, but could not add Mud to Enriched Gravel drops", new Object[0]);
                  FMLLog.info("[KBI] Adding own mud", new Object[0]);
                  GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 35));
               }
            } catch (Exception var4) {
               FMLLog.warning("[KBI] Could not find Karuberu\'s Mud Mod", new Object[0]);
               FMLLog.info("[KBI] Adding own mud", new Object[0]);
               GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 35));
            }
         }
      }

      for(qi = 2; qi < 6; ++qi) {
         if(lapis) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 2));
         }

         if(iron) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 0));
         }

         if(netherMats) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 7));
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 8));
         }

         if(copper && ResourceMain.copper) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 11));
         }

         if(tin && ResourceMain.tin) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 12));
         }

         if(aluminum && ResourceMain.aluminum) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 15));
         }

         if(dusts) {
            if(ResourceMain.saltpeter) {
               GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 24));
            }

            if(ResourceMain.sulfur) {
               GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 23));
            }
         }

         if(forestry && ResourceMain.peat) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 30));
         }

         if(crackedSand && ResourceMain.crackedSand_config) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 36));
         }
      }

      for(qi = 3; qi < 7; ++qi) {
         if(gold) {
            GravelDropAPI.addItemToList(qi, new ItemStack(Item.goldNugget, 1, 0));
         }

         if(silver && ResourceMain.silver) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 13));
         }

         if(lead && ResourceMain.lead) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 14));
         }

         if(nickel && ResourceMain.nickel) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 16));
         }

         if(netherMats) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 32));
         }

         if(jade && ResourceMain.jade) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 34));
         }
      }

      for(qi = 5; qi < 8; ++qi) {
         if(gems) {
            if(ResourceMain.ruby) {
               GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 18));
            }

            if(ResourceMain.sapphire) {
               GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 19));
            }

            if(ResourceMain.greenSapphire) {
               GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 20));
            }
         }

         if(obsidian) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 27));
         }

         if(amber) {
            String var8 = "thaumcraft.api.ItemApi";

            try {
               Object var7 = Class.forName(var8).getMethod("getItem", new Class[]{String.class, Integer.TYPE}).invoke((Object)null, new Object[]{"itemResource", Integer.valueOf(6)});
               if((ItemStack)var7 != null) {
                  GravelDropAPI.addItemToList(qi, (ItemStack)var7);
                  FMLLog.info("[KBI] Found ThaumCraft 3 API; adding TC3 Amber to Enriched Gravel drops", new Object[0]);
               } else {
                  FMLLog.info("[KBI] Found ThaumCraft 3 API, but could not add TC3 Amber to Enriched Gravel drops", new Object[0]);
                  if(ResourceMain.amber) {
                     GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 33));
                  }
               }
            } catch (Exception var5) {
               FMLLog.warning("[KBI] Could not find ThaumCraft 3 API; not adding TC3 Amber to Enriched Gravel drops", new Object[0]);
               if(ResourceMain.amber) {
                  GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 33));
               }
            }
         }
      }

      for(qi = 6; qi < 8; ++qi) {
         if(emeraldShards) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 10));
         }

         if(platinum && ResourceMain.platinum) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 17));
         }

         if(titanium && ResourceMain.titanium) {
            GravelDropAPI.addItemToList(qi, new ItemStack(nuggets, 1, 22));
         }
      }

      if(diamondShards) {
         GravelDropAPI.addItemToList(7, new ItemStack(nuggets, 1, 9));
      }

      if(cobaltArdite) {
         if(ResourceMain.cobalt) {
            GravelDropAPI.addItemToList(7, new ItemStack(nuggets, 1, 28));
         }

         if(ResourceMain.ardite) {
            GravelDropAPI.addItemToList(7, new ItemStack(nuggets, 1, 29));
         }
      }

   }

}
