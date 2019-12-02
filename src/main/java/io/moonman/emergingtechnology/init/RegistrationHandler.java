package io.moonman.emergingtechnology.init;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.block.blocks.Hydroponic;

import io.moonman.emergingtechnology.tile.tiles.HydroponicTileEntity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegistrationHandler {

    public static void registerBlocks(Register<Block> event) {
        final Block[] blocks = { new Hydroponic() };


        ResourceLocation hydroponicLocation = new ResourceLocation(EmergingTechnology.MODID + ":hydroponic");

        GameRegistry.registerTileEntity(HydroponicTileEntity.class, hydroponicLocation);

        event.getRegistry().registerAll(blocks);
    }

    public static void registerItems(Register<Item> event) {
        Item hydroponic = generateItemBlock(ModBlocks.hydroponic);

        final Item[] itemBlocks = { hydroponic };

        event.getRegistry().registerAll(itemBlocks);
    }

    public static void registerModels(ModelRegistryEvent event) {
       
        registerModel(Item.getItemFromBlock(ModBlocks.hydroponic), 0);
    }

    private static Item generateItemBlock(Block block) {
        System.out.println("EmergingTechnology - Registering " + block.toString());
        return new ItemBlock(block).setRegistryName(block.getRegistryName());
    }

    private static void registerModel(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}