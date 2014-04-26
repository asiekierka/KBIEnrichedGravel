package mods.KBIgravelore.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import java.io.File;
import mods.KBIgravelore.common.CommonProxy;
import mods.KBIgravelore.common.EntityNetherrock;
import mods.KBIgravelore.common.EntityObsidian;
import mods.KBIgravelore.common.EntityRedRock;
import mods.KBIgravelore.common.EntityRock;
import mods.KBIgravelore.common.GravelMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;

public class ClientProxy extends CommonProxy {

   public void initiate() {
      RenderingRegistry.registerEntityRenderingHandler(EntityRock.class, new RenderSnowball(GravelMain.nuggets, 4));
      RenderingRegistry.registerEntityRenderingHandler(EntityNetherrock.class, new RenderSnowball(GravelMain.nuggets, 7));
      RenderingRegistry.registerEntityRenderingHandler(EntityObsidian.class, new RenderSnowball(GravelMain.nuggets, 27));
      RenderingRegistry.registerEntityRenderingHandler(EntityRedRock.class, new RenderSnowball(GravelMain.nuggets, 31));
   }

   public File getMinecraftDir() {
      return Minecraft.getMinecraft().mcDataDir;
   }
}
