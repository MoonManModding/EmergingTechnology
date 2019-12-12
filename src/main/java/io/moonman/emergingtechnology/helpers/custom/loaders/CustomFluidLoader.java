package io.moonman.emergingtechnology.helpers.custom.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.helpers.custom.classes.CustomFluid;
import io.moonman.emergingtechnology.helpers.custom.classes.CustomGrowthMedium;
import io.moonman.emergingtechnology.helpers.custom.helpers.CustomFluidHelper;
import io.moonman.emergingtechnology.helpers.custom.helpers.CustomGrowthMediumHelper;
import io.moonman.emergingtechnology.helpers.custom.wrappers.CustomFluidWrapper;
import io.moonman.emergingtechnology.helpers.machines.LightHelper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Loads and validates the custom fluid JSON file
 */
public class CustomFluidLoader {

    public static final int STARTING_ID = 5;

    public static void preInit(FMLPreInitializationEvent e) {

        String path = e.getModConfigurationDirectory().getAbsolutePath() + "\\" + EmergingTechnology.MODID
                + "\\custom-fluids.json";
                loadCustomFluids(path);
    }

    public static void loadCustomFluids(String filePath) {
        EmergingTechnology.logger.info("EmergingTechnology - Attempting to load custom fluids...");
        try {
            CustomFluidHelper.customFluids = readFromJson(filePath);
            EmergingTechnology.logger.info("EmergingTechnology - Loaded "
                    + CustomFluidHelper.customFluids.length + " custom fluids.");

            for (CustomFluid fluid : CustomFluidHelper.customFluids) {
                EmergingTechnology.logger.info(fluid.name + " - Id:" + fluid.id + " g: " + fluid.growthModifier
                        + " e: " + fluid.fluidUsage + " ap: " + fluid.allPlants + " p: " + fluid.plants.length);
            }

        } catch (Exception ex) {
            EmergingTechnology.logger.warn("Warning! There was a problem loading custom fluids:");
            EmergingTechnology.logger.warn(ex);
            CustomGrowthMediumHelper.customGrowthMedia = new CustomGrowthMedium[] {};
        }
    }

    private static CustomFluid[] readFromJson(String filePath) throws IOException {

        Gson gson = new Gson();

        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        JsonElement je = gson.fromJson(bufferedReader, JsonElement.class);
        JsonArray json = je.getAsJsonArray();
        CustomFluidWrapper[] wrappers = gson.fromJson(json, CustomFluidWrapper[].class);

        return generateCustomFluidsFromWrappers(wrappers);
    }

    private static CustomFluid[] generateCustomFluidsFromWrappers(CustomFluidWrapper[] wrappers) {
        CustomFluid[] customFluids = new CustomFluid[wrappers.length];

        for (int i = 0; i < wrappers.length; i++) {
            CustomFluid fluid = validateWrapper(i, wrappers[i]);
            customFluids[i] = fluid;
        }

        return customFluids;
    }

    private static CustomFluid validateWrapper(int index, CustomFluidWrapper wrapper) {

        int id = STARTING_ID + index;
        String name = wrapper.name;
        int growthModifier = checkBounds(wrapper.growthModifier);
        int energyUsage = checkBounds(wrapper.fluidUsage);

        int boostModifier = wrapper.boostModifier;
        String[] plants = wrapper.plants;

        boolean allPlants = false;

        if (plants.length == 0)
            allPlants = true;

        return new CustomFluid(id, name, energyUsage, growthModifier, allPlants, plants, boostModifier);
    }

    private static int checkBounds(int value) {
        value = value > 100 ? 100 : value;
        value = value < 0 ? 0 : value;

        return value;
    }
}