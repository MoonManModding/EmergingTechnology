package io.moonman.emergingtechnology.config.hydroponics;

import io.moonman.emergingtechnology.config.hydroponics.aquaponic.HydroponicsModuleAquaponic;
import io.moonman.emergingtechnology.config.hydroponics.beds.HydroponicsModuleGrowBed;
import io.moonman.emergingtechnology.config.hydroponics.diffuser.HydroponicsModuleDiffuser;
import io.moonman.emergingtechnology.config.hydroponics.filler.HydroponicsModuleFiller;
import io.moonman.emergingtechnology.config.hydroponics.harvester.HydroponicsModuleHarvester;
import io.moonman.emergingtechnology.config.hydroponics.injector.HydroponicsModuleInjector;
import io.moonman.emergingtechnology.config.hydroponics.lights.HydroponicsModuleGrowLight;
import io.moonman.emergingtechnology.config.hydroponics.scrubber.HydroponicsModuleScrubber;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;

public class HydroponicsModule {

    @Name("Grow Bed")
	@LangKey("config.emergingtechnology.hydroponics.growbed.title")
    public final HydroponicsModuleGrowBed GROWBED = new HydroponicsModuleGrowBed();
    
    @Name("Grow Light")
	@LangKey("config.emergingtechnology.hydroponics.growlight.title")
    public final HydroponicsModuleGrowLight GROWLIGHT = new HydroponicsModuleGrowLight();
    
    @Name("Harvester")
	@LangKey("config.emergingtechnology.hydroponics.harvester.title")
    public final HydroponicsModuleHarvester HARVESTER = new HydroponicsModuleHarvester();
    
    @Name("Filler")
	@LangKey("config.emergingtechnology.hydroponics.filler.title")
    public final HydroponicsModuleFiller FILLER = new HydroponicsModuleFiller();
    
    @Name("Scrubber")
	@LangKey("config.emergingtechnology.hydroponics.scrubber.title")
    public final HydroponicsModuleScrubber SCRUBBER = new HydroponicsModuleScrubber();
    
    @Name("Diffuser")
	@LangKey("config.emergingtechnology.hydroponics.diffuser.title")
    public final HydroponicsModuleDiffuser DIFFUSER = new HydroponicsModuleDiffuser();
    
    @Name("Injector")
	@LangKey("config.emergingtechnology.hydroponics.injector.title")
    public final HydroponicsModuleInjector INJECTOR = new HydroponicsModuleInjector();
    
    @Name("Aquaponic")
	@LangKey("config.emergingtechnology.hydroponics.aquaponic.title")
	public final HydroponicsModuleAquaponic AQUAPONIC = new HydroponicsModuleAquaponic();
}