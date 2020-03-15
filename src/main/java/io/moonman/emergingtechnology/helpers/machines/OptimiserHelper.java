package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.machines.classes.OptimiserPacket;
import io.moonman.emergingtechnology.machines.classes.tile.IOptimisableTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OptimiserHelper {

    public static void pushPacketsToAdjacentMachines(World world, BlockPos pos, OptimiserPacket packet) {

        for (EnumFacing facing : EnumFacing.VALUES) {

            for (int i = 1; i < EmergingTechnologyConfig.ELECTRICS_MODULE.OPTIMISER.range; i++) {

                TileEntity tile = world.getTileEntity(pos.offset(facing, i));

                if (tile == null)
                    continue;
                if (tile instanceof IOptimisableTile == false)
                    continue;

                IOptimisableTile machine = (IOptimisableTile) tile;

                machine.setPacket(packet);
            }
        }
    }
}