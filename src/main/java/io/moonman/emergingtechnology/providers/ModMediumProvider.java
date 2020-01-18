package io.moonman.emergingtechnology.providers;

import java.util.ArrayList;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.providers.classes.ModMedium;
import io.moonman.emergingtechnology.helpers.custom.loaders.CustomMediumLoader;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModMediumProvider {
    private static ModMedium[] allMedia;
    public static ModMedium[] customMedia;

    public static final int BASE_MEDIUM_COUNT = 6;

    public static ModMedium[] getMedia() {
        return allMedia;
    }

    public static void preInit(FMLPreInitializationEvent event) {

        readFromFile(event);

        ArrayList<ModMedium> generatedMedia = new ArrayList<ModMedium>();

        generatedMedia.addAll(generateBaseMedia());
        generatedMedia.addAll(generateCustomMedia());

        allMedia = generatedMedia.toArray(new ModMedium[0]);
    }

    private static ArrayList<ModMedium> generateBaseMedia() {

        String[] plants = new String[] {};

        ModMedium dirt = new ModMedium(1, "minecraft:dirt", EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.DIRT.growthDirtFluidUsage, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.DIRT.growthDirtModifier, plants, 0, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.DIRT.destroyProbability);
        ModMedium sand = new ModMedium(2, "minecraft:sand", EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.SAND.growthSandFluidUsage, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.SAND.growthSandModifier, plants, 0, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.SAND.destroyProbability);
        ModMedium gravel = new ModMedium(3, "minecraft:gravel", EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.GRAVEL.growthGravelFluidUsage, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.GRAVEL.growthGravelModifier, plants, 0, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.GRAVEL.destroyProbability);
        ModMedium clay = new ModMedium(4, "minecraft:clay", EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.CLAY.growthClayFluidUsage, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.CLAY.growthClayModifier, plants, 0, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.CLAY.destroyProbability);
        ModMedium clayball = new ModMedium(5, "minecraft:clay_ball", EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.CLAY.growthClayFluidUsage, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.CLAY.growthClayModifier, plants, 0, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.CLAY.destroyProbability);
        ModMedium biochar = new ModMedium(6, "emergingtechnology:biocharblock", EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.BIOCHAR.growthBiocharFluidUsage, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.BIOCHAR.growthBiocharModifier, plants, 0, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.BIOCHAR.destroyProbability);

        ArrayList<ModMedium> baseMedia = new ArrayList<ModMedium>();

        baseMedia.add(dirt);
        baseMedia.add(sand);
        baseMedia.add(gravel);
        baseMedia.add(clay);
        baseMedia.add(clayball);
        baseMedia.add(biochar);

        return baseMedia;
    }

    private static ArrayList<ModMedium> generateCustomMedia() {
        ArrayList<ModMedium> media = new ArrayList<ModMedium>();

        if (customMedia != null) {
            int idCounter = BASE_MEDIUM_COUNT + 1;
            for (ModMedium medium : customMedia) {
                medium.id = idCounter;
                media.add(medium);
                idCounter++;
            }
        }

        return media;
    }

    private static void readFromFile(FMLPreInitializationEvent event) {
        CustomMediumLoader.preInit(event);
    }

    public static boolean mediumExists(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;

        String name = itemStack.getItem().getRegistryName().toString();

        ModMedium bulb = getMediumByName(name);

        if (bulb == null) return false;

        return true;
    }

    public static ModMedium getMediumById(int id) {
        for (ModMedium medium : allMedia) {
            if (id == medium.id) {
                return medium;
            }
        }

        return null;
    }

    public static ModMedium getMediumByName(String name) {
        for (ModMedium medium : allMedia) {
            if (name.equalsIgnoreCase(medium.name)) {
                return medium;
            }
        }
        return null;
    }

    public static int getMediumIdFromItemStack(ItemStack itemStack) {
        for (ModMedium medium : allMedia) {
            if (itemStack.getItem().getRegistryName().toString().equalsIgnoreCase(medium.name)) {
                return medium.id;
            }
        }

        return 0;
    }

    public static int getGrowthProbabilityForMediumById(int id) {
        ModMedium medium = getMediumById(id);

        if (medium == null) return 0;

        return medium.growthModifier;

    }

    public static int getDestroyProbabilityForMediumById(int id) {
        ModMedium medium = getMediumById(id);

        if (medium == null) return 0;

        return medium.destroyChance;

    }

    public static int getFluidUsageForMediumById(int id) {
        ModMedium medium = getMediumById(id);

        if (medium == null) return 0;
        
        return medium.waterUsage;
    }

    public static String[] getBoostPlantNamesForMediumById(int id) {
        ModMedium medium = getMediumById(id);

        if (medium == null) return new String[] {};

        return medium.plants;
    }

    public static int getBoostModifierForMediumById(int id) {
        ModMedium medium = getMediumById(id);

        if (medium == null) return 0;

        return medium.boostModifier;
    }

    public static int getSpecificPlantGrowthBoostForId(int id, String plantName) {

        ModMedium medium = getMediumById(id);

        if (medium == null) {
            return 0;
        }

        if (medium.allPlants == true) {
            return 0;
        }

        for (String plant : medium.plants) {
            if (plantName.equalsIgnoreCase(plant)) {
                return medium.boostModifier;
            }
        }

        return 0;
    }
}