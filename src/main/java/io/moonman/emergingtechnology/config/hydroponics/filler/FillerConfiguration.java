package io.moonman.emergingtechnology.config.hydroponics.filler;

import io.moonman.emergingtechnology.config.ConfigHelper;
import net.minecraftforge.common.ForgeConfigSpec;

public class FillerConfiguration {
    
    public static final String MACHINE = "Filler";
    public static final String MACHINE_DESCRIPTION = "Filler Settings";

    public static ForgeConfigSpec.BooleanValue DISABLED;

    public static ForgeConfigSpec.IntValue WATER_TRANSFER;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER) {
        COMMON_BUILDER.comment(MACHINE_DESCRIPTION).push(MACHINE);

        DISABLED = ConfigHelper.getMachineDisabled(COMMON_BUILDER);

        WATER_TRANSFER = COMMON_BUILDER.comment("Water Transfer Rate")
                .defineInRange("transferRate", 1000, 0, Integer.MAX_VALUE);

        COMMON_BUILDER.pop();
    }
}