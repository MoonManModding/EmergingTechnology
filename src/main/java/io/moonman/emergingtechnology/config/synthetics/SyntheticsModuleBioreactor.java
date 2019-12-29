package io.moonman.emergingtechnology.config.synthetics;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class SyntheticsModuleBioreactor {

    @Name("Tissue Bioreactor Energy Usage")
    @Config.Comment("How much energy the Tissue Bioreactor uses per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 1000)
    public int bioreactorEnergyUsage = 100;
    
    @Name("Tissue Bioreactor Water Usage")
    @Config.Comment("How much water the Tissue Bioreactor uses per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 1000)
    public int bioreactorWaterUsage = 100;

    @Name("Tissue Bioreactor Operation Time")
    @Config.Comment("How long the Tissue Bioreactor takes to create samples from syringes.")
    @RangeInt(min = 0, max = 1000)
    public int bioreactorBaseTimeTaken = 200;

}