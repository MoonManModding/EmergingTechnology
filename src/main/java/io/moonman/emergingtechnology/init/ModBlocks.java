package io.moonman.emergingtechnology.init;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.block.blocks.BiocharBlock;
import io.moonman.emergingtechnology.block.blocks.ClearPlasticBlock;
import io.moonman.emergingtechnology.block.blocks.Frame;
import io.moonman.emergingtechnology.block.blocks.Ladder;
import io.moonman.emergingtechnology.block.blocks.MachineCase;
import io.moonman.emergingtechnology.block.blocks.PlasticBlock;
import io.moonman.emergingtechnology.block.blocks.ShreddedPlantBlock;
import io.moonman.emergingtechnology.block.blocks.ShreddedPlasticBlock;
import io.moonman.emergingtechnology.block.blocks.ShreddedStarchBlock;
import io.moonman.emergingtechnology.fluid.fluidblocks.CarbonDioxideBlock;
import io.moonman.emergingtechnology.fluid.fluidblocks.NutrientBlock;
import io.moonman.emergingtechnology.machines.battery.Battery;
import io.moonman.emergingtechnology.machines.biomass.BiomassGenerator;
import io.moonman.emergingtechnology.machines.bioreactor.Bioreactor;
import io.moonman.emergingtechnology.machines.collector.Collector;
import io.moonman.emergingtechnology.machines.cooker.Cooker;
import io.moonman.emergingtechnology.machines.diffuser.Diffuser;
import io.moonman.emergingtechnology.machines.fabricator.Fabricator;
import io.moonman.emergingtechnology.machines.filler.Filler;
import io.moonman.emergingtechnology.machines.harvester.Harvester;
import io.moonman.emergingtechnology.machines.hydroponic.Hydroponic;
import io.moonman.emergingtechnology.machines.injector.Injector;
import io.moonman.emergingtechnology.machines.light.Light;
import io.moonman.emergingtechnology.machines.piezoelectric.Piezoelectric;
import io.moonman.emergingtechnology.machines.algaebioreactor.AlgaeBioreactor;
import io.moonman.emergingtechnology.machines.processor.Processor;
import io.moonman.emergingtechnology.machines.scaffolder.Scaffolder;
import io.moonman.emergingtechnology.machines.scrubber.Scrubber;
import io.moonman.emergingtechnology.machines.shredder.Shredder;
import io.moonman.emergingtechnology.machines.solar.Solar;
import io.moonman.emergingtechnology.machines.solarglass.SolarGlass;
import io.moonman.emergingtechnology.machines.tidal.TidalGenerator;
import io.moonman.emergingtechnology.machines.wind.Wind;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

/**
 * All blocks from Emerging Technology
 */
@ObjectHolder(EmergingTechnology.MODID)
public class ModBlocks {

	// region Hydroponics Blocks
	public static final Hydroponic hydroponic = null;
	public static final Light light = null;
	public static final Frame frame = null;
	public static final Harvester harvester = null;
	public static final Filler filler = null;
	public static final Scrubber scrubber = null;
	public static final Diffuser diffuser = null;
	public static final Injector injector = null;
	// endregion

	// region Polymers Blocks
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
	// endregion

	// region Synthetics Blocks
	public static final Cooker cooker = null;
	public static final Bioreactor bioreactor = null;
	public static final Scaffolder scaffolder = null;
	public static final AlgaeBioreactor algaebioreactor = null;
	// endregion

	// region Electrics Blocks
	public static final Piezoelectric piezoelectric = null;
	public static final TidalGenerator tidalgenerator = null;
	public static final Solar solar = null;
	public static final SolarGlass solarglass = null;
	public static final Wind wind = null;
	public static final Battery battery = null;
	public static final BiomassGenerator biomassgenerator = null;
	public static final BiocharBlock biocharblock = null;
	// endregion

	// region Fluid Blocks
	public static final CarbonDioxideBlock carbondioxideblock = null;
	public static final NutrientBlock nutrientblock = null;
	// endregion

	public static Block[] getBlocks() {
		Block[] blocks = { ModBlocks.hydroponic, ModBlocks.harvester, ModBlocks.filler, ModBlocks.scrubber,
				ModBlocks.diffuser, ModBlocks.injector, ModBlocks.light, ModBlocks.processor, ModBlocks.shredder, ModBlocks.fabricator,
				ModBlocks.collector, ModBlocks.cooker, ModBlocks.bioreactor, ModBlocks.scaffolder,
				ModBlocks.algaebioreactor, ModBlocks.piezoelectric, ModBlocks.tidalgenerator,
				ModBlocks.biomassgenerator, ModBlocks.solar, ModBlocks.solarglass, ModBlocks.wind, ModBlocks.battery,
				ModBlocks.ladder, ModBlocks.plasticblock, ModBlocks.frame, ModBlocks.clearplasticblock,
				ModBlocks.machinecase, ModBlocks.shreddedplantblock, ModBlocks.shreddedplasticblock,
				ModBlocks.shreddedstarchblock, ModBlocks.biocharblock,  ModBlocks.nutrientblock, ModBlocks.carbondioxideblock};

		return blocks;
	}

	public static Block[] generateBlocks() {
		Block[] blocks = { new Hydroponic(), new Harvester(), new Filler(), new Scrubber(), new Diffuser(), new Injector(), new Light(),
				new Frame(), new Processor(), new Shredder(), new PlasticBlock(), new ClearPlasticBlock(),
				new MachineCase(), new Fabricator(), new Collector(), new Cooker(), new Scaffolder(), new Bioreactor(),
				new AlgaeBioreactor(), new Ladder(), new Piezoelectric(), new TidalGenerator(), new BiomassGenerator(),
				new Solar(), new SolarGlass(), new Wind(), new Battery(), new BiocharBlock(),
				new ShreddedPlasticBlock(), new ShreddedPlantBlock(), new ShreddedStarchBlock(), new NutrientBlock(),
				new CarbonDioxideBlock() };

		return blocks;
	}
}