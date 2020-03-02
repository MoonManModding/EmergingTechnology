package io.moonman.emergingtechnology.providers.loaders;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.providers.classes.ModFluid;
import io.moonman.emergingtechnology.providers.ModFluidProvider;
import io.moonman.emergingtechnology.providers.system.JsonHelper;
import io.moonman.emergingtechnology.providers.wrappers.CustomFluidWrapper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Loads and validates the custom fluid JSON file
 */
public class CustomFluidLoader {

    public static void preInit(FMLPreInitializationEvent e) {
        loadCustomFluids(JsonHelper.getPathForFile(e, "custom-fluids.json"));
    }

    public static void loadCustomFluids(String filePath) {
        EmergingTechnology.logger.info("EmergingTechnology - Loading custom fluids...");
        try {
            ModFluidProvider.customFluids = readFromJson(filePath);
            EmergingTechnology.logger.info("EmergingTechnology - Loaded "
                    + ModFluidProvider.customFluids.length + " custom fluids.");

        } catch (FileNotFoundException ex) {
            EmergingTechnology.logger.warn("Fluid file not found.");
            ModFluidProvider.customFluids = new ModFluid[] {};
        } catch (Exception ex) {
            EmergingTechnology.logger.warn("Warning! There was a problem loading custom fluids:");
            EmergingTechnology.logger.warn(ex);
            ModFluidProvider.customFluids = new ModFluid[] {};
        }
    }

    private static ModFluid[] readFromJson(String filePath) throws IOException {

        CustomFluidWrapper[] wrappers = JsonHelper.GSON_INSTANCE.fromJson(JsonHelper.readFromJson(filePath), CustomFluidWrapper[].class);

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