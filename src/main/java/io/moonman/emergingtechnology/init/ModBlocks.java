package io.moonman.emergingtechnology.init;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.block.blocks.Frame;
import io.moonman.emergingtechnology.machines.hydroponic.Hydroponic;
import io.moonman.emergingtechnology.machines.light.Light;
import io.moonman.emergingtechnology.machines.processor.Processor;
import io.moonman.emergingtechnology.machines.shredder.Shredder;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
 
/**
All blocks from Emerging Technology
*/
@ObjectHolder(EmergingTechnology.MODID)
public class ModBlocks {

	//region Hydroponics Blocks
	public static final Hydroponic hydroponic = null;
	public static final Light light = null;
	public static final Frame frame = null;
	//endregion

	//region Polymers Blocks
	public static final Processor processor = null;
	public static final Shredder shredder = null;
	//endregion
}