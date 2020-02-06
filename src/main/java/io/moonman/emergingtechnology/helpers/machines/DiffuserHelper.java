package io.moonman.emergingtechnology.helpers.machines;

import java.util.Random;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.fluid.FluidStorageHandler;
import io.moonman.emergingtechnology.helpers.PlantHelper;
import io.moonman.emergingtechnology.machines.biomass.BiomassGeneratorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

/**
 * Provides useful methods for the Diffuser
 */
public class DiffuserHelper {

    // public static boolean isItemStackValid(ItemStack itemStack) {
    // return getPlannedStackFromItemStack(itemStack) != null;
    // }

    // public static ItemStack getPlannedStackFromItemStack(ItemStack itemStack) {
    // return RecipeProvider.getOutputForItemStackFromRecipes(itemStack,
    // RecipeProvider.scrubberRecipes);
    // }

    // public static IMachineRecipe getRecipeFromInputItemStack(ItemStack itemStack)
    // {
    // return RecipeProvider.getMatchingRecipe(itemStack,
    // RecipeProvider.scrubberRecipes);
    // }

    public static int boostSurroundingPlants(World world, BlockPos pos, FluidTank gasHandler) {
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
                        // ...do boost
                        block.updateTick(world, position, blockState, new Random());
                        plantsBoosted += 1;
                        gasHandler.drain(EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserGasBaseUsage,
                                true);
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
}