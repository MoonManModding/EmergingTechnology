package io.moonman.emergingtechnology.helpers.machines;

import java.util.Random;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.PlantHelper;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.item.hydroponics.nozzles.NozzleBase;
import io.moonman.emergingtechnology.item.hydroponics.nozzles.NozzleLong;
import io.moonman.emergingtechnology.item.hydroponics.nozzles.NozzlePrecise;
import io.moonman.emergingtechnology.item.hydroponics.nozzles.NozzleWide;
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

        if (nozzle instanceof NozzleWide) return 1;
        if (nozzle instanceof NozzleLong) return 2;
        if (nozzle instanceof NozzlePrecise) return 3;

        return 0;
    }

    public static int boostSurroundingPlants(World world, BlockPos pos, FluidTank gasHandler, int probability, int nozzleId) {
        int plantsBoosted = 0;
        int range = EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserBaseRange;

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

                    // If plant is not fully grown...
                    if (!PlantHelper.isCropAtPositionReadyForHarvest(world, position)) {

                        // If roll is successful
                        if (rollForGrow(probability) == true) {
                            // ...do boost
                            block.updateTick(world, position, blockState, new Random());
                            plantsBoosted += 1;
                            gasHandler.drain(EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserGasBaseUsage,
                                    true);
                        }
                    }

                } else if (blockState.getBlock() == Blocks.AIR) {
                    // Pass through air
                    continue;
                } else {
                    // Blocked by object
                    break;
                }
            }
        }

        return plantsBoosted;
    }

    private static void growBlock(World world, BlockPos pos, FluidTank gasHandler, int probability) {

    }

    private static boolean rollForGrow(int probability) {
        int random = new Random().nextInt(101);

        return random < probability;
    }
}