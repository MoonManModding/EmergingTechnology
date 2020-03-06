package io.moonman.emergingtechnology.init;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.blocks.BiocharBlock;
import io.moonman.emergingtechnology.blocks.ClearPlasticBlock;
import io.moonman.emergingtechnology.blocks.MachineCase;
import io.moonman.emergingtechnology.blocks.PlasticBlock;
import io.moonman.emergingtechnology.machines.filler.Filler;
import io.moonman.emergingtechnology.machines.solar.Solar;
import io.moonman.emergingtechnology.machines.solarglass.SolarGlass;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    // Machines
    @ObjectHolder(EmergingTechnology.MODID + ":" + Solar.name)
    public static Solar SOLAR;

    @ObjectHolder(EmergingTechnology.MODID + ":" + SolarGlass.name)
    public static SolarGlass SOLARGLASS;

    @ObjectHolder(EmergingTechnology.MODID + ":" + Filler.name)
    public static Filler FILLER;

    // Blocks

    @ObjectHolder(EmergingTechnology.MODID + ":" + PlasticBlock.name)
    public static PlasticBlock PLASTICBLOCK;

    @ObjectHolder(EmergingTechnology.MODID + ":" + ClearPlasticBlock.name)
    public static ClearPlasticBlock CLEARPLASTICBLOCK;

    @ObjectHolder(EmergingTechnology.MODID + ":" + BiocharBlock.name)
    public static BiocharBlock BIOCHARBLOCK;

    @ObjectHolder(EmergingTechnology.MODID + ":" + MachineCase.name)
    public static MachineCase MACHINECASE;

    // Registration

    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
        registerBlocks(event, new PlasticBlock(), new BiocharBlock(), new MachineCase(), new ClearPlasticBlock(),
                new Solar(), new SolarGlass(), new Filler());
    }

    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
        registerBlockItems(event, createBlockItem(PLASTICBLOCK, PlasticBlock.name),
                createBlockItem(BIOCHARBLOCK, BiocharBlock.name), createBlockItem(MACHINECASE, MachineCase.name),
                createBlockItem(SOLAR, Solar.name), createBlockItem(SOLARGLASS, SolarGlass.name),
                createBlockItem(FILLER, Filler.name), createBlockItem(CLEARPLASTICBLOCK, ClearPlasticBlock.name));
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