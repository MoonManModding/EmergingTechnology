package io.moonman.emergingtechnology.config.hydroponics.scrubber;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleScrubber {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Scrubber - Energy Usage")
    @Config.Comment("The amount of energy used per tick.")
    @RangeInt(min = 1, max = Integer.MAX_VALUE)
    public int scrubberEnergyBaseUsage = 75;

    @Name("Scrubber - Water Usage")
    @Config.Comment("The amount of water used per tick.")
    @RangeInt(min = 1, max = Integer.MAX_VALUE)
    public int scrubberWaterBaseUsage = 75;
    
    @Name("Scrubber - CO2 capture rate")
    @Config.Comment("The amount of CO2 captured per tick.")
    @RangeInt(min = 1, max = Integer.MAX_VALUE)
    public int scrubberGasGenerated = 50;

    @Name("Scrubber - Operation Time")
    @Config.Comment("The time taken for the Scrubber to process Carbon Dioxide")
    @RangeInt(min = 1, max = Integer.MAX_VALUE)
    public int scrubberBaseTimeTaken = 100;
    
    @Name("Scrubber - Biochar Boost")
    @Config.Comment("The amount of Carbon Dioxide boosted when Biochar used")
    @RangeInt(min = 1, max = Integer.MAX_VALUE)
    public int biocharBoostAmount = 100;
}