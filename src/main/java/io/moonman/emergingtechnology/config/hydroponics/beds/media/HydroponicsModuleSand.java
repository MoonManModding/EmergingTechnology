package io.moonman.emergingtechnology.config.hydroponics.beds.media;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleSand {

    @Name("Sand Growth Modifier %")
    @Config.Comment("Probability of growth from sand medium per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthSandModifier = 4;

    @Name("Sand Fluid Usage")
    @Config.Comment("Fluid used by Sand on successful growth per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthSandFluidUsage = 1;

    @Name("Sand Destroy Probability")
    @Config.Comment("Probability of Sand being destroyed (out of 1000) during use in Grow Bed per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 999)
    public int destroyProbability = 1;
}