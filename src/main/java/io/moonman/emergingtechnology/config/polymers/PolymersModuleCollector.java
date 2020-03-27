package io.moonman.emergingtechnology.config.polymers;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class PolymersModuleCollector {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Collector Plastic Recovered Probability")
    @Config.Comment("The probability of recovering waste items out of 1000 (1 = 0.1%) per tick.")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int plasticRecoveryProbability = 1;

    @Name("Collector Minimum Required Surrounding Water")
    @Config.Comment("Minimum required surrounding water blocks for Collector to function in a 5 x 5 grid (not including self)")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int minimumWaterBlocks = 15;

    @Name("Disable Collector Biome Requirement")
    @Config.Comment("Set this to true and the Collector will work in all Biomes")
    public boolean biomeRequirementDisabled = false;
}