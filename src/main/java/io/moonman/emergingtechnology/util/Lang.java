package io.moonman.emergingtechnology.util;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.enums.ResourceTypeEnum;
import net.minecraft.util.text.TextComponentTranslation;

public class Lang {

    private static final String SOURCE = "info.emergingtechnology.";
    private static final String GUI_SOURCE = "gui.emergingtechnology.";
    private static final String DESC = ".description";

    // Machine descriptions for tooltips
    public static final TextComponentTranslation BIOMASS_DESC = new TextComponentTranslation(SOURCE + "biomass" + DESC);
    public static final TextComponentTranslation BIOREACTOR_DESC = new TextComponentTranslation(
            SOURCE + "bioreactor" + DESC);
    public static final TextComponentTranslation COLLECTOR_DESC = new TextComponentTranslation(
            SOURCE + "collector" + DESC);
    public static final TextComponentTranslation COOKER_DESC = new TextComponentTranslation(SOURCE + "cooker" + DESC);
    public static final TextComponentTranslation FABRICATOR_DESC = new TextComponentTranslation(
            SOURCE + "fabricator" + DESC);
    public static final TextComponentTranslation FILLER_DESC = new TextComponentTranslation(SOURCE + "filler" + DESC);
    public static final TextComponentTranslation SCRUBBER_DESC = new TextComponentTranslation(
            SOURCE + "scrubber" + DESC);
    public static final TextComponentTranslation DIFFUSER_DESC = new TextComponentTranslation(
            SOURCE + "diffuser" + DESC);
    public static final TextComponentTranslation HARVESTER_DESC = new TextComponentTranslation(
            SOURCE + "harvester" + DESC);
    public static final TextComponentTranslation HYDROPONIC_DESC = new TextComponentTranslation(
            SOURCE + "hydroponic" + DESC);
    public static final TextComponentTranslation LIGHT_DESC = new TextComponentTranslation(SOURCE + "light" + DESC);
    public static final TextComponentTranslation PIEZOELECTRIC_DESC = new TextComponentTranslation(
            SOURCE + "piezoelectric" + DESC);
    public static final TextComponentTranslation PROCESSOR_DESC = new TextComponentTranslation(
            SOURCE + "processor" + DESC);
    public static final TextComponentTranslation SCAFFOLDER_DESC = new TextComponentTranslation(
            SOURCE + "scaffolder" + DESC);
    public static final TextComponentTranslation SHREDDER_DESC = new TextComponentTranslation(
            SOURCE + "shredder" + DESC);
    public static final TextComponentTranslation SOLAR_DESC = new TextComponentTranslation(SOURCE + "solar" + DESC);
    public static final TextComponentTranslation SOLARGLASS_DESC = new TextComponentTranslation(SOURCE + "solarglass" + DESC);
    public static final TextComponentTranslation TIDAL_DESC = new TextComponentTranslation(SOURCE + "tidal" + DESC);
    public static final TextComponentTranslation WIND_DESC = new TextComponentTranslation(SOURCE + "wind" + DESC);
    public static final TextComponentTranslation BATTERY_DESC = new TextComponentTranslation(SOURCE + "battery" + DESC);

    // Block descriptions for tooltips
    public static final TextComponentTranslation FRAME_DESC = new TextComponentTranslation(
            SOURCE + "frame.description");

    // Bulb descriptions for tooltips
    public static final TextComponentTranslation BULB_DESC = new TextComponentTranslation(SOURCE + "bulb.description");
    public static final TextComponentTranslation EMPTY_SYRINGE_DESC = new TextComponentTranslation(
            SOURCE + "emptysyringe.description");

    // Nozzle descriptions for tooltips
    public static final TextComponentTranslation NOZZLE_DESC = new TextComponentTranslation(
            SOURCE + "nozzle.description");
    public static final TextComponentTranslation NOZZLE_COMPONENT_DESC = new TextComponentTranslation(
            SOURCE + "nozzlecomponent.description");

    // Interaction descriptions
    public static final TextComponentTranslation INTERACT_RIGHT_CLICK = new TextComponentTranslation(
            SOURCE + "interaction.rightclick");
    public static final TextComponentTranslation INTERACT_SHIFT = new TextComponentTranslation(
            SOURCE + "interaction.shift");

    // GUI
    public static final TextComponentTranslation GUI_BASE_MEDIUM = new TextComponentTranslation(
            GUI_SOURCE + "label.basemedium");
    public static final TextComponentTranslation GUI_BASE_FLUID = new TextComponentTranslation(
            GUI_SOURCE + "label.basefluid");
    public static final TextComponentTranslation GUI_BOOST_MEDIUM = new TextComponentTranslation(
            GUI_SOURCE + "label.boostmedium");
    public static final TextComponentTranslation GUI_BOOST_FLUID = new TextComponentTranslation(
            GUI_SOURCE + "label.boostfluid");
    public static final TextComponentTranslation GUI_BOOST_LIGHT = new TextComponentTranslation(
            GUI_SOURCE + "label.boostlight");
    public static final TextComponentTranslation GUI_BASE = new TextComponentTranslation(GUI_SOURCE + "label.base");
    public static final TextComponentTranslation GUI_BOOST = new TextComponentTranslation(GUI_SOURCE + "label.boost");
    public static final TextComponentTranslation GUI_MAX_RANGE = new TextComponentTranslation(
            GUI_SOURCE + "label.maxrange");
    public static final TextComponentTranslation GUI_STORAGE_ENERGY = new TextComponentTranslation(
            GUI_SOURCE + "label.energy");
    public static final TextComponentTranslation GUI_STORAGE_FLUID = new TextComponentTranslation(
            GUI_SOURCE + "label.fluid");
    public static final TextComponentTranslation GUI_STORAGE_GAS = new TextComponentTranslation(
            GUI_SOURCE + "label.gas");
    public static final TextComponentTranslation GUI_STORAGE_HEAT = new TextComponentTranslation(
            GUI_SOURCE + "label.heat");
    public static final TextComponentTranslation GUI_GROWTH = new TextComponentTranslation(GUI_SOURCE + "label.growth");
    public static final TextComponentTranslation GUI_AFFECTED_PLANTS = new TextComponentTranslation(
            GUI_SOURCE + "label.affectedplants");
    public static final TextComponentTranslation GUI_ERROR = new TextComponentTranslation(GUI_SOURCE + "label.error");

