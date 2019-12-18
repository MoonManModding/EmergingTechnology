package io.moonman.emergingtechnology.helpers.custom.loaders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraftforge.oredict.OreDictionary;

/**
 * Loads useful Ore Dictionary lists for Emerging Technology
 */
public class OreDictionaryLoader {

    public static int[] PLASTIC_ORE_IDS;

    public static void init() {
        PLASTIC_ORE_IDS = getValidPlasticOreDictIds();
    }

    private static int[] getValidPlasticOreDictIds() {
        String[] oreDictNames = getValidPlasticOreDictNames();
        ArrayList<Integer> validOreIds = new ArrayList<Integer>();

        for (int i = 0; i < oreDictNames.length; i++) {
                validOreIds.add(OreDictionary.getOreID(oreDictNames[i]));
        }

        return convertIntegers(validOreIds);
    }

    public static String[] getValidPlasticOreDictNames() {
        String[] oreDictNames = OreDictionary.getOreNames();
        List<String> nameList = new ArrayList<String>();

        for (int i = 0; i < oreDictNames.length; i++) {
            nameList.add(oreDictNames[i]);
        }

        return nameList.stream().filter(x -> isEntryPlastic(x)).toArray(String[]::new);
    }

    private static boolean isEntryPlastic(String name) {
        name = name.toLowerCase();
        return name.contains("plastic");
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