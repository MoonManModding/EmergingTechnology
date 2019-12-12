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

}