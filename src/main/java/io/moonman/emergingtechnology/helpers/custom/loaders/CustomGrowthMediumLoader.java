package io.moonman.emergingtechnology.helpers.custom.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.helpers.custom.classes.CustomGrowthMedium;
import io.moonman.emergingtechnology.helpers.custom.helpers.CustomGrowthMediumHelper;
import io.moonman.emergingtechnology.helpers.custom.wrappers.CustomGrowthMediumWrapper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
Loads and validates the custom growth medium JSON file
*/
public class CustomGrowthMediumLoader {

    public static final int STARTING_ID = 6;

    public static void preInit(FMLPreInitializationEvent e) {

        String path = e.getModConfigurationDirectory().getAbsolutePath() + "\\" + EmergingTechnology.MODID + "\\custom-media.json";
        loadCustomGrowthMedia(path);
    }

    public static void loadCustomGrowthMedia(String customGrowthMediaFilePath) {
        EmergingTechnology.logger.info("EmergingTechnology - Attempting to load custom growth media...");
        try {
            CustomGrowthMediumHelper.customGrowthMedia = readFromJson(customGrowthMediaFilePath);
            EmergingTechnology.logger.info("EmergingTechnology - Loaded " + CustomGrowthMediumHelper.customGrowthMedia.length
                    + " custom growth media.");

            for (CustomGrowthMedium medium :CustomGrowthMediumHelper.customGrowthMedia) {
                EmergingTechnology.logger.info(medium.name + " - Id:" + medium.id + " g: " + medium.growthModifier + " w: " + medium.waterUsage);
            }

        } catch (Exception ex) {
            EmergingTechnology.logger.warn("Warning! There was a problem loading custom growth media:");
            EmergingTechnology.logger.warn(ex);
            CustomGrowthMediumHelper.customGrowthMedia = new CustomGrowthMedium[] {};
        }
    }

    private static CustomGrowthMedium[] readFromJson(String customGrowthMediaFilePath) throws IOException {

        Gson gson = new Gson();

        FileReader fileReader = new FileReader(customGrowthMediaFilePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        JsonElement je = gson.fromJson(bufferedReader, JsonElement.class);
        JsonArray json = je.getAsJsonArray();
        CustomGrowthMediumWrapper[] wrappers = gson.fromJson(json, CustomGrowthMediumWrapper[].class);

        return generateCustomGrowthMediaFromWrappers(wrappers);
    }

    private static CustomGrowthMedium[] generateCustomGrowthMediaFromWrappers(CustomGrowthMediumWrapper[] wrappers) {
        CustomGrowthMedium[] customGrowthMedia = new CustomGrowthMedium[wrappers.length];

        for (int i = 0; i < wrappers.length; i++) {
            CustomGrowthMedium medium = validateWrapper(i, wrappers[i]);
            customGrowthMedia[i] = medium;
        }

        return customGrowthMedia;
    }

    private static CustomGrowthMedium validateWrapper(int index, CustomGrowthMediumWrapper wrapper) {

        int id = STARTING_ID + index;
        String name = wrapper.name;
        int growthModifier = checkBounds(wrapper.growthModifier);
        int waterUsage = checkBounds(wrapper.waterUsage);

        return new CustomGrowthMedium(id, name, waterUsage, growthModifier);
    }

    private static int checkBounds(int value) {
        value = value > 100 ? 100 : value;
        value = value < 0 ? 0 : value;

        return value;
    }
}