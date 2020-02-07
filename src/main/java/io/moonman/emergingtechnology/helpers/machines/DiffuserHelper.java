package io.moonman.emergingtechnology.helpers.machines;

import java.util.Random;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.PlantHelper;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.item.hydroponics.nozzles.NozzleBase;
import io.moonman.emergingtechnology.item.hydroponics.nozzles.NozzleLong;
import io.moonman.emergingtechnology.item.hydroponics.nozzles.NozzlePrecise;
import io.moonman.emergingtechnology.item.hydroponics.nozzles.NozzleSmart;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

/**
 * Provides useful methods for the Diffuser
 */
public class DiffuserHelper {

    public static boolean isItemStackValid(ItemStack itemStack) {
        return itemStack.getItem() instanceof NozzleBase;
    }

    public static int getNozzleIdForItemStack(ItemStack itemStack) {

        if (StackHelper.isItemStackEmpty(itemStack)) {
            return 0;
        }

        Item nozzle = itemStack.getItem();

        if (nozzle instanceof NozzlePrecise)
            return 1;
        if (nozzle instanceof NozzleLong)
            return 2;
        if (nozzle instanceof NozzleSmart)
            return 3;

        return 0;
    }

    public static int boostSurroundingPlants(World world, BlockPos pos, FluidTank gasHandler, int probability,
            int nozzleId) {

        int baseRange = EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserBaseRange;

        if (nozzleId == 0) {
            return doBoost(world, pos, gasHandler, probability, baseRange);
        }

        if (nozzleId == 1) {
            return doBoost(world, pos, gasHandler, probability * 2, baseRange);
        }

        if (nozzleId == 2) {
            return doBoost(world, pos, gasHandler, probability, baseRange * 2);
        }

        if (nozzleId == 3) {
            return doBoost(world, pos, gasHandler, probability * 2, baseRange * 2);
        }

        return 0;
    }

    private static int doBoost(World world, BlockPos pos, FluidTank gasHandler, int probability, int range) {
        int plantsBoosted = 0;

        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            for (int i = 1; i < range + 1; i++) {

                // Not enough gas
                if (gasHandler
                        .getFluidAmount() < EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserGasBaseUsage) {
                    break;
                }

                BlockPos position = pos.offset(facing, i);
                IBlockState blockState = world.getBlockState(position);
                Block block = blockState.getBlock();

                if (PlantHelper.isPlantBlock(blockState.getBlock())) {

                    boolean success = growBlock(world, blockState, block, position, gasHandler, probability);

                    if (success)
                        plantsBoosted += 1;

                } else if (blockState.getBlock() == Blocks.AIR) {
                    continue;
                } else {
                    break;
                }
            }
        }

        return plantsBoosted;
    }

    private static boolean growBlock(World world, IBlockState blockState, Block block, BlockPos position,
            FluidTank gasHandler, int probability) {
        boolean isValidPlant = false;
        if (!PlantHelper.isCropAtPositionReadyForHarvest(world, position)) {
            isValidPlant = true;
            if (rollForGrow(probability) == true) {
                block.updateTick(world, position, blockState, new Random());
                gasHandler.drain(EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserGasBaseUsage, true);
            }
        }

        return isValidPlant;
    }

    private static boolean rollForGrow(int probability) {
        return (new Random().nextInt(101)) < probability;
    }
}