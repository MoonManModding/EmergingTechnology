package io.moonman.emergingtechnology.proxy;

import java.io.File;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.helpers.custom.loaders.CustomGrowthMediumLoader;
import io.moonman.emergingtechnology.init.RegistrationHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = EmergingTechnology.MODID)
public abstract class CommonProxy {

    public static Configuration config;

    @SubscribeEvent
    public static void registerItems(Register<Item> event) {
        RegistrationHandler.registerItems(event);
    }

    @SubscribeEvent
    public static void registerBlocks(Register<Block> event) {
        RegistrationHandler.registerBlocks(event);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        RegistrationHandler.registerModels(event);
    }

    @SubscribeEvent
    public static void registerSoundEvents(Register<SoundEvent> event) {
    }

    @SubscribeEvent
    public static void playSound(PlaySoundEvent event) {

    }

    public void preInit(FMLPreInitializationEvent e) {
        String customGrowthMediaFilePath = e.getModConfigurationDirectory().getAbsolutePath() + "\\" + EmergingTechnology.MODID + "\\test.json";
        CustomGrowthMediumLoader.loadCustomGrowthMedia(customGrowthMediaFilePath);
    }

    public void init() {

    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    abstract public boolean playerIsInCreativeMode(EntityPlayer player);

    /**
     * is this a dedicated server?
     * 
     * @return true if this is a dedicated server, false otherwise
     */
    abstract public boolean isDedicatedServer();
}