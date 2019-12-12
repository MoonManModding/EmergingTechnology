package io.moonman.emergingtechnology.helpers.custom.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.helpers.custom.classes.ModMedium;
import io.moonman.emergingtechnology.helpers.custom.providers.ModMediumProvider;
import io.moonman.emergingtechnology.helpers.custom.wrappers.CustomMediumWrapper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Loads and validates the custom growth medium JSON file
 */
public class CustomMediumLoader {

    public static void preInit(FMLPreInitializationEvent e) {

        String path = e.getModConfigurationDirectory().getAbsolutePath() + "\\" + EmergingTechnology.MODID
                + "\\custom-media.json";
        loadCustomGrowthMedia(path);
    }

    public static void loadCustomGrowthMedia(String customGrowthMediaFilePath) {
        EmergingTechnology.logger.info("EmergingTechnology - Attempting to load custom growth media...");
        try {
            ModMediumProvider.customMedia= readFromJson(customGrowthMediaFilePath);
            EmergingTechnology.logger.info("EmergingTechnology - Loaded "
                    + ModMediumProvider.customMedia.length + " custom growth media.");

            for (ModMedium medium : ModMediumProvider.customMedia) {
                EmergingTechnology.logger.info(medium.name + " - Id:" + medium.id + " g: " + medium.growthModifier
                        + " w: " + medium.waterUsage + " ap: " + medium.allPlants + " p: " + medium.plants.length);
            }

        } catch (Exception ex) {
            EmergingTechnology.logger.warn("Warning! There was a problem loading custom growth media:");
            EmergingTechnology.logger.warn(ex);
            ModMediumProvider.customMedia = new ModMedium[] {};
        }
    }

    private static ModMedium[] readFromJson(String customGrowthMediaFilePath) throws IOException {

        Gson gson = new Gson();

        FileReader fileReader = new FileReader(customGrowthMediaFilePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        JsonElement je = gson.fromJson(bufferedReader, JsonElement.class);
        JsonArray json = je.getAsJsonArray();
        CustomMediumWrapper[] wrappers = gson.fromJson(json, CustomMediumWrapper[].class);

        return generateModMediaFromWrappers(wrappers);
    }

    private static ModMedium[] generateModMediaFromWrappers(CustomMediumWrapper[] wrappers) {
        ModMedium[] customGrowthMedia = new ModMedium[wrappers.length];

        for (int i = 0; i < wrappers.length; i++) {
            ModMedium medium = validateWrapper(wrappers[i]);
            customGrowthMedia[i] = medium;
        }

        return customGrowthMedia;
    }

    private static ModMedium validateWrapper(CustomMediumWrapper wrapper) {

        String name = wrapper.name;
        int growthModifier = checkBounds(wrapper.growthModifier);
        int waterUsage = checkBounds(wrapper.waterUsage);

        int boostModifier = wrapper.boostModifier;
        String[] plants = wrapper.plants;

        return new ModMedium(0, name, waterUsage, growthModifier, plants, boostModifier);
    }

    private static int checkBounds(int value) {
        value = value > 100 ? 100 : value;
        value = value < 0 ? 0 : value;

        return value;
    }
}