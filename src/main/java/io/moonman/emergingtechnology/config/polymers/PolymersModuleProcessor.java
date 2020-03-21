package io.moonman.emergingtechnology.config.polymers;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class PolymersModuleProcessor {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Processor Operation Energy Cost")
    @Config.Comment("How much energy the Processor uses when processing per tick.")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int processorEnergyBaseUsage = 15;

    @Name("Processor Operation Water Cost")
    @Config.Comment("How much water the Processor uses when processing per tick.")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int processorWaterBaseUsage = 15;

    @Name("Processor Operation Time")
    @Config.Comment("How long the Processor takes to process items.")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int processorBaseTimeTaken = 20;
}