package io.moonman.emergingtechnology.config.synthetics;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class SyntheticsModuleBioreactor {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Tissue Bioreactor Energy Usage")
    @Config.Comment("How much energy the Tissue Bioreactor uses per tick.")
    @RangeInt(min = 0, max = 1000)
    public int bioreactorEnergyUsage = 35;
    
    @Name("Tissue Bioreactor Water Usage")
    @Config.Comment("How much water the Tissue Bioreactor uses per tick.")
    @RangeInt(min = 0, max = 1000)
    public int bioreactorWaterUsage = 25;

    @Name("Tissue Bioreactor Operation Time")
    @Config.Comment("How long the Tissue Bioreactor takes to create samples from syringes.")
    @RangeInt(min = 0, max = 1000)
    public int bioreactorBaseTimeTaken = 200;

}