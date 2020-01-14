package io.moonman.emergingtechnology.helpers.machines;

import java.util.Random;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

/**
 * Provides useful methods for the Collector
 */
public class CollectorHelper {

    private static ItemStack PAPER = new ItemStack(ModItems.paperwaste);
    private static ItemStack PLASTIC = new ItemStack(ModItems.plasticwaste);

    public static boolean isInValidBiome(Biome biome) {
        return biome == Biomes.BEACH || biome == Biomes.OCEAN || biome == Biomes.DEEP_OCEAN;
    }

    public static boolean isFloatingInWater(World world, BlockPos pos) {

        IBlockState aboveBlock = world.getBlockState(pos.add(0, 1, 0));

        // If underwater, is invalid
        if (aboveBlock.getBlock() == Blocks.WATER) {
            return false;
        }

        BlockPos startPos = pos.add(-2, 0, -2);

        int waterBlockCount = 0;

        for (int x = 0; x < 5; x ++) {
            for (int z = 0; z < 5; z++) {
                boolean isValidNeighbour = isValidNeighbour(world.getBlockState(startPos.add(x, 0, z)));
                if (isValidNeighbour) {
                    waterBlockCount++;
                    if (waterBlockCount >= EmergingTechnologyConfig.POLYMERS_MODULE.COLLECTOR.minimumWaterBlocks) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static ItemStack getRandomRecoveredItemStack() {
        int random = new Random().nextInt(101);

        if (random < 50) {
            return PLASTIC.copy();
        } else {
            return PAPER.copy();
        }
    }

    public static boolean isValidItemStack(ItemStack itemStack) {
        return StackHelper.compareItemStacks(itemStack, PAPER) || StackHelper.compareItemStacks(itemStack, PLASTIC);
    }

    private static boolean isValidNeighbour(IBlockState state) {

        Block block = state.getBlock();

        return (block == Blocks.WATER);
    }
}