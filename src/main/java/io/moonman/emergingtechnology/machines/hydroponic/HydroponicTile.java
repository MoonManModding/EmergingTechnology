package io.moonman.emergingtechnology.machines.hydroponic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.moonman.emergingtechnology.handlers.fluid.FluidStorageHandler;
import io.moonman.emergingtechnology.init.ModTiles;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.MachineTileBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class HydroponicTile extends MachineTileBase {

    protected FluidStorageHandler fluidStorageHandler = new FluidStorageHandler(Reference.HYDROPONIC_FLUID_CAPACITY);

    public HydroponicTile() {
        super(ModTiles.HYDROPONIC);
    }

    public LazyOptional<FluidStorageHandler> fluidHandler = LazyOptional.of(() -> fluidStorageHandler);

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT fluidTag = tag.getCompound("fluid");
        fluidHandler.ifPresent(h -> h.deserializeNBT(fluidTag));
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        fluidHandler.ifPresent(h -> {
            CompoundNBT compound = h.serializeNBT();
            tag.put("fluid", compound);
        });
        return super.write(tag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void cycle() {
        //FillerHelper.fillAdjacent(this.getWorld(), this.getPos(), this.fluidHandler);
    }
}