package io.moonman.emergingtechnology.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class ConfigHelper {

    public static BooleanValue getMachineDisabled(ForgeConfigSpec.Builder COMMON_BUILDER) {
        return COMMON_BUILDER.comment("Disable Machine")
                .define("disabled", false);
    }

}