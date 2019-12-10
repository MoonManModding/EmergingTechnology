package io.moonman.emergingtechnology.init;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.block.blocks.Frame;
import io.moonman.emergingtechnology.item.items.BlueBulb;
import io.moonman.emergingtechnology.item.items.GreenBulb;
import io.moonman.emergingtechnology.item.items.PurpleBulb;
import io.moonman.emergingtechnology.item.items.RedBulb;
import io.moonman.emergingtechnology.machines.hydroponic.Hydroponic;
import io.moonman.emergingtechnology.machines.hydroponic.HydroponicTESR;
import io.moonman.emergingtechnology.machines.hydroponic.HydroponicTileEntity;
import io.moonman.emergingtechnology.machines.light.Light;
import io.moonman.emergingtechnology.machines.light.LightTileEntity;
import io.moonman.emergingtechnology.machines.processor.Processor;
import io.moonman.emergingtechnology.machines.processor.ProcessorTileEntity;
import io.moonman.emergingtechnology.machines.shredder.Shredder;
import io.moonman.emergingtechnology.machines.shredder.ShredderTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
Registers blocks, items and models for Emerging Technology
*/
public class RegistrationHandler {

    public static void registerBlocks(Register<Block> event) {
        final Block[] blocks = { new Hydroponic(), new Light(), new Frame(), new Processor(), new Shredder() };

        ResourceLocation hydroponicLocation = new ResourceLocation(EmergingTechnology.MODID + ":hydroponic");
        ResourceLocation lightLocation = new ResourceLocation(EmergingTechnology.MODID + ":light");
        ResourceLocation processorLocation = new ResourceLocation(EmergingTechnology.MODID + ":processor");
        ResourceLocation shredderLocation = new ResourceLocation(EmergingTechnology.MODID + ":shredder");

        GameRegistry.registerTileEntity(HydroponicTileEntity.class, hydroponicLocation);
        GameRegistry.registerTileEntity(LightTileEntity.class, lightLocation);
        GameRegistry.registerTileEntity(ProcessorTileEntity.class, processorLocation);
        GameRegistry.registerTileEntity(ShredderTileEntity.class, shredderLocation);

        event.getRegistry().registerAll(blocks);
    }

    public static void registerItems(Register<Item> event) {

        // Items
        final Item[] items = { new RedBulb(), new GreenBulb(), new BlueBulb(), new PurpleBulb() };

        // Items from Blocks
        Item hydroponic = generateItemBlock(ModBlocks.hydroponic);
        Item light = generateItemBlock(ModBlocks.light);
        Item frame = generateItemBlock(ModBlocks.frame);
        Item processor = generateItemBlock(ModBlocks.processor);
        Item shredder = generateItemBlock(ModBlocks.shredder);

        final Item[] itemBlocks = { hydroponic, light, frame, processor, shredder };

        // Register
        event.getRegistry().registerAll(items);
        event.getRegistry().registerAll(itemBlocks);
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {

        // Item Models
        registerModel(ModItems.redbulb, 0);
        registerModel(ModItems.greenbulb, 0);
        registerModel(ModItems.bluebulb, 0);
        registerModel(ModItems.purplebulb, 0);

        // Block Models
        registerModel(Item.getItemFromBlock(ModBlocks.hydroponic), 0);
        registerModel(Item.getItemFromBlock(ModBlocks.light), 0);
        registerModel(Item.getItemFromBlock(ModBlocks.frame), 0);
        registerModel(Item.getItemFromBlock(ModBlocks.processor), 0);
        registerModel(Item.getItemFromBlock(ModBlocks.shredder), 0);

        // Hydroponic TESR
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.hydroponic), 0, new ModelResourceLocation(ModBlocks.hydroponic.getRegistryName(), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(HydroponicTileEntity.class, new HydroponicTESR());
    }

    private static Item generateItemBlock(Block block) {
        return new ItemBlock(block).setRegistryName(block.getRegistryName());
    }

    private static void registerModel(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}