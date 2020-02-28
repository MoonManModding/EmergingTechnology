package io.moonman.emergingtechnology.proxy;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.animation.AnimationHelper;
import io.moonman.emergingtechnology.providers.ModBulbProvider;
import io.moonman.emergingtechnology.providers.ModFluidProvider;
import io.moonman.emergingtechnology.providers.ModMediumProvider;
import io.moonman.emergingtechnology.providers.ModTissueProvider;
import io.moonman.emergingtechnology.proxy.interop.ModLoader;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.worldgen.OreGenerator;
import io.moonman.emergingtechnology.worldgen.WorldTickHandler;
import io.moonman.emergingtechnology.init.OreRegistrationHandler;
import io.moonman.emergingtechnology.init.RegistrationHandler;
import io.moonman.emergingtechnology.integration.crafttweaker.CraftTweakerHelper;
import io.moonman.emergingtechnology.network.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryModifiable;

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
    public static void registerRecipes(Register<IRecipe> event) {
        IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
        RecipeBuilder.removeRecipes(registry);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        RegistrationHandler.registerModels(event);
    }

    public void preInit(FMLPreInitializationEvent e) {
        RegistrationHandler.registerFluids();

        EmergingTechnologyConfig.preInit();
        ModLoader.preInit();

        GameRegistry.registerWorldGenerator(OreGenerator.instance, 10);
        MinecraftForge.EVENT_BUS.register(OreGenerator.instance);

        ModBulbProvider.preInit(e);
        ModMediumProvider.preInit(e);
        ModFluidProvider.preInit(e);
        ModTissueProvider.preInit(e);
        RecipeProvider.preInit(e);

        CraftTweakerHelper.preInit();

        PacketHandler.registerMessages(EmergingTechnology.MODID);
    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(WorldTickHandler.instance);
        OreRegistrationHandler.init();
        RecipeBuilder.buildMachineRecipes();
    }

    public void postInit(FMLPostInitializationEvent e) {
        
    }

	public World getWorld(MessageContext ctx) {
		return ctx.getServerHandler().player.world;
    }
    
    abstract public boolean playerIsInCreativeMode(EntityPlayer player);

    /**
     * is this a dedicated server?
     * 
     * @return true if this is a dedicated server, false otherwise
     */
    abstract public boolean isDedicatedServer();
}