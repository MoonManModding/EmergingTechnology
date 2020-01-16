package io.moonman.emergingtechnology.config.electrics;

import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;

public class ElectricsModule {

    @Name("Piezoelectric Tile")
	@LangKey("config.emergingtechnology.electrics.piezoelectric.title")
    public final ElectricsModulePiezoelectric PIEZOELECTRIC = new ElectricsModulePiezoelectric();

    @Name("Tidal Generator")
	@LangKey("config.emergingtechnology.electrics.tidal.title")
    public final ElectricsModuleTidalGenerator TIDALGENERATOR = new ElectricsModuleTidalGenerator();

    @Name("Solar Generator")
	@LangKey("config.emergingtechnology.electrics.solar.title")
    public final ElectricsModuleSolar SOLAR = new ElectricsModuleSolar();

    @Name("Biomass Generator")
	@LangKey("config.emergingtechnology.electrics.biomass.title")
    public final ElectricsModuleBiomassGenerator BIOMASSGENERATOR = new ElectricsModuleBiomassGenerator();

    @Name("Battery")
	@LangKey("config.emergingtechnology.electrics.battery.title")
    public final ElectricsModuleBattery BATTERY = new ElectricsModuleBattery();
}