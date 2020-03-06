package io.moonman.emergingtechnology.config.synthetics;

import net.minecraftforge.common.ForgeConfigSpec;

public class SyntheticsConfiguration {
    
    public static final String CATEGORY = "Synthetics";

    public static void initCommon(ForgeConfigSpec.Builder COMMON_BUILDER) {
        COMMON_BUILDER.comment(CATEGORY).push(CATEGORY);

        COMMON_BUILDER.pop();
    }
}