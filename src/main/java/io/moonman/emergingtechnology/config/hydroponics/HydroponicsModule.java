package io.moonman.emergingtechnology.config.hydroponics;

import io.moonman.emergingtechnology.config.hydroponics.beds.HydroponicsModuleGrowBed;
import io.moonman.emergingtechnology.config.hydroponics.filler.HydroponicsModuleFiller;
import io.moonman.emergingtechnology.config.hydroponics.harvester.HydroponicsModuleHarvester;
import io.moonman.emergingtechnology.config.hydroponics.lights.HydroponicsModuleGrowLight;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;

public class HydroponicsModule {

    @Name("Grow Beds")
	@Comment("Configure Grow Beds here")
    public final HydroponicsModuleGrowBed GROWBED = new HydroponicsModuleGrowBed();
    
    @Name("Grow Lights")
	@Comment("Configure Grow Lights here")
    public final HydroponicsModuleGrowLight GROWLIGHT = new HydroponicsModuleGrowLight();
    
    @Name("Harvester")
	@Comment("Configure Harvester here")
    public final HydroponicsModuleHarvester HARVESTER = new HydroponicsModuleHarvester();
    
    @Name("Filler")
	@Comment("Configure Filler here")
	public final HydroponicsModuleFiller FILLER = new HydroponicsModuleFiller();
}