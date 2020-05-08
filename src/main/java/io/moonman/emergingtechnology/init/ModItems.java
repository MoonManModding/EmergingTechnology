package io.moonman.emergingtechnology.init;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import io.moonman.emergingtechnology.item.electrics.Biochar;
import io.moonman.emergingtechnology.item.electrics.Biomass;
import io.moonman.emergingtechnology.item.electrics.circuits.Circuit;
import io.moonman.emergingtechnology.item.electrics.circuits.CircuitAdvanced;
import io.moonman.emergingtechnology.item.electrics.circuits.CircuitBasic;
import io.moonman.emergingtechnology.item.electrics.circuits.CircuitSuperior;
import io.moonman.emergingtechnology.item.hydroponics.Fertilizer;
import io.moonman.emergingtechnology.item.hydroponics.bulbs.BlueBulb;
import io.moonman.emergingtechnology.item.hydroponics.bulbs.GreenBulb;
import io.moonman.emergingtechnology.item.hydroponics.bulbs.PurpleBulb;
import io.moonman.emergingtechnology.item.hydroponics.bulbs.RedBulb;
import io.moonman.emergingtechnology.item.hydroponics.nozzles.NozzleComponent;
import io.moonman.emergingtechnology.item.hydroponics.nozzles.NozzleLong;
import io.moonman.emergingtechnology.item.hydroponics.nozzles.NozzlePrecise;
import io.moonman.emergingtechnology.item.hydroponics.nozzles.NozzleSmart;
import io.moonman.emergingtechnology.item.polymers.PlasticRod;
import io.moonman.emergingtechnology.item.polymers.PlasticSheet;
import io.moonman.emergingtechnology.item.polymers.PlasticTissueScaffold;
import io.moonman.emergingtechnology.item.polymers.PlasticWaste;
import io.moonman.emergingtechnology.item.polymers.ShreddedPaper;
import io.moonman.emergingtechnology.item.polymers.Filament;
import io.moonman.emergingtechnology.item.polymers.PaperPulp;
import io.moonman.emergingtechnology.item.polymers.PaperWaste;
import io.moonman.emergingtechnology.item.polymers.ShreddedPlant;
import io.moonman.emergingtechnology.item.polymers.ShreddedPlastic;
import io.moonman.emergingtechnology.item.polymers.ShreddedStarch;
import io.moonman.emergingtechnology.item.polymers.Turbine;
import io.moonman.emergingtechnology.item.synthetics.consumables.AlgaeBarCooked;
import io.moonman.emergingtechnology.item.synthetics.consumables.AlgaeBarRaw;
import io.moonman.emergingtechnology.item.synthetics.consumables.Drink;
import io.moonman.emergingtechnology.item.synthetics.consumables.DrinkPowder;
import io.moonman.emergingtechnology.item.synthetics.consumables.PlasticBottle;
import io.moonman.emergingtechnology.item.synthetics.consumables.SyntheticChickenCooked;
import io.moonman.emergingtechnology.item.synthetics.consumables.SyntheticChickenRaw;
import io.moonman.emergingtechnology.item.synthetics.consumables.SyntheticCowCooked;
import io.moonman.emergingtechnology.item.synthetics.consumables.SyntheticCowRaw;
import io.moonman.emergingtechnology.item.synthetics.consumables.SyntheticPigCooked;
import io.moonman.emergingtechnology.item.synthetics.consumables.SyntheticPigRaw;
import io.moonman.emergingtechnology.item.synthetics.products.Algae;
import io.moonman.emergingtechnology.item.synthetics.products.SyntheticLeather;
import io.moonman.emergingtechnology.item.synthetics.products.SyntheticSilk;
import io.moonman.emergingtechnology.item.synthetics.products.SyntheticSlime;
import io.moonman.emergingtechnology.item.synthetics.syringes.EmptySyringe;

/**
 * All items from Emerging Technology
 */
@ObjectHolder(EmergingTechnology.MODID)
public class ModItems {

	// region Hydroponics Items

	// Block Items
	public static final Item hydroponic = null;
	public static final Item light = null;
	public static final Item frame = null;
	public static final Item harvester = null;
	public static final Item filler = null;
	public static final Item scrubber = null;
	public static final Item diffuser = null;
	public static final Item injector = null;
	public static final Item aquaponic = null;
	public static final Item aquaponicport = null;

	// Items
	public static final Item redbulb = null;
	public static final Item greenbulb = null;
	public static final Item bluebulb = null;
	public static final Item purplebulb = null;

	public static final Item nozzlecomponent = null;
	public static final Item nozzlesmart = null;
	public static final Item nozzlelong = null;
	public static final Item nozzleprecise = null;

	public static final Item fertilizer = null;

	// endregion

	// region Polymers Items

	// Block Items
	public static final Item processor = null;
	public static final Item shredder = null;
	public static final Item plasticblock = null;
	public static final Item clearplasticblock = null;
	public static final Item machinecase = null;
	public static final Item fabricator = null;
	public static final Item collector = null;

	public static final Item ladder = null;

