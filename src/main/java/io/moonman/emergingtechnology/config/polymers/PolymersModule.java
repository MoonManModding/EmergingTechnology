package io.moonman.emergingtechnology.config.polymers;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;

public class PolymersModule {

    @Name("Shredder")
	@Comment("Configure the Shredder here")
    public final PolymersModuleShredder SHREDDER = new PolymersModuleShredder();

    @Name("Processor")
	@Comment("Configure the Processor here")
    public final PolymersModuleProcessor PROCESSOR = new PolymersModuleProcessor();
    
    // @Name("Grow Lights")
	// @Comment("Configure Grow Lights here")
	// public final HydroponicsModuleGrowLight GROWLIGHT = new HydroponicsModuleGrowLight();
}