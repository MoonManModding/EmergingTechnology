package io.moonman.emergingtechnology.config.electrics;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;

public class ElectricsModule {

    @Name("Piezoelectric Tile")
	@Comment("Configure the Piezoelectric Tile here")
    public final ElectricsModulePiezoelectric PIEZOELECTRIC = new ElectricsModulePiezoelectric();

    @Name("Tidal Generator")
	@Comment("Configure the Tidal Generator here")
    public final ElectricsModuleTidalGenerator TIDALGENERATOR = new ElectricsModuleTidalGenerator();

    @Name("Solar Generator")
	@Comment("Configure the Solar Generator here")
    public final ElectricsModuleSolar SOLAR = new ElectricsModuleSolar();

    @Name("Biomass Generator")
	@Comment("Configure the Biomass Generator here")
    public final ElectricsModuleBiomassGenerator BIOMASSGENERATOR = new ElectricsModuleBiomassGenerator();
}