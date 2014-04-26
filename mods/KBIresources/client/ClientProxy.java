package mods.KBIresources.client;

import java.io.File;
import mods.KBIresources.common.CommonProxy;
import net.minecraft.client.Minecraft;

public class ClientProxy extends CommonProxy {

   public File getMinecraftDir() {
      return Minecraft.getMinecraft().mcDataDir;
   }
}
