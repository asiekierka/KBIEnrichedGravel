package mods.KBIgravelore.client;

import cpw.mods.fml.common.FMLCommonHandler;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import mods.KBIgravelore.common.GravelMain;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class RegisterGravelSounds {

   @ForgeSubscribe
   public void loadingSounds(SoundLoadEvent event) {
      String[] soundFiles = new String[]{"siftGravel"};

      for(int i = 0; i < soundFiles.length; ++i) {
         try {
            event.manager.soundPoolSounds.addSound(soundFiles[i]);
            FMLCommonHandler.instance().getFMLLogger().log(Level.INFO, "[KBI] Loaded sound " + soundFiles[i]);
         } catch (Exception var5) {
            FMLCommonHandler.instance().getFMLLogger().log(Level.SEVERE, "[KBI] Could not load sound " + soundFiles[i], var5.toString());
         }
      }
   }
}
