package io.moonman.emergingtechnology.config.synthetics;

import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;

public class SyntheticsModule {

    @Name("Solar Cooker")
	@LangKey("config.emergingtechnology.synthetics.cooker.title")
    public final SyntheticsModuleCooker COOKER = new SyntheticsModuleCooker();

    @Name("Tissue Bioreactor")
	@LangKey("config.emergingtechnology.synthetics.bioreactor.title")
    public final SyntheticsModuleBioreactor BIOREACTOR = new SyntheticsModuleBioreactor();

    @Name("Tissue Scaffolder")
	@LangKey("config.emergingtechnology.synthetics.scaffolder.title")
    public final SyntheticsModuleScaffolder SCAFFOLDER = new SyntheticsModuleScaffolder();
}