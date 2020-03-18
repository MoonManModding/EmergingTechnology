package io.moonman.emergingtechnology.init;

import io.moonman.emergingtechnology.items.electrics.Biochar;
import io.moonman.emergingtechnology.items.electrics.Biomass;
import io.moonman.emergingtechnology.items.electrics.Circuit;
import io.moonman.emergingtechnology.items.electrics.CircuitAdvanced;
import io.moonman.emergingtechnology.items.electrics.CircuitBasic;
import io.moonman.emergingtechnology.items.electrics.CircuitSuperior;
import io.moonman.emergingtechnology.items.hydroponics.BlueBulb;
import io.moonman.emergingtechnology.items.hydroponics.Fertilizer;
import io.moonman.emergingtechnology.items.hydroponics.GreenBulb;
import io.moonman.emergingtechnology.items.hydroponics.NozzleComponent;
import io.moonman.emergingtechnology.items.hydroponics.NozzleLong;
import io.moonman.emergingtechnology.items.hydroponics.NozzlePrecise;
import io.moonman.emergingtechnology.items.hydroponics.NozzleSmart;
import io.moonman.emergingtechnology.items.hydroponics.PurpleBulb;
import io.moonman.emergingtechnology.items.hydroponics.RedBulb;
import io.moonman.emergingtechnology.items.polymers.Filament;
import io.moonman.emergingtechnology.items.polymers.PaperPulp;
import io.moonman.emergingtechnology.items.polymers.PaperWaste;
import io.moonman.emergingtechnology.items.polymers.PlasticRod;
import io.moonman.emergingtechnology.items.polymers.PlasticSheet;
import io.moonman.emergingtechnology.items.polymers.PlasticTissueScaffold;
import io.moonman.emergingtechnology.items.polymers.PlasticWaste;
import io.moonman.emergingtechnology.items.polymers.ShreddedPaper;
import io.moonman.emergingtechnology.items.polymers.ShreddedPlant;
import io.moonman.emergingtechnology.items.polymers.ShreddedPlastic;
import io.moonman.emergingtechnology.items.polymers.ShreddedStarch;
import io.moonman.emergingtechnology.items.polymers.Turbine;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    @ObjectHolder(PlasticRod.registryName)
    public static PlasticRod PLASTICROD;

    @ObjectHolder(PlasticSheet.registryName)
    public static PlasticSheet PLASTICSHEET;

    @ObjectHolder(PlasticTissueScaffold.registryName)
    public static PlasticTissueScaffold PLASTICTISSUESCAFFOLD;

    @ObjectHolder(Turbine.registryName)
    public static Turbine TURBINE;

    @ObjectHolder(Filament.registryName)
    public static Filament FILAMENT;

    @ObjectHolder(PaperPulp.registryName)
    public static PaperPulp PAPERPULP;

    @ObjectHolder(PaperWaste.registryName)
    public static PaperWaste PAPERWASTE;

    @ObjectHolder(PlasticWaste.registryName)
    public static PlasticWaste PLASTICWASTE;

    @ObjectHolder(ShreddedPaper.registryName)
    public static ShreddedPaper SHREDDEDPAPER;

    @ObjectHolder(ShreddedPlastic.registryName)
    public static ShreddedPlastic SHREDDEDPLASTIC;

    @ObjectHolder(ShreddedPlant.registryName)
    public static ShreddedPlant SHREDDEDPLANT;

    @ObjectHolder(ShreddedStarch.registryName)
    public static ShreddedStarch SHREDDEDSTARCH;

    @ObjectHolder(Biochar.registryName)
    public static Biochar BIOCHAR;

    @ObjectHolder(Biomass.registryName)
    public static Biomass BIOMASS;

    @ObjectHolder(Circuit.registryName)
    public static Circuit CIRCUIT;

    @ObjectHolder(CircuitBasic.registryName)
    public static CircuitBasic CIRCUITBASIC;

    @ObjectHolder(CircuitAdvanced.registryName)
    public static CircuitAdvanced CIRCUITADVANCED;

    @ObjectHolder(CircuitSuperior.registryName)
    public static CircuitSuperior CIRCUITSUPERIOR;

    @ObjectHolder(Fertilizer.registryName)
    public static Fertilizer FERTILIZER;

    @ObjectHolder(BlueBulb.registryName)
    public static BlueBulb BLUEBULB;

    @ObjectHolder(RedBulb.registryName)
    public static RedBulb REDBULB;

    @ObjectHolder(GreenBulb.registryName)
    public static GreenBulb GREENBULB;

    @ObjectHolder(PurpleBulb.registryName)
    public static PurpleBulb PURPLEBULB;

    @ObjectHolder(NozzleComponent.registryName)
    public static NozzleComponent NOZZLECOMPONENT;

    @ObjectHolder(NozzleLong.registryName)
    public static NozzleLong NOZZLELONG;

    @ObjectHolder(NozzlePrecise.registryName)
    public static NozzlePrecise NOZZLEPRECISE;

    @ObjectHolder(NozzleSmart.registryName)
    public static NozzleSmart NOZZLESMART;

    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
        registerItems(event, new PlasticRod(), new PlasticSheet(), new PlasticTissueScaffold(), new PlasticWaste(),
                new PaperWaste(), new PaperPulp(), new ShreddedPaper(), new ShreddedPlant(), new ShreddedPlastic(),
                new ShreddedStarch(), new Biochar(), new Biomass(), new RedBulb(), new GreenBulb(), new BlueBulb(),
                new PurpleBulb(), new Turbine(), new Filament(), new Fertilizer(), new Circuit(), new CircuitBasic(),
                new CircuitAdvanced(), new CircuitSuperior(), new NozzleComponent(), new NozzleLong(),
                new NozzlePrecise(), new NozzleSmart());
    }

    private static void registerItems(final RegistryEvent.Register<Item> event, Item... items) {
        for (Item item : items) {
            event.getRegistry().register(item);
        }
    }

}