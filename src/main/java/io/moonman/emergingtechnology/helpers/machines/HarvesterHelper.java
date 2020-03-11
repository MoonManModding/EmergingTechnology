package io.moonman.emergingtechnology.helpers.machines;

import java.util.List;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.helpers.FacingHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Provides useful methods for the Harvester
 */
public class HarvesterHelper {

    public static boolean isInteractableCrop(Block block) {
        if (block == null)
            return false;

        if (block.getRegistryName().toString().equalsIgnoreCase("agricraft:crop")) {
            return true;
        }

        return false;
    }

    public static boolean isInteractableCropReadyForHarvest(IBlockState blockState, World world, BlockPos pos) {

        int itemCount = 0;

        List<ItemStack> drops = getCropDrops(blockState, world, pos);

        for (ItemStack drop : drops) {
            itemCount += drop.getCount();
        }

        return itemCount > 2;
    }

    private static List<ItemStack> getCropDrops(IBlockState blockState, World world, BlockPos pos) {
        return blockState.getBlock().getDrops(world, pos, blockState, 1);
    }

    public static int getAnimationAngleFromFacings(EnumFacing baseFacing, EnumFacing harvestFacing) {
       
        if (baseFacing == harvestFacing) return 0;
        if (baseFacing.rotateY() == harvestFacing) return 90;
        if (baseFacing.rotateYCCW() == harvestFacing) return -90;
        if (baseFacing.getOpposite() == harvestFacing) return 180;

        return 0;
    }
    
    public static EnumFacing getFacingFromAngle(EnumFacing baseFacing, int angle) {
        EnumFacing newFacing = baseFacing;

        if (angle == 90) {
            newFacing = baseFacing.rotateY();
        }

        if (angle == -90) {
            newFacing = baseFacing.rotateYCCW();
        }

        if (angle == 180) {
            newFacing = baseFacing.getOpposite();
        }

        return newFacing;
    }
}