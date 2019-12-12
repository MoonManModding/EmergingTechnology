package io.moonman.emergingtechnology.helpers.custom.providers;

import java.util.ArrayList;

import io.moonman.emergingtechnology.helpers.custom.classes.ModFluid;
import io.moonman.emergingtechnology.helpers.custom.loaders.CustomFluidLoader;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModFluidProvider {
    private static ModFluid[] allFluids;
    public static ModFluid[] customFluids;

    public static ModFluid[] getFluids() {
        return allFluids;
    }

    public static void preInit(FMLPreInitializationEvent event) {

        readFromFile(event);

        ArrayList<ModFluid> generatedFluids = new ArrayList<ModFluid>();

        generatedFluids.addAll(generateBaseFluids());
        generatedFluids.addAll(generateCustomFluids());

        allFluids = generatedFluids.toArray(new ModFluid[0]);
    }

    private static ArrayList<ModFluid> generateBaseFluids() {

        String[] waterPlants = new String[] {};
        String[] lavaPlants = new String[] {"minecraft:nether_wart"};

        ModFluid water = new ModFluid(1, "water", 0, waterPlants, 0);
        ModFluid lava = new ModFluid(1, "lava", 0, lavaPlants, 5);

        ArrayList<ModFluid> baseFluids = new ArrayList<ModFluid>();

        baseFluids.add(water);
        baseFluids.add(lava);

        return baseFluids;
    }

    private static ArrayList<ModFluid> generateCustomFluids() {
        ArrayList<ModFluid> fluids = new ArrayList<ModFluid>();

        if (customFluids != null) {
            int idCounter = 3;
            for (ModFluid fluid : customFluids) {
                fluid.id = idCounter;
                fluids.add(fluid);
                idCounter++;
            }
        }

        return fluids;
    }

    private static void readFromFile(FMLPreInitializationEvent event) {
        CustomFluidLoader.preInit(event);
    }

    public static boolean fluidExists(FluidStack fluidStack) {
        if (fluidStack == null) return false;
        if (fluidStack.getFluid() == null) return false;

        String name = fluidStack.getFluid().getName();

        ModFluid fluid = getFluidByName(name);

        if (fluid == null) return false;

        return true;
    }

    public static ModFluid getFluidById(int id) {
        for (ModFluid fluid : allFluids) {
            if (id == fluid.id) {
                return fluid;
            }
        }

        return null;
    }

    public static ModFluid getFluidByName(String name) {
        for (ModFluid fluid : allFluids) {
            if (name == fluid.name) {
                return fluid;
            }
        }

        return null;
    }

    public static ModFluid getFluidByFluidStack(FluidStack fluidStack) {
        if (fluidStack == null) return null;
        if (fluidStack.getFluid() == null) return null;

        String name = fluidStack.getFluid().getName();

        return getFluidByName(name);
    }

    public static int getGrowthProbabilityForFluidByFluidStack(FluidStack fluidStack) {

        if (fluidStack == null) return 0;
        if (fluidStack.getFluid() == null) return 0;

        String name = fluidStack.getFluid().getName();

        ModFluid fluid = getFluidByName(name);

        if (fluid == null) return 0;

        return fluid.growthModifier;

    }

    public static String[] getBoostPlantNamesForFluidByFluidStack(FluidStack fluidStack) {

        if (fluidStack == null) return new String[] {};
        if (fluidStack.getFluid() == null) return new String[] {};

        String name = fluidStack.getFluid().getName();

        ModFluid fluid = getFluidByName(name);

        if (fluid == null) return new String[] {};

        return fluid.plants;
    }

    public static int getBoostModifierForFluidByFluidStack(FluidStack fluidStack) {

        if (fluidStack == null) return 0;
        if (fluidStack.getFluid() == null) return 0;

        String name = fluidStack.getFluid().getName();

        ModFluid fluid = getFluidByName(name);

        if (fluid == null) return 0;

        return fluid.boostModifier;
    }

    public static int getGrowthProbabilityForFluidById(int id) {
        ModFluid fluid = getFluidById(id);

        if (fluid == null) return 0;

        return fluid.growthModifier;

    }

    public static String[] getBoostPlantNamesForFluidById(int id) {
        ModFluid fluid = getFluidById(id);

        if (fluid == null) return new String[] {};

        return fluid.plants;
    }

    public static int getBoostModifierForFluidById(int id) {
        ModFluid fluid = getFluidById(id);

        if (fluid == null) return 0;

        return fluid.boostModifier;
    }
}