	public static final Item shreddedplasticblock = null;
	public static final Item shreddedplantblock = null;
	public static final Item shreddedstarchblock = null;

	// Items
	public static final Item shreddedplastic = null;
	public static final Item shreddedplant = null;
	public static final Item shreddedstarch = null;
	public static final Item shreddedpaper = null;

	public static final Item plasticwaste = null;
	public static final Item paperwaste = null;
	public static final Item paperpulp = null;

	public static final Item filament = null;
	public static final Item plasticrod = null;
	public static final Item plasticsheet = null;
	public static final Item plastictissuescaffold = null;
	public static final Item turbine = null;

	// endregion

	// region Synthetics Items
	// Block Items
	public static final Item cooker = null;
	public static final Item bioreactor = null;
	public static final Item scaffolder = null;
	public static final Item algaebioreactor = null;

	// Items

	public static final Item emptysyringe = null;

	public static final Item fullsyringe = null;

	public static final Item sample = null;

	public static final Item syntheticcowraw = null;
	public static final Item syntheticchickenraw = null;
	public static final Item syntheticpigraw = null;

	public static final Item syntheticcowcooked = null;
	public static final Item syntheticchickencooked = null;
	public static final Item syntheticpigcooked = null;

	public static final Item syntheticleather = null;
	public static final Item syntheticslime = null;
	public static final Item syntheticsilk = null;

	public static final Item algae = null;
	public static final Item algaebarraw = null;
	public static final Item algaebarcooked = null;

	public static final Item plasticbottle = null;
	public static final Item drinkpowder = null;
	public static final Item drink = null;

	// endregion

	// region Electrics Items
	// Block Items
	public static final Item piezoelectric = null;
	public static final Item tidalgenerator = null;
	public static final Item solar = null;
	public static final Item solarglass = null;
	public static final Item wind = null;
	public static final Item battery = null;
	public static final Item biomassgenerator = null;
	public static final Item biocharblock = null;
	public static final Item optimiser = null;
	public static final Item speaker = null;
	// Items
	public static final Item biomass = null;
	public static final Item biochar = null;

	public static final Item circuit = null;
	public static final Item circuitbasic = null;
	public static final Item circuitadvanced = null;
	public static final Item circuitsuperior = null;
	// endregion

	// region Fluid block items
	public static final Item carbondioxideblock = null;
	// endregion

	// region Ore block items
	public static final Item polluteddirt = null;
	public static final Item pollutedsand = null;
	public static final Item pollutedgravel = null;

	// All *non-block* items
	public static Item[] getItems() {
		Item[] items = { ModItems.redbulb, ModItems.greenbulb, ModItems.bluebulb, ModItems.purplebulb,
				ModItems.nozzlecomponent, ModItems.nozzlesmart, ModItems.nozzlelong, ModItems.nozzleprecise,
				ModItems.fertilizer, ModItems.shreddedplastic, ModItems.shreddedplant, ModItems.shreddedstarch,
				ModItems.shreddedpaper, ModItems.drinkpowder, ModItems.plasticrod, ModItems.plasticsheet, ModItems.filament,
				ModItems.plastictissuescaffold, ModItems.turbine, ModItems.plasticbottle, ModItems.plasticwaste, ModItems.paperwaste,
				ModItems.paperpulp, ModItems.emptysyringe, ModItems.syntheticcowraw, ModItems.syntheticchickenraw,
				ModItems.syntheticpigraw, ModItems.syntheticcowcooked, ModItems.syntheticchickencooked,
				ModItems.syntheticpigcooked, ModItems.syntheticleather, ModItems.syntheticslime, ModItems.syntheticsilk,
				ModItems.algaebarraw, ModItems.algaebarcooked, ModItems.drink, ModItems.algae, ModItems.biomass, ModItems.biochar,
				ModItems.circuit, ModItems.circuitbasic, ModItems.circuitadvanced, ModItems.circuitsuperior };

		return items;
	}

	public static Item[] generateItems() {
		Item[] items = { new RedBulb(), new GreenBulb(), new BlueBulb(), new PurpleBulb(), new NozzleComponent(),
				new NozzleSmart(), new NozzleLong(), new NozzlePrecise(), new Fertilizer(), new ShreddedPlastic(),
				new ShreddedPlant(), new ShreddedStarch(), new ShreddedPaper(), new PaperWaste(), new PaperPulp(),
				new PlasticWaste(), new PlasticRod(), new PlasticSheet(), new Filament(), new PlasticTissueScaffold(),
				new Turbine(), new PlasticBottle(), new EmptySyringe(), new SyntheticCowRaw(), new SyntheticChickenRaw(),
				new SyntheticPigRaw(), new SyntheticPigCooked(), new SyntheticCowCooked(), new SyntheticChickenCooked(),
				new SyntheticLeather(), new SyntheticSlime(), new SyntheticSilk(), new AlgaeBarRaw(),
				new AlgaeBarCooked(), new Algae(), new DrinkPowder(), new Drink(), new Biomass(), new Biochar(), new Circuit(), new CircuitBasic(),
				new CircuitAdvanced(), new CircuitSuperior() };

		return items;
	}
}