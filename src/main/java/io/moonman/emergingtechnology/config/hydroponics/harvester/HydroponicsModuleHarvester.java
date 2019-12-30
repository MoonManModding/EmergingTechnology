package io.moonman.emergingtechnology.config.hydroponics.harvester;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleHarvester {

    @Name("Harvester Operation Energy Cost")
    @Config.Comment("How much energy the Harvester uses when harvesting per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int harvesterEnergyBaseUsage = 50;

    @Name("Harvester - Disable animations")
    @Config.Comment("Disable to improve performance. Only necessary with a significant number of Harvesters")
    public boolean harvesterDisableAnimations = false;
}