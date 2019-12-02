package io.moonman.emergingtechnology.tile.handlers;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidStorageHandler extends FluidTank
{
    public FluidStorageHandler() {
        super(1000);
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
       return fluid.getFluid() == FluidRegistry.WATER;
    }
}