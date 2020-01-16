package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.machines.enums.TurbineSpeedEnum;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Provides useful methods for the Wind Generator
 */
public class WindHelper {

    public static boolean isGeneratorInAir(World world, BlockPos pos) {

        BlockPos startPos = pos.add(-2, 0, -2);

        int waterBlockCount = 0;

        for (int x = 0; x < 5; x ++) {
            for (int z = 0; z < 5; z++) {
                boolean isValidNeighbour = isValidNeighbour(world.getBlockState(startPos.add(x, 0, z)));
                if (isValidNeighbour) {
                    waterBlockCount++;
                    if (waterBlockCount >= EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.minimumAirBlocks) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean isGeneratorAtOptimalHeight(BlockPos pos) {

        int min = EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.minOptimalHeight;
        int max = EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.maxOptimalHeight;

        return pos.getY() < max && pos.getY() > min;

    }

    public static String getTurbineStateFromSpeedEnum(TurbineSpeedEnum speed) {
        switch(speed) {
            case OFF: return "default";
            case SLOW: return "slow";
            case FAST: return "fast";
            default: return "default";
        }
    }

    private static boolean isValidNeighbour(IBlockState state) {

        Block block = state.getBlock();

        return (block == Blocks.AIR);
    }
}