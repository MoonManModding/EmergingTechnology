package io.moonman.emergingtechnology.helpers.custom.helpers;

import io.moonman.emergingtechnology.helpers.custom.classes.CustomFluid;
import net.minecraftforge.fluids.Fluid;

/**
Provides useful methods for custom fluid manipulation
*/
public class CustomFluidHelper {

    public static CustomFluid[] customFluids;

    public static CustomFluid[] getCustomFluids() {
        return CustomFluidHelper.customFluids;
    }

    public static boolean isFluidInCustomFluids(Fluid fluid) {

        CustomFluid customFluid = getCustomFluid(fluid);

        if (customFluid == null) {
            return false;
        }

        for (CustomFluid customFluidEntry : getCustomFluids()) {
            if (fluid.getName().equalsIgnoreCase(customFluidEntry.name)) {
                return true;
            }
        }
        return false;
    }

    public static int getGrowthProbabilityForFluid(Fluid fluid) {

        CustomFluid customFluid = getCustomFluid(fluid);

        if (fluid != null) {
            return customFluid.growthModifier;
        } else {
            return 0;
        }

    }

    public static int getWaterUsageForFluid(Fluid fluid) {

        CustomFluid customFluid = getCustomFluid(fluid);

        if (customFluid != null) {
            return customFluid.fluidUsage;
        } else {
            return 0;
        }
    }

    public static CustomFluid getCustomFluid(Fluid fluid) {

        CustomFluid[] customFluids = getCustomFluids();

        if (customFluids == null) {
            return null;
        }

        for (int i = 0; i < customFluids.length; i++) {
            if (customFluids[i].name.equalsIgnoreCase(fluid.getName())) {
                return customFluids[i];
            }
        }

        return null;
    }

}