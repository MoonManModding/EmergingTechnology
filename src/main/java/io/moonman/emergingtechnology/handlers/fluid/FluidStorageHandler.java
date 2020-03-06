package io.moonman.emergingtechnology.handlers.fluid;

import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

/**
 * A customisable FluidTank used in Emerging Technology for use with Water
 */
public class FluidStorageHandler extends FluidTank implements INBTSerializable<CompoundNBT> {
    public FluidStorageHandler(int capacity) {
        super(capacity);
    }

    @Override
        public boolean isFluidValid(FluidStack fluidStack) {
            return fluidStack.getFluid() == Fluids.WATER;
        }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("fluid", getFluidAmount());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        FluidStack fluidStack = new FluidStack(Fluids.WATER, nbt.getInt("fluid"));
        setFluid(fluidStack);
    }
}