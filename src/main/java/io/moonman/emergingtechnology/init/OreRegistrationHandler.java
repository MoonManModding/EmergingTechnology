package io.moonman.emergingtechnology.init;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Registers OreDicts for Emerging Technology
 */
public class OreRegistrationHandler {

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
            ModBlocks.cooker,
            ModBlocks.bioreactor,
            ModBlocks.scaffolder,
            ModBlocks.fabricator
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

        Item[] filamentPlastic = new Item[] {
            ModItems.filament
        };

        Item[] biomass = new Item[] {
            ModItems.biomass
        };

        Item[] pulpWood = new Item[] {
            ModItems.paperpulp
        };

        Item[] dustBiomass = new Item[] {
            ModItems.biomass
        };

        Item[] coal = new Item[] {
            ModItems.biochar
        };

        Item[] blockCoal = new Item[] {
            ModItems.biocharblock
        };

        Item[] leather = new Item[] {
            ModItems.syntheticleather
        };

        Item[] string = new Item[] {
            ModItems.syntheticsilk
        };

        Item[] slimeball = new Item[] {
            ModItems.syntheticslime
        };
        
        Item[] fertilizer = new Item[] {
            ModItems.fertilizer
        };

        Item[] listAllmeat = new Item[] {
            ModItems.syntheticchickencooked,
            ModItems.syntheticcowcooked,
            ModItems.syntheticpigcooked
        };

        Item[] cookedMeat = new Item[] {
            ModItems.syntheticchickencooked,
            ModItems.syntheticcowcooked,
            ModItems.syntheticpigcooked
        };

        Item[] rawMeat = new Item[] {
            ModItems.syntheticchickenraw,
            ModItems.syntheticcowraw,
            ModItems.syntheticpigraw
        };

        Block[] blockWool = new Block[] {
            Blocks.WOOL
        };

        registerBlocks(machinePlastic, "machinePlastic");
        registerBlocks(blockPlastic, "blockPlastic");
        registerBlocks(glass, "glass");
        registerBlocks(blockGlass, "blockGlass");
        registerBlocks(blockWool, "blockWool");

        registerItems(sheetPlastic, "sheetPlastic");
        registerItems(platePlastic, "platePlastic");
        registerItems(itemPlastic, "itemPlastic");
        registerItems(stickPlastic, "stickPlastic");
        registerItems(stickPlastic, "rodPlastic");
        registerItems(bioplastic, "bioplastic");

        registerItems(starch, "starch");
        registerItems(pulpWood, "pulpWood");
        registerItems(filamentPlastic, "filamentPlastic");
        registerItems(biomass, "biomass");
        registerItems(coal, "coal");
        registerItems(blockCoal, "blockCoal");
        registerItems(dustBiomass, "dustBiomass");
        registerItems(slimeball, "slimeball");
        registerItems(string, "string");
        registerItems(leather, "leather");
        registerItems(fertilizer, "fertilizer");

        registerItems(listAllmeat, "listAllmeat");
        registerItems(rawMeat, "rawMeat");
        registerItems(cookedMeat, "cookedMeat");
        registerItems(rawMeat, "listAllmeatraw");
        registerItems(cookedMeat, "listAllmeatcooked");

        

        OreDictionary.registerOre("listAllchickenraw", ModItems.syntheticchickenraw);
        OreDictionary.registerOre("listAllporkraw", ModItems.syntheticpigraw);
        OreDictionary.registerOre("listAllbeefraw", ModItems.syntheticcowraw);

        OreDictionary.registerOre("listAllchickencooked", ModItems.syntheticchickencooked);
        OreDictionary.registerOre("listAllporkcooked", ModItems.syntheticpigcooked);
        OreDictionary.registerOre("listAllbeefcooked", ModItems.syntheticcowcooked);
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