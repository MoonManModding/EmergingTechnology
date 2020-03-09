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
    @RangeInt(min = 1, max = 1000)
    public int scrubberEnergyBaseUsage = 100;

    @Name("Scrubber - Water Usage")
    @Config.Comment("The amount of water used per tick.")
    @RangeInt(min = 1, max = 1000)
    public int scrubberWaterBaseUsage = 50;
    
    @Name("Scrubber - CO2 capture rate")
    @Config.Comment("The amount of CO2 captured per tick.")
    @RangeInt(min = 1, max = 1000)
    public int scrubberGasGenerated = 160;

    @Name("Scrubber - Operation Time")
    @Config.Comment("The time taken for the Scrubber to process Carbon Dioxide")
    @RangeInt(min = 1, max = 1000)
    public int scrubberBaseTimeTaken = 10;
    
    @Name("Scrubber - Transfer rate")
    @Config.Comment("The amount of Carbon Dioxide transferred to adjacent Diffusers per tick.")
    @RangeInt(min = 1, max = 1000)
    public int scrubberGasTransferRate = 160;
    
    @Name("Scrubber - Biochar Boost")
    @Config.Comment("The amount of Carbon Dioxide boosted when Biochar used")
    @RangeInt(min = 1, max = 1000)
    public int biocharBoostAmount = 100;
}