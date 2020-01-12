package io.moonman.emergingtechnology.config.electrics;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class ElectricsModuleSolar {

    @Name("Solar Energy Generated")
    @Config.Comment("How much energy the Solar tile generates when in sunshine.")
    @RangeInt(min = 0, max = 1000)
    public int solarEnergyGenerated = 150;
}