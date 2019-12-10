package io.moonman.emergingtechnology.helpers.custom.loaders;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraftforge.oredict.OreDictionary;

/**
Loads useful Ore Dictionary lists for Emerging Technology
*/
public class OreDictionaryLoader {

    public static int[] PLASTIC_ORE_IDS;

    public static void preInit() {
        PLASTIC_ORE_IDS = getValidOreDictIds();
    }

    public static int[] getValidOreDictIds() {
        String[] oreDictNames = OreDictionary.getOreNames();
        int[] validOreIds = new int[]{};

        EmergingTechnology.logger.info("Checking for plastic oreDict entries...");

        for (int i = 0; i < oreDictNames.length; i++) {
            if (oreDictNames[i].contains("plastic") || oreDictNames[i].contains("rubber") || oreDictNames[i].contains("resin")) {
                validOreIds[i] = OreDictionary.getOreID(oreDictNames[i]);
            }
        }

        EmergingTechnology.logger.info("Found " + validOreIds.length + " plastic entries.");

        return validOreIds;
    }

}