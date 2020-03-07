package io.moonman.emergingtechnology.config.hydroponics.bed;

import io.moonman.emergingtechnology.config.ConfigHelper;
import net.minecraftforge.common.ForgeConfigSpec;

public class GrowBedConfiguration {

    public static final String MACHINE = "Grow Bed";
    public static final String MACHINE_DESCRIPTION = "Grow Bed Settings";

    public static ForgeConfigSpec.BooleanValue DISABLED;

    public static ForgeConfigSpec.IntValue WATER_TRANSFER;
    public static ForgeConfigSpec.IntValue WATER_USAGE;
    public static ForgeConfigSpec.IntValue ENERGY_USAGE;
    public static ForgeConfigSpec.IntValue LAVA_BOOST;
    public static ForgeConfigSpec.BooleanValue REQUIRES_ENERGY;
    public static ForgeConfigSpec.BooleanValue DESTROYS_MEDIA;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER) {
        COMMON_BUILDER.comment(MACHINE_DESCRIPTION).push(MACHINE);

        DISABLED = ConfigHelper.getMachineDisabled(COMMON_BUILDER);

        WATER_TRANSFER = COMMON_BUILDER.comment("Water Transfer Rate").defineInRange("transferRate", 100, 1,
                Integer.MAX_VALUE);

        WATER_USAGE = COMMON_BUILDER.comment("Water Usage Rate").defineInRange("waterUsage", 10, 1,
                Integer.MAX_VALUE);

        ENERGY_USAGE = COMMON_BUILDER.comment("Energy Usage Rate").defineInRange("energyUsage", 100, 1,
                Integer.MAX_VALUE);

        LAVA_BOOST = COMMON_BUILDER.comment("Boost to Netherwart growth when bed contains lava").defineInRange("lavaBoost", 25, 1,
                Integer.MAX_VALUE);

        REQUIRES_ENERGY = COMMON_BUILDER.comment("Grow Beds require energy").define("requiresEnergy", false);
        DESTROYS_MEDIA = COMMON_BUILDER.comment("Grow Beds destroy media").define("destroysMedia", false);

        COMMON_BUILDER.pop();
    }
}