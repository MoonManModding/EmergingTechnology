package io.moonman.emergingtechnology.config.hydroponics;

import io.moonman.emergingtechnology.config.hydroponics.filler.FillerConfiguration;
import net.minecraftforge.common.ForgeConfigSpec;

public class HydroponicsConfiguration {
    
    public static final String CATEGORY = "Hydroponics";

    public static void initCommon(ForgeConfigSpec.Builder COMMON_BUILDER) {
        COMMON_BUILDER.comment(CATEGORY).push(CATEGORY);

        FillerConfiguration.init(COMMON_BUILDER);

        COMMON_BUILDER.pop();
    }
}