package io.moonman.emergingtechnology.helpers.custom.loaders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.init.ModBlocks;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Loads useful Ore Dictionary lists for Emerging Technology
 */
public class OreDictionaryLoader {

    public static int[] PLASTIC_ORE_IDS;

    public static void init() {
        PLASTIC_ORE_IDS = getValidPlasticOreDictIds();
    }

    public static void registerItems() {
        OreDictionary.registerOre("machinePlastic", ModBlocks.hydroponic);
        OreDictionary.registerOre("machinePlastic", ModBlocks.light);
        OreDictionary.registerOre("machinePlastic", ModBlocks.shredder);
        OreDictionary.registerOre("machinePlastic", ModBlocks.processor);
    }

    private static int[] getValidPlasticOreDictIds() {
        String[] oreDictNames = OreDictionary.getOreNames();
        ArrayList<Integer> validOreIds = new ArrayList<Integer>();

        EmergingTechnology.logger.info("Checking for plastic oreDict entries...");

        for (int i = 0; i < oreDictNames.length; i++) {
            if (isEntryPlastic(oreDictNames[i])) {
                validOreIds.add(OreDictionary.getOreID(oreDictNames[i]));
            }
        }

        EmergingTechnology.logger.info("Found " + validOreIds.size() + " plastic entries: ");
        
        for (int id : validOreIds) {
            EmergingTechnology.logger.info(OreDictionary.getOreName(id));
        }

        return convertIntegers(validOreIds); // I miss LINQ ;_;
    }

    private static boolean isEntryPlastic(String name) {
        name = name.toLowerCase();
        return name.contains("plastic") || name.contains("rubber") || name.contains("resin") || name.contains("rosin");
    }

    public static int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }
}