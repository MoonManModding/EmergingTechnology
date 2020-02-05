package io.moonman.emergingtechnology.handlers.fluid;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

/**
 * A customisable FluidTank used in Emerging Technology for use with Water
 */
public class FluidStorageHandler extends FluidTank {
    public FluidStorageHandler(int capacity) {
        super(capacity);
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {

        return fluid.getFluid() == FluidRegistry.WATER;
    }
}