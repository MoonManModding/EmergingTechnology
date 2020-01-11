package io.moonman.emergingtechnology.config.hydroponics.beds.media;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleGravel {

    @Name("Gravel Growth Modifier %")
    @Config.Comment("Probability of growth from gravel medium per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthGravelModifier = 2;

    @Name("Gravel Fluid Usage")
    @Config.Comment("Fluid used by Gravel on successful growth per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthGravelFluidUsage = 1;

    @Name("Gravel Destroy Probability")
    @Config.Comment("Probability of Gravel being destroyed (out of 1000) during use in Grow Bed per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 999)
    public int destroyProbability = 1;
}