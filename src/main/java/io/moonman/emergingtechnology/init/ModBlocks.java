package io.moonman.emergingtechnology.init;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.block.blocks.ClearPlasticBlock;
import io.moonman.emergingtechnology.block.blocks.Frame;
import io.moonman.emergingtechnology.block.blocks.Ladder;
import io.moonman.emergingtechnology.block.blocks.MachineCase;
import io.moonman.emergingtechnology.block.blocks.PlasticBlock;
import io.moonman.emergingtechnology.block.blocks.ShreddedPlantBlock;
import io.moonman.emergingtechnology.block.blocks.ShreddedPlasticBlock;
import io.moonman.emergingtechnology.block.blocks.ShreddedStarchBlock;
import io.moonman.emergingtechnology.machines.biomass.BiomassGenerator;
import io.moonman.emergingtechnology.machines.bioreactor.Bioreactor;
import io.moonman.emergingtechnology.machines.collector.Collector;
import io.moonman.emergingtechnology.machines.cooker.Cooker;
import io.moonman.emergingtechnology.machines.fabricator.Fabricator;
import io.moonman.emergingtechnology.machines.filler.Filler;
import io.moonman.emergingtechnology.machines.harvester.Harvester;
import io.moonman.emergingtechnology.machines.hydroponic.Hydroponic;
import io.moonman.emergingtechnology.machines.light.Light;
import io.moonman.emergingtechnology.machines.piezoelectric.Piezoelectric;
import io.moonman.emergingtechnology.machines.processor.Processor;
import io.moonman.emergingtechnology.machines.scaffolder.Scaffolder;
import io.moonman.emergingtechnology.machines.shredder.Shredder;
import io.moonman.emergingtechnology.machines.solar.Solar;
import io.moonman.emergingtechnology.machines.tidal.TidalGenerator;
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
	public static final Harvester harvester = null;
	public static final Filler filler = null;
	//endregion

	//region Polymers Blocks
	public static final Processor processor = null;
	public static final Shredder shredder = null;
	public static final PlasticBlock plasticblock = null;
	public static final ClearPlasticBlock clearplasticblock = null;
	public static final MachineCase machinecase = null;
	public static final Fabricator fabricator = null;
	public static final Collector collector = null;
	public static final Ladder ladder = null;

	public static final ShreddedPlantBlock shreddedplantblock = null;
	public static final ShreddedPlasticBlock shreddedplasticblock = null;
	public static final ShreddedStarchBlock shreddedstarchblock = null;
	//endregion

	//region Synthetics Blocks
	public static final Cooker cooker = null;
	public static final Bioreactor bioreactor = null;
	public static final Scaffolder scaffolder = null;
	//endregion

	//region Electrics Blocks
	public static final Piezoelectric piezoelectric = null;
	public static final TidalGenerator tidalgenerator = null;
	public static final Solar solar = null;
	public static final BiomassGenerator biomassgenerator = null;
	//endregion
}