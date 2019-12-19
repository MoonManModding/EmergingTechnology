package io.moonman.emergingtechnology.init;

import io.moonman.emergingtechnology.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Registers OreDicts for Emerging Technology
 */
public class OreRegistrationHandler {

    public static int[] PLASTIC_ORE_IDS;

    public static void init() {
        registerItems();
    }

    public static void registerItems() {

        Block[] machinePlastic = new Block[] {
            ModBlocks.hydroponic,
            ModBlocks.light,
            ModBlocks.shredder,
            ModBlocks.processor,
            ModBlocks.machinecase,
            ModBlocks.cooker
        };

        Block[] blockPlastic = new Block[] {
            ModBlocks.plasticblock
        };

        Block[] blockGlass = new Block[] {
            ModBlocks.clearplasticblock
        };

        Block[] glass = new Block[] {
            ModBlocks.clearplasticblock
        };

        Item[] sheetPlastic = new Item[] {
            ModItems.plasticsheet
        };

        Item[] platePlastic = new Item[] {
            ModItems.plasticsheet
        };

        Item[] dustPlastic = new Item[] {
            ModItems.shreddedplastic,
            ModItems.shreddedstarch
        };

        Item[] orePlastic = new Item[] {
            ModItems.shreddedplastic,
            ModItems.shreddedstarch
        };

        Item[] itemPlastic = new Item[] {
            
        };

        Item[] stickPlastic = new Item[] {
            ModItems.plasticrod
        };

        Item[] bioplastic = new Item[] {
            ModItems.plasticsheet
        };

        Item[] starch = new Item[] {
            ModItems.shreddedstarch
        };

        Item[] filament = new Item[] {
            ModItems.filament
        };

        registerBlocks(machinePlastic, "machinePlastic");
        registerBlocks(blockPlastic, "blockPlastic");
        registerBlocks(glass, "glass");
        registerBlocks(blockGlass, "blockGlass");
        registerItems(sheetPlastic, "sheetPlastic");
        registerItems(platePlastic, "platePlastic");
        registerItems(dustPlastic, "dustPlastic");
        registerItems(orePlastic, "orePlastic");
        registerItems(itemPlastic, "itemPlastic");
        registerItems(stickPlastic, "stickPlastic");
        registerItems(bioplastic, "bioplastic");
        registerItems(starch, "starch");
        registerItems(filament, "filament");
    }

    private static void registerBlocks(Block[] blocks, String name) {
        for (Block block : blocks) {
            OreDictionary.registerOre(name, block);
        }
    }

    private static void registerItems(Item[] items, String name) {
        for (Item item : items) {
            OreDictionary.registerOre(name, item);
        }
    }
}