package io.moonman.emergingtechnology.handlers.fluid;

import java.util.List;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.FluidTankPropertiesWrapper;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

/**
 * A customisable FluidTank for both gas and fluid used in Emerging Technology
 */
public class SplitFluidStorageHandler implements IFluidHandler {

    private final FluidStorageHandler dummyTank = new FluidStorageHandler(0);

    private final FluidTank gasTank;
    private final FluidTank fluidTank;

    private final List<String> gasNames;
    private final List<String> fluidNames;

    public SplitFluidStorageHandler(FluidTank fluidTank, FluidTank gasTank, List<String> fluidNames, List<String> gasNames) {
        this.fluidTank = fluidTank;
        this.gasTank = gasTank;
        this.fluidNames = fluidNames;
        this.gasNames = gasNames;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        Fluid fluid = resource.getFluid();

        if (fluid == null) return 0;

        for (String fluidName : fluidNames) {
            if (fluid.getName().equalsIgnoreCase(fluidName)) {
                return fluidTank.fill(resource, doFill);
            }
        }
        
        for (String gasName : gasNames) {
            if (fluid.getName().equalsIgnoreCase(gasName)) {
                return gasTank.fill(resource, doFill);
            }
        }

        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return dummyTank.drain(resource, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return dummyTank.drain(maxDrain, doDrain);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[] {
            new FluidTankPropertiesWrapper(fluidTank),
            new FluidTankPropertiesWrapper(gasTank)
        };
    }

}