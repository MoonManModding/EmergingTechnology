package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.config.hydroponics.filler.FillerConfiguration;
import io.moonman.emergingtechnology.handlers.fluid.FluidStorageHandler;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.filler.FillerTile;
import net.minecraft.fluid.Fluids;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class FillerHelper {

    public static void fillAdjacent(World world, BlockPos pos, LazyOptional<FluidStorageHandler> fluidStorageHandler) {

        for (Direction facing : Direction.values()) {
            TileEntity neighbour = world.getTileEntity(pos.offset(facing));

            if (neighbour == null || neighbour instanceof FillerTile) {
                continue;
            }

            LazyOptional<IFluidHandler> neighbourFluidHandler = neighbour
                    .getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());

            neighbourFluidHandler.ifPresent(f -> {
                f.fill(new FluidStack(Fluids.WATER, FillerConfiguration.WATER_TRANSFER.get()), FluidAction.EXECUTE);
            });
        }
        fluidStorageHandler.ifPresent(f -> f.setFluid(new FluidStack(Fluids.WATER, Reference.FILLER_FLUID_CAPACITY)));
    }
}