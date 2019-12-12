package io.moonman.emergingtechnology.config.hydroponics.beds.media;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleClay {

    @Name("Clay Growth Modifier %")
    @Config.Comment("Probability of growth from clay medium per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthClayModifier = 8;

    @Name("Clay Fluid Usage")
    @Config.Comment("Fluid used by Clay on successful growth per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthClayFluidUsage = 1;

}