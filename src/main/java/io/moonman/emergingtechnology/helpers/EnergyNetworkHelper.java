package io.moonman.emergingtechnology.helpers;

import io.moonman.emergingtechnology.handlers.energy.GeneratorEnergyStorageHandler;
import io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * Simple helper for pushing energy to peripheral machines
 */
public class EnergyNetworkHelper {

    public static void pushEnergy(World world, BlockPos blockPos, GeneratorEnergyStorageHandler energyStorageHandler) {
        for (EnumFacing facing : EnumFacing.VALUES) {

            BlockPos position = blockPos;
            boolean reachedEdge = false;
            int count = 1;

            while (reachedEdge == false) {
                position = blockPos.offset(facing, count);

                if (!isGeneratorTileAtPosition(world, position)) {
                    reachedEdge = true;
                }

                count ++;
            }

            tryPushEnergyToTile(world, position, facing.getOpposite(), energyStorageHandler);

            continue;
        }
    }

    private static boolean isGeneratorTileAtPosition(World world, BlockPos position) {
        TileEntity tileEntity = world.getTileEntity(position);

        if (tileEntity == null) return false;
        if (!(tileEntity instanceof MachineTileBase)) return false;
        
        MachineTileBase machine = (MachineTileBase) tileEntity;

        return machine.isEnergyGeneratorTile();
    }

    private static void tryPushEnergyToTile(World world, BlockPos position, EnumFacing facing, GeneratorEnergyStorageHandler energyStorageHandler) {
        TileEntity tileEntity = world.getTileEntity(position);

        if (tileEntity == null) return;
        if (!tileEntity.hasCapability(CapabilityEnergy.ENERGY, facing)) return;

        IEnergyStorage energyStorage = tileEntity.getCapability(CapabilityEnergy.ENERGY, facing);

        if (energyStorage == null) return;

        int pushedEnergy = energyStorage.receiveEnergy(energyStorageHandler.getEnergyStored(), false);

        energyStorageHandler.extractEnergy(pushedEnergy, false);
    }
    
}