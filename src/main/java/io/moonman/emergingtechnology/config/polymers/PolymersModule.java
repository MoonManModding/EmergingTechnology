package io.moonman.emergingtechnology.config.polymers;

import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;

public class PolymersModule {

    @Name("Shredder")
	@LangKey("config.emergingtechnology.polymers.shredder.title")
    public final PolymersModuleShredder SHREDDER = new PolymersModuleShredder();

    @Name("Processor")
	@LangKey("config.emergingtechnology.polymers.processor.title")
    public final PolymersModuleProcessor PROCESSOR = new PolymersModuleProcessor();

    @Name("Fabricator")
	@LangKey("config.emergingtechnology.polymers.fabricator.title")
    public final PolymersModuleFabricator FABRICATOR = new PolymersModuleFabricator();

    @Name("Collector")
	@LangKey("config.emergingtechnology.polymers.collector.title")
    public final PolymersModuleCollector COLLECTOR = new PolymersModuleCollector();
}