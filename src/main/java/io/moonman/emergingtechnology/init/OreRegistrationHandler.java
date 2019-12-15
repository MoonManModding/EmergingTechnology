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
            //ModBlocks.fabricator
        };

        Block[] blockPlastic = new Block[] {
            ModBlocks.plasticblock
        };

        Item[] sheetPlastic = new Item[] {
            ModItems.plasticsheet
        };

        Item[] dustPlastic = new Item[] {
            ModItems.shreddedplastic,
            ModItems.shreddedplant
        };

        Item[] itemPlastic = new Item[] {
            ModItems.shreddedplastic,
            ModItems.shreddedplant
        };

        Item[] stickPlastic = new Item[] {
            ModItems.plasticrod
        };

        Item[] rodPlastic = new Item[] {
            ModItems.plasticrod
        };

        registerBlocks(machinePlastic, "machinePlastic");
        registerBlocks(blockPlastic, "blockPlastic");

        registerItems(sheetPlastic, "sheetPlastic");
        registerItems(dustPlastic, "dustPlastic");
        registerItems(itemPlastic, "itemPlastic");
        registerItems(stickPlastic, "stickPlastic");
        registerItems(rodPlastic, "rodPlastic");
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