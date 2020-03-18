package io.moonman.emergingtechnology;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.moonman.emergingtechnology.config.Config;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.ModItemGroup;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.init.ModTiles;
import io.moonman.emergingtechnology.proxy.ClientProxy;
import io.moonman.emergingtechnology.proxy.IProxy;
import io.moonman.emergingtechnology.proxy.ServerProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(EmergingTechnology.MODID)
public class EmergingTechnology {

    public static final String MODID = "emergingtechnology";
    public static final String MODID_REG = MODID + ":";
    public static final ItemGroup ITEMGROUP = new ModItemGroup(MODID);

    public static final Logger LOGGER = LogManager.getLogger();

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public EmergingTechnology() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);

        MinecraftForge.EVENT_BUS.register(this);

        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID + "-common.toml"));
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void setupClient(final FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        
    }

   
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            ModBlocks.onBlocksRegistry(event);
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            ModBlocks.onItemsRegistry(event);
            ModItems.onItemsRegistry(event);
        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
            ModTiles.onTileEntityRegistry(event);
        }
    }
}
