package io.moonman.emergingtechnology.config.hydroponics.light;

import io.moonman.emergingtechnology.config.ConfigHelper;
import net.minecraftforge.common.ForgeConfigSpec;

public class GrowLightConfiguration {

        public static final String MACHINE = "Grow Light";
        public static final String MACHINE_DESCRIPTION = "Grow Light Settings";

        public static ForgeConfigSpec.BooleanValue DISABLED;

        public static ForgeConfigSpec.IntValue RANGE;
        public static ForgeConfigSpec.IntValue DROPOFF;

        public static ForgeConfigSpec.IntValue RED_BULB_GROWTH;
        public static ForgeConfigSpec.IntValue GREEN_BULB_GROWTH;
        public static ForgeConfigSpec.IntValue BLUE_BULB_GROWTH;
        public static ForgeConfigSpec.IntValue UV_BULB_GROWTH;

        public static ForgeConfigSpec.IntValue BASE_ENERGY;

        public static ForgeConfigSpec.IntValue RED_BULB_ENERGY;
        public static ForgeConfigSpec.IntValue GREEN_BULB_ENERGY;
        public static ForgeConfigSpec.IntValue BLUE_BULB_ENERGY;
        public static ForgeConfigSpec.IntValue UV_BULB_ENERGY;

        public static void init(ForgeConfigSpec.Builder COMMON_BUILDER) {
                
                COMMON_BUILDER.comment(MACHINE_DESCRIPTION).push(MACHINE);

                DISABLED = ConfigHelper.getMachineDisabled(COMMON_BUILDER);

                RANGE = COMMON_BUILDER.comment("Grow Light - Range").defineInRange("range", 100, 2, Integer.MAX_VALUE);

                DROPOFF = COMMON_BUILDER.comment("Grow Light - Range Modifier Dropoff").defineInRange("dropoff", 100, 0,
                                Integer.MAX_VALUE);

                RED_BULB_GROWTH = COMMON_BUILDER.comment("Red Bulb Growth Modifier %").defineInRange("redBulbGrowth", 1,
                                1, Integer.MAX_VALUE);

                GREEN_BULB_GROWTH = COMMON_BUILDER.comment("Green Bulb Growth Modifier %")
                                .defineInRange("greenBulbGrowth", 1, 2, Integer.MAX_VALUE);

                BLUE_BULB_GROWTH = COMMON_BUILDER.comment("Blue Bulb Growth Modifier %").defineInRange("blueBulbGrowth",
                                1, 4, Integer.MAX_VALUE);

                UV_BULB_GROWTH = COMMON_BUILDER.comment("UV Bulb Growth Modifier %").defineInRange("uvBulbGrowth", 1, 8,
                                Integer.MAX_VALUE);

                BASE_ENERGY = COMMON_BUILDER.comment("Base Energy Usage").defineInRange("baseEnergy", 1, 100,
                                Integer.MAX_VALUE);

                RED_BULB_ENERGY = COMMON_BUILDER.comment("Red Bulb Energy Usage").defineInRange("redBulbEnergy", 1, 2,
                                Integer.MAX_VALUE);

                GREEN_BULB_ENERGY = COMMON_BUILDER.comment("Green Bulb Energy Usage").defineInRange("greenBulbEnergy",
                                3, 8, Integer.MAX_VALUE);

                BLUE_BULB_ENERGY = COMMON_BUILDER.comment("Blue Bulb Energy Usage").defineInRange("blueBulbEnergy", 1,
                                4, Integer.MAX_VALUE);

                UV_BULB_ENERGY = COMMON_BUILDER.comment("UV Bulb Energy Usage").defineInRange("uvBulbEnergy", 1, 5,
                                Integer.MAX_VALUE);

                COMMON_BUILDER.pop();
        }
}