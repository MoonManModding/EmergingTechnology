package io.moonman.emergingtechnology.config.hydroponics.harvester;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleHarvester {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Harvester Operation Energy Cost")
    @Config.Comment("How much energy the Harvester uses when harvesting per tick.")
    @RangeInt(min = 0, max = 1000)
    public int harvesterEnergyBaseUsage = 45;

    @Name("Harvester Energy Transfer Rate")
    @Config.Comment("How much energy the Harvester uses when harvesting per tick.")
    @RangeInt(min = 0, max = 1000)
    public int harvesterEnergyTransferRate = 450;

    @Name("Harvester - Disable animations")
    @Config.Comment("Disable to improve performance. Only necessary with a significant number of Harvesters")
    public boolean harvesterDisableAnimations = false;
}