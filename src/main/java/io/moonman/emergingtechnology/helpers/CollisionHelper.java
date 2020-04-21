package io.moonman.emergingtechnology.helpers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;

public class CollisionHelper {

    // Standard AABB for machines
    private static final AxisAlignedBB AABB_BASE_BLOCK = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);
    private static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.0D, 0.9375D, 0.98D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.98D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.0D, 0.9375D, 0.02D, 1.0D, 1.0D, 0.0D);
    private static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 0.02D, 1.0D, 1.0D);

    public static List<AxisAlignedBB> getMachineCollisionBoxList(IBlockState state) {
        List<AxisAlignedBB> list = new ArrayList<AxisAlignedBB>();

        list.add(AABB_BASE_BLOCK);
        list.add(AABB_NORTH);
        list.add(AABB_SOUTH);
        list.add(AABB_EAST);
        list.add(AABB_WEST);

        return list;
    }
}