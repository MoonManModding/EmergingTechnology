package io.moonman.emergingtechnology.config.electrics;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;

public class ElectricsModule {

    @Name("Piezoelectric Tile")
	@Comment("Configure the Piezoelectric Tile here")
    public final ElectricsModulePiezoelectric PIEZOELECTRIC = new ElectricsModulePiezoelectric();
}