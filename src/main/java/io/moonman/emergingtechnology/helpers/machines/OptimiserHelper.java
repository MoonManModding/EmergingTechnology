package io.moonman.emergingtechnology.helpers.machines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.moonman.emergingtechnology.helpers.machines.classes.OptimiserPacket;
import io.moonman.emergingtechnology.machines.optimiser.OptimiserTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OptimiserHelper {

    public static OptimiserPacket getAdjacentOptimisersPacket(World world, BlockPos pos) {

        List<OptimiserPacket> packets = new ArrayList<OptimiserPacket>();

        for (EnumFacing facing : EnumFacing.VALUES) {

            TileEntity tile = world.getTileEntity(pos.offset(facing));

            if (tile == null) continue;
            if (tile instanceof OptimiserTileEntity == false) continue;

            OptimiserTileEntity optimiser = (OptimiserTileEntity) tile;

            packets.add(optimiser.getPacket());
        }

        return merge(packets);
    }

    private static OptimiserPacket merge(List<OptimiserPacket> packets) {

        List<Integer> energy = new ArrayList<Integer>();
        List<Integer> fluid = new ArrayList<Integer>();
        List<Integer> progress = new ArrayList<Integer>();

        energy.add(1);
        fluid.add(1);
        progress.add(1);

        for (OptimiserPacket packet : packets) {
            energy.add(packet.energyModifier);
            fluid.add(packet.fluidModifier);
            progress.add(packet.progressModifier);
        }

        OptimiserPacket finalPacket = new OptimiserPacket(Collections.max(energy), Collections.max(fluid), Collections.max(progress));

        return finalPacket;
    }
}