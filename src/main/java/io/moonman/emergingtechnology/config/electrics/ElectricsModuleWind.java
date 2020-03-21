package io.moonman.emergingtechnology.config.electrics;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class ElectricsModuleWind {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Energy Generated")
    @Config.Comment("How much energy the Wind Generator generates when in wind.")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int energyGenerated = 35;
    
    @Name("Minimum Required Surrounding Air")
    @Config.Comment("Minimum required surrounding air blocks for Wind Generator to function in a 5 x 5 grid (not including self)")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int minimumAirBlocks = 10;

    @Name("Maximum Optimal Height")
    @Config.Comment("The highest point of the Wind Generator's optimal height")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int maxOptimalHeight = 150;

    @Name("Minimum Optimal Height")
    @Config.Comment("The lowest point of the Wind Generator's optimal height")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int minOptimalHeight = 75;
}