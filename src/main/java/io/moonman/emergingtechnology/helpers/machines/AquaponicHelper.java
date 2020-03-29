package io.moonman.emergingtechnology.helpers.machines;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.block.blocks.AquaponicBase;
import io.moonman.emergingtechnology.block.blocks.AquaponicBlock;
import io.moonman.emergingtechnology.block.blocks.AquaponicGlass;
import io.moonman.emergingtechnology.machines.aquaponic.AquaponicTileEntity;
import io.moonman.emergingtechnology.machines.aquaponicport.AquaponicPort;
import io.moonman.emergingtechnology.machines.aquaponicport.AquaponicPortTileEntity;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Provides useful methods for the Aquaponic machine
 */
public class AquaponicHelper {

    public static void fillMultiblockStructure(World world, BlockPos pos, EnumFacing facing) {

        EnumFacing left = facing.rotateY();
        EnumFacing right = facing.rotateYCCW();
        EnumFacing behind = facing.getOpposite();
        List<BlockPos> positions = new ArrayList<BlockPos>();

        for (int h = 1; h < 3; h++) {
            for (int i = 0; i < 5; i++) {

                BlockPos row = pos.offset(behind, i);

                row = row.offset(EnumFacing.UP, h);

                if (i > 0 && i < 4) {
                    positions.add(row);
                    positions.add(row.offset(left, 1));
                    positions.add(row.offset(right, 1));
                }
            }
        }

        for (BlockPos position : positions) {
            if (requiresFill(world, position)) {
                world.setBlockState(position, Blocks.WATER.getDefaultState(), 3);
            }
        }
    }

    public static void setPortControllerBlocks(AquaponicTileEntity controller, World world, BlockPos pos, EnumFacing facing) {
        EnumFacing left = facing.rotateY();
        EnumFacing right = facing.rotateYCCW();
        EnumFacing behind = facing.getOpposite();

        for (int i = 0; i < 5; i++) {
            List<BlockPos> positions = new ArrayList<BlockPos>();

            BlockPos row = pos.offset(behind, i);

            if (i > 0) {
                positions.add(row);
            }

            positions.add(row.offset(left, 1));
            positions.add(row.offset(left, 2));
            positions.add(row.offset(right, 1));
            positions.add(row.offset(right, 2));

            positions.stream().forEach(x -> {
                trySetAquaponicPortBlockController(controller, world, x);
            });
        }
    }

    public static boolean isValidMultiblockStructure(World world, BlockPos pos, EnumFacing facing) {
        return isValidBaseStructure(world, pos, facing) && isValidFrameStructure(world, pos, facing)
                && isEmptyFrameStructure(world, pos, facing);
    }

    private static boolean isValidBaseStructure(World world, BlockPos pos, EnumFacing facing) {

        EnumFacing left = facing.rotateY();
        EnumFacing right = facing.rotateYCCW();
        EnumFacing behind = facing.getOpposite();

        for (int i = 0; i < 5; i++) {
            List<BlockPos> positions = new ArrayList<BlockPos>();

            BlockPos row = pos.offset(behind, i);

            if (i > 0) {
                positions.add(row);
            }

            positions.add(row.offset(left, 1));
            positions.add(row.offset(left, 2));
            positions.add(row.offset(right, 1));
            positions.add(row.offset(right, 2));

            if (!positions.stream().allMatch(x -> isValidBaseBlock(world, x)))
                return false;
        }

        return true;
    }

    private static boolean isValidFrameStructure(World world, BlockPos pos, EnumFacing facing) {
        EnumFacing left = facing.rotateY();
        EnumFacing right = facing.rotateYCCW();
        EnumFacing behind = facing.getOpposite();

        for (int h = 1; h < 3; h++) {
            for (int i = 0; i < 5; i++) {
                List<BlockPos> positions = new ArrayList<BlockPos>();

                BlockPos row = pos.offset(behind, i);

                row = row.offset(EnumFacing.UP, h);

                if (i == 0 || i == 4) {
                    positions.add(row);
                    positions.add(row.offset(left, 1));
                    positions.add(row.offset(right, 1));
                } else {
                    positions.add(row.offset(left, 2));
                    positions.add(row.offset(right, 2));
                }

                if (!positions.stream().allMatch(x -> isValidFrameBlock(world, x)))
                    return false;
            }
        }

        return true;
    }

    private static boolean isEmptyFrameStructure(World world, BlockPos pos, EnumFacing facing) {
        EnumFacing left = facing.rotateY();
        EnumFacing right = facing.rotateYCCW();
        EnumFacing behind = facing.getOpposite();

        for (int h = 1; h < 3; h++) {
            for (int i = 0; i < 5; i++) {
                List<BlockPos> positions = new ArrayList<BlockPos>();

                BlockPos row = pos.offset(behind, i);

                row = row.offset(EnumFacing.UP, h);

                if (i > 0 && i < 4) {
                    positions.add(row);
                    positions.add(row.offset(left, 1));
                    positions.add(row.offset(right, 1));
                }

                if (!positions.stream().allMatch(x -> isValidContentsBlock(world, x)))
                    return false;
            }
        }

        return true;
    }

    private static void trySetAquaponicPortBlockController(AquaponicTileEntity controller, World world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() instanceof AquaponicPort) {
            if (world.getTileEntity(pos) instanceof AquaponicPortTileEntity) {
                AquaponicPortTileEntity port = (AquaponicPortTileEntity) world.getTileEntity(pos);
                port.setController(controller);
            }
        }
    }

    private static boolean isValidBaseBlock(World world, BlockPos pos) {

        Block block = world.getBlockState(pos).getBlock();

        if (block instanceof AquaponicBase || block instanceof AquaponicPort)
            return true;

        return false;
    }

    private static boolean isValidFrameBlock(World world, BlockPos pos) {

        Block block = world.getBlockState(pos).getBlock();

        if (block instanceof AquaponicGlass || block instanceof AquaponicBlock)
            return true;

        return false;
    }

    private static boolean isValidContentsBlock(World world, BlockPos pos) {

        Block block = world.getBlockState(pos).getBlock();

        if (block == Blocks.WATER || block == Blocks.FLOWING_WATER || block == Blocks.AIR)
            return true;

        return false;
    }

    private static boolean requiresFill(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == Blocks.AIR;
    }
}