    public static final TextComponentTranslation GUI_FABRICATOR_ERROR = new TextComponentTranslation(
            GUI_SOURCE + "fabricator.error");
    public static final TextComponentTranslation GUI_FABRICATOR_NO_ENERGY = new TextComponentTranslation(
            GUI_SOURCE + "fabricator.noenergy");
    public static final TextComponentTranslation GUI_FABRICATOR_NO_INPUT = new TextComponentTranslation(
            GUI_SOURCE + "fabricator.noinput");
    public static final TextComponentTranslation GUI_FABRICATOR_FULL_OUTPUT = new TextComponentTranslation(
            GUI_SOURCE + "fabricator.fulloutput");
    public static final TextComponentTranslation GUI_FABRICATOR_INVALID_OUTPUT = new TextComponentTranslation(
            GUI_SOURCE + "fabricator.invalidoutput");
    public static final TextComponentTranslation GUI_FABRICATOR_INVALID_INPUT = new TextComponentTranslation(
            GUI_SOURCE + "fabricator.invalidinput");
    public static final TextComponentTranslation GUI_FABRICATOR_BUTTON_PROGRAM = new TextComponentTranslation(
            GUI_SOURCE + "fabricator.buttonprogram");
    public static final TextComponentTranslation GUI_FABRICATOR_BUTTON_REQUIRES = new TextComponentTranslation(
            GUI_SOURCE + "fabricator.buttonrequires");

    // Items
    public static String getMeatName(String name, boolean cooked) {
        String type = cooked ? "cooked" : "raw";
        return new TextComponentTranslation(SOURCE + type + "meat.name", name).getFormattedText();
    }

    public static String getMeatDescription(String name, boolean cooked) {
        String type = cooked ? "cooked" : "raw";
        return new TextComponentTranslation(SOURCE + type + "meat.description", name).getFormattedText();
    }

    public static String getTissueName(String name, boolean isSample) {
        String type = isSample ? "sample" : "syringe";
        return new TextComponentTranslation(SOURCE + type + ".name", name).getFormattedText();
    }

    public static String getTissueDescription(String name, boolean isSample) {
        String type = isSample ? "sample" : "syringe";
        return new TextComponentTranslation(SOURCE + type + ".description", name).getFormattedText();
    }

    // Special descriptions
    public static String getSmartNozzleDescription() {
        return new TextComponentTranslation(SOURCE + "nozzlesmart.description",
                EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.SMART.rangeMultiplier,
                EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.SMART.boostMultiplier).getFormattedText();
    }

    public static String getLongNozzleDescription() {
        return new TextComponentTranslation(SOURCE + "nozzlelong.description",
                EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.LONG.rangeMultiplier).getFormattedText();
    }

    public static String getPreciseNozzleDescription() {
        return new TextComponentTranslation(SOURCE + "nozzleprecise.description",
                EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.PRECISE.boostMultiplier).getFormattedText();
    }

    // Special requirements
    public static final TextComponentTranslation BIOME_REQUIREMENT = new TextComponentTranslation(
            SOURCE + "biome.required");
    public static final TextComponentTranslation WATER_SURFACE_REQUIREMENT = new TextComponentTranslation(
            SOURCE + "watersurface.required");

    public static String getWaterBlocksRequired(int required) {
        return new TextComponentTranslation(SOURCE + "waterblocks.required", required).getFormattedText();
    }

    public static String getAirBlocksRequired(int required) {
        return new TextComponentTranslation(SOURCE + "airblocks.required", required).getFormattedText();
    }

    public static String getDepthBoost(int min, int max) {
        return new TextComponentTranslation(SOURCE + "waterdepth.required", min, max).getFormattedText();
    }

    // Special generated

    public static String getHeatGainLoss(int gain, int loss) {
        return new TextComponentTranslation(SOURCE + "cookerheat.generated", gain, loss).getFormattedText();
    }

    public static String getLightRange(int range) {
        return new TextComponentTranslation(SOURCE + "lightrange.generated", range).getFormattedText();
    }

    public static String getGasRange(int range) {
        return new TextComponentTranslation(SOURCE + "gasrange.generated", range).getFormattedText();
    }

    // Resource
    public static String getRequired(int required, ResourceTypeEnum type) {
        return new TextComponentTranslation(SOURCE + type.toString().toLowerCase() + ".required", required)
                .getFormattedText();
    }

    public static String getGenerated(int generated, ResourceTypeEnum type) {
        return new TextComponentTranslation(SOURCE + type.toString().toLowerCase() + ".generated", generated)
                .getFormattedText();
    }

    public static String getCapacity(int capacity, ResourceTypeEnum type) {
        return new TextComponentTranslation(SOURCE + type.toString().toLowerCase() + ".capacity", capacity)
                .getFormattedText();
    }

    public static String get(TextComponentTranslation translation) {
        return translation.getFormattedText();
    }

    public static String capitaliseName(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}