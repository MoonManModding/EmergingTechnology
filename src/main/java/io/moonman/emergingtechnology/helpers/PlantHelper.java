package io.moonman.emergingtechnology.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockReed;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

/**
Provides useful methods to manipulate and validate plant items and blocks
*/
public class PlantHelper {
    
    public static boolean isPlantItem(Item item) {
        return item instanceof IPlantable || item instanceof IGrowable || isItemInOverride(item);
    }

    public static boolean isPlantBlock(Block block) {
        return block instanceof IPlantable || block instanceof IGrowable;
    }

    public static int getPlantLightAtPosition(World world, BlockPos position) {
        return world.getLightFromNeighbors(position.add(0, 1, 0));
    }

    public static int getPlantGrowthAtPosition(World world, BlockPos position) {
        IBlockState state = world.getBlockState(position.add(0, 1, 0));

        if (state == null) return 0;

        Block block = state.getBlock();

        if (block instanceof BlockCrops) {
            return state.getValue(BlockCrops.AGE);
        };

        if (block instanceof BlockReed || block instanceof BlockCactus) {
            int growth = 0;

            Block[] aboveBlocks = new Block[] {
                world.getBlockState(position.add(0, 1, 0)).getBlock(),
                world.getBlockState(position.add(0, 2, 0)).getBlock(),
                world.getBlockState(position.add(0, 3, 0)).getBlock()
            };

            for (Block aboveBlock : aboveBlocks) {
                if (aboveBlock instanceof BlockReed || block instanceof BlockCactus) {
                    growth++;
                } else {
                    return growth;
                };
            }

            return growth;
        };

        return 0;
    }

    public static String getPlantNameAtPosition(World world, BlockPos position) {
        IBlockState state = world.getBlockState(position.add(0, 1, 0));

        if (state == null) return "Nothing";

        return state.getBlock().getLocalizedName();
    }

    private static boolean isItemInOverride(Item item) {
        if(item == Items.REEDS) return true;
        return false;
    }
}