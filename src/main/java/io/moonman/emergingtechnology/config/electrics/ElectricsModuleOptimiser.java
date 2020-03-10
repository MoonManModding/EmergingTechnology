package io.moonman.emergingtechnology.config.electrics;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class ElectricsModuleOptimiser {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Energy usage")
    @Config.Comment("How much energy the Optimiser uses per tick.")
    @RangeInt(min = 0, max = 10000)
    public int energyUsage = 25;

    @Name("Water usage")
    @Config.Comment("How much water the Optimiser uses per tick.")
    @RangeInt(min = 0, max = 10000)
    public int waterUsage = 15;
}