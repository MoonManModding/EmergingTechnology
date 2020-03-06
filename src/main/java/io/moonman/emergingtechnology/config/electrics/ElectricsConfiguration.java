package io.moonman.emergingtechnology.config.electrics;

import net.minecraftforge.common.ForgeConfigSpec;

public class ElectricsConfiguration {
    
    public static final String CATEGORY = "Electrics";

    public static void initCommon(ForgeConfigSpec.Builder COMMON_BUILDER) {
        COMMON_BUILDER.comment(CATEGORY).push(CATEGORY);

        COMMON_BUILDER.pop();
    }

}