package io.moonman.emergingtechnology.config.synthetics;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;

public class SyntheticsModule {

    @Name("Solar Cooker")
	@Comment("Configure the Solar Cooker here")
    public final SyntheticsModuleCooker COOKER = new SyntheticsModuleCooker();

    @Name("Tissue Bioreactor")
	@Comment("Configure the Tissue Bioreactor here")
    public final SyntheticsModuleBioreactor BIOREACTOR = new SyntheticsModuleBioreactor();

    @Name("Tissue Scaffolder")
	@Comment("Configure the Tissue Scaffolder here")
    public final SyntheticsModuleScaffolder SCAFFOLDER = new SyntheticsModuleScaffolder();
}