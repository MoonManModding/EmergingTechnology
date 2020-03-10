package io.moonman.emergingtechnology.config.electrics;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class ElectricsModuleTidalGenerator {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Energy Generated")
    @Config.Comment("How much energy the Tidal Generator generates when underwater.")
    @RangeInt(min = 0, max = 10000)
    public int tidalEnergyGenerated = 55;
    
    @Name("Minimum Required Surrounding Water")
    @Config.Comment("Minimum required surrounding water blocks for Tidal Generator to function in a 5 x 5 grid (not including self)")
    @RangeInt(min = 0, max = 100)
    public int minimumWaterBlocks = 15;

    @Name("Maximum Optimal Depth")
    @Config.Comment("The shallowest point of the Tidal Generator's optimal depth")
    @RangeInt(min = 0, max = 100)
    public int maxOptimalDepth = 55;

    @Name("Minimum Optimal Depth")
    @Config.Comment("The deepest point of the Tidal Generator's optimal depth")
    @RangeInt(min = 0, max = 100)
    public int minOptimalDepth = 45;

    @Name("Disable Turbine Biome Requirement")
    @Config.Comment("Set this to true and the Turbine will work in all Biomes")
    public boolean biomeRequirementDisabled = false;
}