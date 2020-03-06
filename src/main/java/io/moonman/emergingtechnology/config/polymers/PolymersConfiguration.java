package io.moonman.emergingtechnology.config.polymers;

import net.minecraftforge.common.ForgeConfigSpec;

public class PolymersConfiguration {
    
    public static final String CATEGORY = "Polymers";

    public static void initCommon(ForgeConfigSpec.Builder COMMON_BUILDER) {
        COMMON_BUILDER.comment(CATEGORY).push(CATEGORY);

        COMMON_BUILDER.pop();
    }
}