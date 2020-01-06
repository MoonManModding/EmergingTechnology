package io.moonman.emergingtechnology.helpers.custom.loaders;

import java.io.IOException;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.providers.classes.ModTissue;
import io.moonman.emergingtechnology.providers.ModTissueProvider;
import io.moonman.emergingtechnology.helpers.custom.system.JsonHelper;
import io.moonman.emergingtechnology.helpers.custom.wrappers.CustomTissueWrapper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Loads and validates the custom tissue JSON file
 */
public class CustomTissueLoader {

    public static void preInit(FMLPreInitializationEvent e) {

        String path = e.getModConfigurationDirectory().getAbsolutePath() + "\\" + EmergingTechnology.MODID
                + "\\custom-tissues.json";
        loadCustomTissues(path);
    }

    public static void loadCustomTissues(String path) {
        EmergingTechnology.logger.info("EmergingTechnology - Loading custom tissues...");
        try {
            ModTissueProvider.customTissues = readFromJson(path);
            EmergingTechnology.logger.info("EmergingTechnology - Loaded "
                    + ModTissueProvider.customTissues.length + " custom tissues.");

        } catch (Exception ex) {
            EmergingTechnology.logger.warn("Warning! There was a problem loading custom tissues:");
            EmergingTechnology.logger.warn(ex);
            ModTissueProvider.customTissues = new ModTissue[] {};
        }
    }

    private static ModTissue[] readFromJson(String filePath) throws IOException {

        CustomTissueWrapper[] wrappers = JsonHelper.GSON_INSTANCE.fromJson(JsonHelper.readFromJson(filePath), CustomTissueWrapper[].class);

        return generateModTissueFromWrappers(wrappers);
    }

    private static ModTissue[] generateModTissueFromWrappers(CustomTissueWrapper[] wrappers) {
        ModTissue[] customModTissues = new ModTissue[wrappers.length];

        for (int i = 0; i < wrappers.length; i++) {
            ModTissue tissue = validateWrapper(wrappers[i]);
            customModTissues[i] = tissue;
        }

        return customModTissues;
    }

    private static ModTissue validateWrapper(CustomTissueWrapper wrapper) {

        String name = wrapper.name;
        String entityId = wrapper.entityId;
        String result = wrapper.result;

        return new ModTissue(name, entityId, result);
    }
}