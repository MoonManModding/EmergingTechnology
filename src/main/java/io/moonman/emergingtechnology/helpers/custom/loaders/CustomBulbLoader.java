package io.moonman.emergingtechnology.helpers.custom.loaders;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.providers.classes.ModBulb;
import io.moonman.emergingtechnology.providers.ModBulbProvider;
import io.moonman.emergingtechnology.helpers.custom.system.JsonHelper;
import io.moonman.emergingtechnology.helpers.custom.wrappers.CustomBulbWrapper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Loads and validates the custom bulb JSON file
 */
public class CustomBulbLoader {

    public static void preInit(FMLPreInitializationEvent e) {
        loadCustomBulbs(JsonHelper.getPathForFile(e, "custom-bulbs.json"));
    }

    public static void loadCustomBulbs(String filePath) {
        EmergingTechnology.logger.info("EmergingTechnology - Loading custom bulbs...");
        try {
            ModBulbProvider.customBulbs = readFromJson(filePath);
            EmergingTechnology.logger
                    .info("EmergingTechnology - Loaded " + ModBulbProvider.customBulbs.length + " custom bulbs.");

        } catch (FileNotFoundException ex) {
            EmergingTechnology.logger.warn("Bulb file not found.");
            ModBulbProvider.customBulbs = new ModBulb[] {};
        } catch (Exception ex) {
            EmergingTechnology.logger.warn("Warning! There was a problem loading custom bulbs:");
            EmergingTechnology.logger.warn(ex);
            ModBulbProvider.customBulbs = new ModBulb[] {};
        }
    }

    private static ModBulb[] readFromJson(String filePath) throws IOException {

        CustomBulbWrapper[] wrappers = JsonHelper.GSON_INSTANCE.fromJson(JsonHelper.readFromJson(filePath),
                CustomBulbWrapper[].class);

        return generateModBulbsFromWrappers(wrappers);
    }

    private static ModBulb[] generateModBulbsFromWrappers(CustomBulbWrapper[] wrappers) {
        ModBulb[] customBulbs = new ModBulb[wrappers.length];

        for (int i = 0; i < wrappers.length; i++) {
            ModBulb bulb = validateWrapper(wrappers[i]);
            customBulbs[i] = bulb;
        }

        return customBulbs;
    }

    private static ModBulb validateWrapper(CustomBulbWrapper wrapper) {

        String name = wrapper.name;
        int color = wrapper.color <= 4 ? wrapper.color : 0;
        int growthModifier = checkBounds(wrapper.growthModifier);
        int energyUsage = checkBounds(wrapper.energyUsage);

        int boostModifier = wrapper.boostModifier;
        String[] plants = wrapper.plants;

        return new ModBulb(0, name, color, energyUsage, growthModifier, plants, boostModifier);
    }

    private static int checkBounds(int value) {
        value = value > 100 ? 100 : value;
        value = value < 0 ? 0 : value;

        return value;
    }
}