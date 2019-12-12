package io.moonman.emergingtechnology.helpers.custom.providers;

import java.util.ArrayList;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.custom.classes.ModBulb;
import io.moonman.emergingtechnology.helpers.custom.loaders.CustomBulbLoader;
import io.moonman.emergingtechnology.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModBulbProvider {
    private static ModBulb[] allBulbs;
    public static ModBulb[] customBulbs;

    public static ModBulb[] getBulbs() {
        return allBulbs;
    }

    public static void preInit(FMLPreInitializationEvent event) {

        readFromFile(event);

        ArrayList<ModBulb> generatedBulbs = new ArrayList<ModBulb>();

        generatedBulbs.addAll(generateBaseBulbs());
        generatedBulbs.addAll(generateCustomBulbs());

        allBulbs = generatedBulbs.toArray(new ModBulb[0]);
    }

    private static ArrayList<ModBulb> generateBaseBulbs() {

        String[] plants = new String[] {};

        ModBulb redBulb = new ModBulb(1, ModItems.redbulb.getRegistryName().toString(), 1, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyRedBulbModifier, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthRedBulbModifier, plants, 0);
        ModBulb greenBulb = new ModBulb(2, ModItems.greenbulb.getRegistryName().toString(), 2, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyGreenBulbModifier, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthGreenBulbModifier, plants, 0);
        ModBulb blueBulb = new ModBulb(3, ModItems.bluebulb.getRegistryName().toString(), 3, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyBlueBulbModifier, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthBlueBulbModifier, plants, 0);
        ModBulb purpleBulb = new ModBulb(4, ModItems.purplebulb.getRegistryName().toString(), 4, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyPurpleBulbModifier, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthPurpleBulbModifier, plants, 0);
        ModBulb glowstone = new ModBulb(5, Blocks.GLOWSTONE.getRegistryName().toString(), 1, 0, 0, plants, 0);

        ArrayList<ModBulb> baseBulbs = new ArrayList<ModBulb>();

        baseBulbs.add(redBulb);
        baseBulbs.add(greenBulb);
        baseBulbs.add(blueBulb);
        baseBulbs.add(purpleBulb);
        baseBulbs.add(glowstone);

        return baseBulbs;
    }

    private static ArrayList<ModBulb> generateCustomBulbs() {
        ArrayList<ModBulb> bulbs = new ArrayList<ModBulb>();

        if (customBulbs != null) {
            int idCounter = 6;
            for (ModBulb bulb : customBulbs) {
                bulb.id = idCounter;
                bulbs.add(bulb);
                idCounter++;
            }
        }

        return bulbs;
    }

    private static void readFromFile(FMLPreInitializationEvent event) {
        CustomBulbLoader.preInit(event);
    }

    public static boolean bulbExists(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;

        String name = itemStack.getItem().getRegistryName().toString();

        ModBulb bulb = getBulbByName(name);

        if (bulb == null) return false;

        return true;
    }

    public static ModBulb getBulbById(int id) {
        for (ModBulb bulb : allBulbs) {
            if (id == bulb.id) {
                return bulb;
            }
        }
        return null;
    }

    public static ModBulb getBulbByName(String name) {
        for (ModBulb bulb : allBulbs) {
            if (name == bulb.name) {
                return bulb;
            }
        }
        return null;
    }

    public static int getBulbIdFromItemStack(ItemStack itemStack) {
        for (ModBulb bulb : allBulbs) {
            if (itemStack.getItem().getRegistryName().toString().equalsIgnoreCase(bulb.name)) {
                return bulb.id;
            }
        }

        return 0;
    }

    public static int getGrowthProbabilityForBulbById(int id) {
        ModBulb bulb = getBulbById(id);

        if (bulb == null) return 0;

        return bulb.growthModifier;

    }

    public static int getEnergyUsageForBulbById(int id) {
        ModBulb bulb = getBulbById(id);

        if (bulb == null) return 0;
        
        return bulb.energyUsage;
    }

    public static String[] getBoostPlantNamesForBulbById(int id) {
        ModBulb bulb = getBulbById(id);

        if (bulb == null) return new String[] {};

        return bulb.plants;
    }

    public static int getBoostModifierForBulbById(int id) {
        ModBulb bulb = getBulbById(id);

        if (bulb == null) return 0;

        return bulb.boostModifier;
    }
}