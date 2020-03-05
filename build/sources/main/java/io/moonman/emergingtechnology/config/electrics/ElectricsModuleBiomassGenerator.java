package io.moonman.emergingtechnology.config.electrics;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class ElectricsModuleBiomassGenerator {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Biomass Generator Energy Generated")
    @Config.Comment("How much energy the Biomass Generator generates per biomass item.")
    @RangeInt(min = 0, max = 10000)
    public int biomassEnergyGenerated = 250;

    @Name("Biomass Generator Process Time")
    @Config.Comment("How long the biomass generator takes to process a biomass item into energy")
    @RangeInt(min = 0, max = 100)
    public int baseTimeTaken = 25;
}