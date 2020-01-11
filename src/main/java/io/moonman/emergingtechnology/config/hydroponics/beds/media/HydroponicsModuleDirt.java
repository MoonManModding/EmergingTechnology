package io.moonman.emergingtechnology.config.hydroponics.beds.media;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleDirt {

    @Name("Dirt Growth Modifier %")
    @Config.Comment("Probability of growth from dirt medium per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthDirtModifier = 1;

    @Name("Dirt Fluid Usage")
    @Config.Comment("Fluid used by Dirt on successful growth per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthDirtFluidUsage = 1;

    @Name("Dirt Destroy Probability")
    @Config.Comment("Probability of Dirt being destroyed (out of 1000) during use in Grow Bed per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 999)
    public int destroyProbability = 1;
}