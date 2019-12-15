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

    @Name("Fabricator")
	@Comment("Configure the Fabricator here")
    public final PolymersModuleFabricator FABRICATOR = new PolymersModuleFabricator();
}