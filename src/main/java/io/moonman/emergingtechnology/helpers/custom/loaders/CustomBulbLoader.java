package io.moonman.emergingtechnology.helpers.custom.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.helpers.custom.classes.CustomBulb;
import io.moonman.emergingtechnology.helpers.custom.classes.CustomGrowthMedium;
import io.moonman.emergingtechnology.helpers.custom.helpers.CustomBulbHelper;
import io.moonman.emergingtechnology.helpers.custom.helpers.CustomGrowthMediumHelper;
import io.moonman.emergingtechnology.helpers.custom.wrappers.CustomBulbWrapper;
import io.moonman.emergingtechnology.helpers.machines.LightHelper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Loads and validates the custom bulb JSON file
 */
public class CustomBulbLoader {

    public static final int STARTING_ID = 5;

    public static void preInit(FMLPreInitializationEvent e) {

        String path = e.getModConfigurationDirectory().getAbsolutePath() + "\\" + EmergingTechnology.MODID
                + "\\custom-bulbs.json";
                loadCustomBulbs(path);
    }

    public static void loadCustomBulbs(String filePath) {
        EmergingTechnology.logger.info("EmergingTechnology - Attempting to load custom bulbs...");
        try {
            CustomBulbHelper.customBulbs = readFromJson(filePath);
            EmergingTechnology.logger.info("EmergingTechnology - Loaded "
                    + CustomBulbHelper.customBulbs.length + " custom bulbs.");

            for (CustomBulb bulb : CustomBulbHelper.customBulbs) {
                EmergingTechnology.logger.info(bulb.name + " - Id:" + bulb.id + " g: " + bulb.growthModifier
                        + " e: " + bulb.energyUsage + " ap: " + bulb.allPlants + " p: " + bulb.plants.length);
            }

        } catch (Exception ex) {
            EmergingTechnology.logger.warn("Warning! There was a problem loading custom bulbs:");
            EmergingTechnology.logger.warn(ex);
            CustomGrowthMediumHelper.customGrowthMedia = new CustomGrowthMedium[] {};
        }
    }

    private static CustomBulb[] readFromJson(String filePath) throws IOException {

        Gson gson = new Gson();

        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        JsonElement je = gson.fromJson(bufferedReader, JsonElement.class);
        JsonArray json = je.getAsJsonArray();
        CustomBulbWrapper[] wrappers = gson.fromJson(json, CustomBulbWrapper[].class);

        return generateCustomBulbsFromWrappers(wrappers);
    }

    private static CustomBulb[] generateCustomBulbsFromWrappers(CustomBulbWrapper[] wrappers) {
        CustomBulb[] customBulbs = new CustomBulb[wrappers.length];

        for (int i = 0; i < wrappers.length; i++) {
            CustomBulb bulb = validateWrapper(i, wrappers[i]);
            customBulbs[i] = bulb;
        }

        return customBulbs;
    }

    private static CustomBulb validateWrapper(int index, CustomBulbWrapper wrapper) {

        int id = STARTING_ID + index;
        String name = wrapper.name;
        int color = wrapper.color <= LightHelper.BULB_COUNT ? wrapper.color : LightHelper.BULB_COUNT;
        int growthModifier = checkBounds(wrapper.growthModifier);
        int energyUsage = checkBounds(wrapper.energyUsage);

        int boostModifier = wrapper.boostModifier;
        String[] plants = wrapper.plants;

        boolean allPlants = false;

        if (plants.length == 0)
            allPlants = true;

        return new CustomBulb(id, name, color, energyUsage, growthModifier, allPlants, plants, boostModifier);
    }

    private static int checkBounds(int value) {
        value = value > 100 ? 100 : value;
        value = value < 0 ? 0 : value;

        return value;
    }
}