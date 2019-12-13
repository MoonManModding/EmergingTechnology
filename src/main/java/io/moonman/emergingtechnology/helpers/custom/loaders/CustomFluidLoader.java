package io.moonman.emergingtechnology.helpers.custom.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.providers.classes.ModFluid;
import io.moonman.emergingtechnology.providers.ModFluidProvider;
import io.moonman.emergingtechnology.helpers.custom.wrappers.CustomFluidWrapper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Loads and validates the custom fluid JSON file
 */
public class CustomFluidLoader {

    public static void preInit(FMLPreInitializationEvent e) {

        String path = e.getModConfigurationDirectory().getAbsolutePath() + "\\" + EmergingTechnology.MODID
                + "\\custom-fluids.json";
                loadCustomFluids(path);
    }

    public static void loadCustomFluids(String filePath) {
        EmergingTechnology.logger.info("EmergingTechnology - Attempting to load custom fluids...");
        try {
            ModFluidProvider.customFluids = readFromJson(filePath);
            EmergingTechnology.logger.info("EmergingTechnology - Loaded "
                    + ModFluidProvider.customFluids.length + " custom fluids.");

            for (ModFluid fluid : ModFluidProvider.customFluids) {
                EmergingTechnology.logger.info(fluid.name + " - Id:" + fluid.id + " g: " + fluid.growthModifier
                         + " ap: " + fluid.allPlants + " p: " + fluid.plants.length);
            }

        } catch (Exception ex) {
            EmergingTechnology.logger.warn("Warning! There was a problem loading custom fluids:");
            EmergingTechnology.logger.warn(ex);
            ModFluidProvider.customFluids = new ModFluid[] {};
        }
    }

    private static ModFluid[] readFromJson(String filePath) throws IOException {

        Gson gson = new Gson();

        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        JsonElement je = gson.fromJson(bufferedReader, JsonElement.class);
        JsonArray json = je.getAsJsonArray();
        CustomFluidWrapper[] wrappers = gson.fromJson(json, CustomFluidWrapper[].class);

        return generateModFluidsFromWrappers(wrappers);
    }

    private static ModFluid[] generateModFluidsFromWrappers(CustomFluidWrapper[] wrappers) {
        ModFluid[] customFluids = new ModFluid[wrappers.length];

        for (int i = 0; i < wrappers.length; i++) {
            ModFluid fluid = validateWrapper(wrappers[i]);
            customFluids[i] = fluid;
        }

        return customFluids;
    }

    private static ModFluid validateWrapper(CustomFluidWrapper wrapper) {

        String name = wrapper.name;
        int growthModifier = checkBounds(wrapper.growthModifier);

        int boostModifier = wrapper.boostModifier;
        String[] plants = wrapper.plants;

        return new ModFluid(0, name, growthModifier, plants, boostModifier);
    }

    private static int checkBounds(int value) {
        value = value > 100 ? 100 : value;
        value = value < 0 ? 0 : value;

        return value;
    }
}