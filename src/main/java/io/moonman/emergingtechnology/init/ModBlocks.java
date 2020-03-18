package io.moonman.emergingtechnology.init;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.blocks.BiocharBlock;
import io.moonman.emergingtechnology.blocks.ClearPlasticBlock;
import io.moonman.emergingtechnology.blocks.MachineCase;
import io.moonman.emergingtechnology.blocks.PlasticBlock;
import io.moonman.emergingtechnology.machines.filler.Filler;
import io.moonman.emergingtechnology.machines.hydroponic.Hydroponic;
import io.moonman.emergingtechnology.machines.solar.Solar;
import io.moonman.emergingtechnology.machines.solarglass.SolarGlass;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    // Machines
    @ObjectHolder(Solar.registryName)
    public static Solar SOLAR;

    @ObjectHolder(SolarGlass.registryName)
    public static SolarGlass SOLARGLASS;

    @ObjectHolder(Filler.registryName)
    public static Filler FILLER;

    @ObjectHolder(Hydroponic.registryName)
    public static Hydroponic HYDROPONIC;

    // Blocks

    @ObjectHolder(PlasticBlock.registryName)
    public static PlasticBlock PLASTICBLOCK;

    @ObjectHolder(ClearPlasticBlock.registryName)
    public static ClearPlasticBlock CLEARPLASTICBLOCK;

    @ObjectHolder(BiocharBlock.registryName)
    public static BiocharBlock BIOCHARBLOCK;

    @ObjectHolder(MachineCase.registryName)
    public static MachineCase MACHINECASE;

    // Registration

    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
        registerBlocks(event, new PlasticBlock(), new BiocharBlock(), new MachineCase(), new ClearPlasticBlock(),
                new Solar(), new SolarGlass(), new Filler(), new Hydroponic());
    }

    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
        registerBlockItems(event, createBlockItem(PLASTICBLOCK, PlasticBlock.name),
                createBlockItem(BIOCHARBLOCK, BiocharBlock.name), createBlockItem(MACHINECASE, MachineCase.name),
                createBlockItem(SOLAR, Solar.name), createBlockItem(SOLARGLASS, SolarGlass.name),
                createBlockItem(FILLER, Filler.name), createBlockItem(HYDROPONIC, Hydroponic.name),
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