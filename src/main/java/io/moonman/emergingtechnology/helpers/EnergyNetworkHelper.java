package io.moonman.emergingtechnology.helpers;

import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.machines.MachineTileBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * Simple helper for pushing energy to peripheral machines
 */
public class EnergyNetworkHelper {

    public static void pushEnergy(World world, BlockPos blockPos,
            LazyOptional<EnergyStorageHandler> energyStorageHandler) {
        for (Direction direction : Direction.values()) {

            BlockPos position = blockPos;
            boolean reachedEdge = false;
            int count = 1;

            while (reachedEdge == false) {
                position = blockPos.offset(direction, count);

                if (!isGeneratorTileAtPosition(world, position)) {
                    reachedEdge = true;
                }

                count++;
            }

            tryPushEnergyToTile(world, position, direction.getOpposite(), energyStorageHandler);

            continue;
        }
    }

    private static boolean isGeneratorTileAtPosition(World world, BlockPos position) {
        TileEntity tileEntity = world.getTileEntity(position);

        if (tileEntity == null)
            return false;
        if (!(tileEntity instanceof MachineTileBase))
            return false;

        MachineTileBase machine = (MachineTileBase) tileEntity;

        return machine.isEnergyGeneratorTile();
    }

    private static void tryPushEnergyToTile(World world, BlockPos position, Direction direction,
            LazyOptional<EnergyStorageHandler> energyStorageHandler) {
        TileEntity tileEntity = world.getTileEntity(position);

        if (tileEntity == null)
            return;

        LazyOptional<IEnergyStorage> energyStorage = tileEntity.getCapability(CapabilityEnergy.ENERGY, direction);

        energyStorage.ifPresent(e -> {
            energyStorageHandler.ifPresent(h -> {
                int pushedEnergy = e.receiveEnergy(h.getEnergyStored(), false);
                h.consumeEnergy(pushedEnergy);
            });
        });
    }
}