package io.moonman.emergingtechnology.config.hydroponics.diffuser.nozzle;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModulePreciseNozzle {

    @Name("Nozzle - Range Multiplier")
    @Config.Comment("This value will be multiplied by the base range set in the Diffuser configuration")
    @RangeInt(min = 1, max = 10)
    public int rangeMultiplier = 1;

    @Name("Nozzle - Boost Multiplier")
    @Config.Comment("This value will be multiplied by the base growth probability set in the Diffuser configuration")
    @RangeInt(min = 1, max = 10)
    public int boostMultiplier = 2;
}