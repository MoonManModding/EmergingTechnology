package io.moonman.emergingtechnology.config.hydroponics.beds.media;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleSand {

    @Name("Sand Growth Modifier %")
    @Config.Comment("Probability of growth from sand medium per tick.")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int growthSandModifier = 4;

    @Name("Sand Fluid Usage")
    @Config.Comment("Fluid used by Sand on successful growth per tick.")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int growthSandFluidUsage = 10;

    @Name("Sand Destroy Probability")
    @Config.Comment("Probability of Sand being destroyed (out of 1000) during use in Grow Bed per tick.")
    @RangeInt(min = 0, max = 999)
    public int destroyProbability = 2;
}