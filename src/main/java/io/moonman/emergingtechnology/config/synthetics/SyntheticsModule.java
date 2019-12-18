package io.moonman.emergingtechnology.config.synthetics;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;

public class SyntheticsModule {

    @Name("Solar Cooker")
	@Comment("Configure the Solar Cooker here")
    public final SyntheticsModuleCooker COOKER = new SyntheticsModuleCooker();
}