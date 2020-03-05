package io.moonman.emergingtechnology.init;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.blocks.BiocharBlock;
import io.moonman.emergingtechnology.blocks.ClearPlasticBlock;
import io.moonman.emergingtechnology.blocks.PlasticBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    // Blocks

    @ObjectHolder(EmergingTechnology.MODID + ":" + PlasticBlock.name)
    public static PlasticBlock PLASTICBLOCK;

    @ObjectHolder(EmergingTechnology.MODID + ":" + ClearPlasticBlock.name)
    public static ClearPlasticBlock CLEARPLASTICBLOCK;

    @ObjectHolder(EmergingTechnology.MODID + ":" + BiocharBlock.name)
    public static BiocharBlock BIOCHARBLOCK;

    // Registration

    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
        registerBlocks(event, new PlasticBlock(), new BiocharBlock(), new ClearPlasticBlock());
    }

    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
        registerBlockItems(event, createBlockItem(PLASTICBLOCK, PlasticBlock.name),
                createBlockItem(BIOCHARBLOCK, BiocharBlock.name),
                createBlockItem(CLEARPLASTICBLOCK, ClearPlasticBlock.name));
    }

    // Helpers

    private static Item createBlockItem(Block block, String name) {
        return new BlockItem(block, new Item.Properties().group(EmergingTechnology.ITEMGROUP)).setRegistryName(name);
    }

    private static void registerBlocks(final RegistryEvent.Register<Block> event, Block... blocks) {
        for (Block block : blocks) {
            event.getRegistry().register(block);
        }
    }

    private static void registerBlockItems(final RegistryEvent.Register<Item> event, Item... blockItems) {
        for (Item block : blockItems) {
            event.getRegistry().register(block);
        }
    }

}