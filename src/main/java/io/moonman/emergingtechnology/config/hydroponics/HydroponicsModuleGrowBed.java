package io.moonman.emergingtechnology.config.hydroponics;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleGrowBed {

    @Name("Grow Bed - Water Usage")
    @Config.Comment("The amount of water used by a grow bed per cycle (~10 ticks).")
    @RangeInt(min = 1, max = 100)
    public int growBedWaterUsePerCycle = 1;

    @Name("Grow Bed - Water Transfer Rate")
    @Config.Comment("The amount of water transferred to other beds by a grow bed per cycle (~10 ticks).")
    @RangeInt(min = 1, max = 100)
    public int growBedWaterTransferRate = 10;

    @Name("Dirt Growth Modifier %")
    @Config.Comment("Probability of growth from dirt medium per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthDirtModifier = 1;

    @Name("Sand Growth Modifier %")
    @Config.Comment("Probability of growth from sand medium per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthSandModifier = 4;

    @Name("Gravel Growth Modifier %")
    @Config.Comment("Probability of growth from gravel medium per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthGravelModifier = 2;

    @Name("Clay Growth Modifier %")
    @Config.Comment("Probability of growth from clay medium per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthClayModifier = 8;
}