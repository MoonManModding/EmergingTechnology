package io.moonman.emergingtechnology.init;

import java.util.ArrayList;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.item.synthetics.SampleItemBase;
import io.moonman.emergingtechnology.item.synthetics.SyringeItemBase;
import io.moonman.emergingtechnology.machines.battery.BatteryTileEntity;
import io.moonman.emergingtechnology.machines.biomass.BiomassGeneratorTileEntity;
import io.moonman.emergingtechnology.machines.bioreactor.BioreactorTileEntity;
import io.moonman.emergingtechnology.machines.collector.CollectorTileEntity;
import io.moonman.emergingtechnology.machines.cooker.CookerTileEntity;
import io.moonman.emergingtechnology.machines.fabricator.FabricatorTileEntity;
import io.moonman.emergingtechnology.machines.filler.FillerTileEntity;
import io.moonman.emergingtechnology.machines.harvester.HarvesterTileEntity;
import io.moonman.emergingtechnology.machines.hydroponic.HydroponicTESR;
import io.moonman.emergingtechnology.machines.hydroponic.HydroponicTileEntity;
import io.moonman.emergingtechnology.machines.light.LightTileEntity;
import io.moonman.emergingtechnology.machines.piezoelectric.PiezoelectricTileEntity;
import io.moonman.emergingtechnology.machines.processor.ProcessorTileEntity;
import io.moonman.emergingtechnology.machines.scaffolder.ScaffolderTileEntity;
import io.moonman.emergingtechnology.machines.scrubber.ScrubberTileEntity;
import io.moonman.emergingtechnology.machines.shredder.ShredderTileEntity;
import io.moonman.emergingtechnology.machines.solar.SolarTileEntity;
import io.moonman.emergingtechnology.machines.tidal.TidalGeneratorTileEntity;
import io.moonman.emergingtechnology.machines.wind.WindTileEntity;
import io.moonman.emergingtechnology.providers.ModTissueProvider;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.animation.AnimationTESR;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Registers blocks, items and models for Emerging Technology
 */
public class RegistrationHandler {

    public static void registerBlocks(Register<Block> event) {

        final Block[] blocks = ModBlocks.generateBlocks();

        registerTileEntity(HydroponicTileEntity.class, "hydroponic");
        registerTileEntity(HarvesterTileEntity.class, "harvester");
        registerTileEntity(FillerTileEntity.class, "filler");
        registerTileEntity(ScrubberTileEntity.class, "scrubber");
        registerTileEntity(LightTileEntity.class, "light");
        registerTileEntity(ProcessorTileEntity.class, "processor");
        registerTileEntity(ShredderTileEntity.class, "shredder");
        registerTileEntity(FabricatorTileEntity.class, "fabricator");
        registerTileEntity(CollectorTileEntity.class, "collector");
        registerTileEntity(CookerTileEntity.class, "cooker");
        registerTileEntity(PiezoelectricTileEntity.class, "piezoelectric");
        registerTileEntity(BioreactorTileEntity.class, "bioreactor");
        registerTileEntity(ScaffolderTileEntity.class, "scaffolder");
        registerTileEntity(TidalGeneratorTileEntity.class, "tidalgenerator");
        registerTileEntity(BiomassGeneratorTileEntity.class, "biomassgenerator");
        registerTileEntity(SolarTileEntity.class, "solar");
        registerTileEntity(WindTileEntity.class, "wind");
        registerTileEntity(BatteryTileEntity.class, "battery");

        event.getRegistry().registerAll(blocks);
    }

    public static void registerItems(Register<Item> event) {

        final Item[] items = ModItems.generateItems();
        final Item[] itemBlocks = generateItemBlocks(ModBlocks.getBlocks());

        for (Item item : ModTissueProvider.modSamples) {
            event.getRegistry().register(item);
        }

        for (Item item : ModTissueProvider.modSyringes) {
            event.getRegistry().register(item);
        }

        // Register
        event.getRegistry().registerAll(itemBlocks);
        event.getRegistry().registerAll(items);
    }

    public static void registerFluids() {
        FluidRegistry.registerFluid(ModFluids.CARBON_DIOXIDE);
        FluidRegistry.addBucketForFluid(ModFluids.CARBON_DIOXIDE);
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {

        for (Block block : ModBlocks.getBlocks()) {
            registerModel(Item.getItemFromBlock(block));
        }

        for (Item item : ModItems.getItems()) {
            registerModel(item);
        }

        registerModTissueModels();

        // Hydroponic TESR
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.hydroponic), 0,
                new ModelResourceLocation(ModBlocks.hydroponic.getRegistryName(), "inventory"));

        ClientRegistry.bindTileEntitySpecialRenderer(HydroponicTileEntity.class, new HydroponicTESR());

        ClientRegistry.bindTileEntitySpecialRenderer(TidalGeneratorTileEntity.class,
                new AnimationTESR<TidalGeneratorTileEntity>());

        ClientRegistry.bindTileEntitySpecialRenderer(WindTileEntity.class, new AnimationTESR<WindTileEntity>());
        ClientRegistry.bindTileEntitySpecialRenderer(ScrubberTileEntity.class, new AnimationTESR<ScrubberTileEntity>());

        RenderHandler.registerMeshesAndStatesForBlock(ModBlocks.carbondioxideblock);
    }

    private static Item[] generateItemBlocks(Block[] blocks) {
        ArrayList<Item> itemBlocks = new ArrayList<Item>();

        for (Block block : blocks) {
            itemBlocks.add(generateItemBlock(block));
        }

        return itemBlocks.toArray(new Item[0]);
    }

    private static Item generateItemBlock(Block block) {
        return new ItemBlock(block).setRegistryName(block.getRegistryName());
    }

    private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String name) {
        GameRegistry.registerTileEntity(tileEntityClass, getResourceLocation(name));
    }

    private static void registerModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    private static void registerModTissueModels() {

        for (SyringeItemBase item : ModTissueProvider.modSyringes) {
            ModelLoader.setCustomModelResourceLocation(item, 0,
                    new ModelResourceLocation(EmergingTechnology.MODID + ":fullsyringe", "inventory"));
        }

        for (SampleItemBase item : ModTissueProvider.modSamples) {
            ModelLoader.setCustomModelResourceLocation(item, 0,
                    new ModelResourceLocation(EmergingTechnology.MODID + ":sample", "inventory"));
        }
    }

    private static ResourceLocation getResourceLocation(String location) {
        return new ResourceLocation(EmergingTechnology.MODID + ":" + location);
    }
}