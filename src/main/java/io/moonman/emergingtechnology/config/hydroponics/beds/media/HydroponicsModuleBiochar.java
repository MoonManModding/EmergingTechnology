package io.moonman.emergingtechnology.config.hydroponics.beds.media;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleBiochar {

    @Name("Biochar Growth Modifier %")
    @Config.Comment("Probability of growth from Biochar medium per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthBiocharModifier = 10;

    @Name("Biochar Fluid Usage")
    @Config.Comment("Fluid used by Biochar on successful growth per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthBiocharFluidUsage = 2;

    @Name("Biochar Destroy Probability")
    @Config.Comment("Probability of Biochar being destroyed (out of 1000) during use in Grow Bed per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 999)
    public int destroyProbability = 15;

}