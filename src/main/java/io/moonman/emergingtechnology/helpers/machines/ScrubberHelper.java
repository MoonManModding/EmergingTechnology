package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.machines.biomass.BiomassGenerator;
import io.moonman.emergingtechnology.machines.biomass.BiomassGeneratorTileEntity;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Provides useful methods for the Scrubber
 */
public class ScrubberHelper {

    public static boolean isItemStackValid(ItemStack itemStack) {
        return getPlannedStackFromItemStack(itemStack) != null;
    }

    public static ItemStack getPlannedStackFromItemStack(ItemStack itemStack) {
        return RecipeProvider.getOutputForItemStackFromRecipes(itemStack, RecipeProvider.scrubberRecipes);
    }

    public static IMachineRecipe getRecipeFromInputItemStack(ItemStack itemStack) {
        return RecipeProvider.getMatchingRecipe(itemStack, RecipeProvider.scrubberRecipes);
    }

    public static int getGasGenerated(World world, BlockPos pos) {
        int bonus = getSurroundingPollutionSourcesBonus(world, pos);
        return EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.scrubberGasGenerated + bonus;
    }

    private static int getSurroundingPollutionSourcesBonus(World world, BlockPos pos) {
        BlockPos startPos = pos.add(-2, -2, -2);

        int pollutionBonus = 0;

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                for (int z = 0; z < 5; z++) {
                    pollutionBonus += getPollutionBonusForBlockState(world, startPos.add(x, y, z));
                }
            }
        }

        return pollutionBonus;
    }

    private static int getPollutionBonusForBlockState(World world, BlockPos pos) {
        int pollutionBonus = 0;

        IBlockState blockState = world.getBlockState(pos);
        TileEntity tileEntity = world.getTileEntity(pos);

        if (blockState.getBlock() == Blocks.LIT_FURNACE) {
            pollutionBonus = 100;
        }
        
        if (tileEntity instanceof BiomassGeneratorTileEntity) {
            BiomassGeneratorTileEntity generator = (BiomassGeneratorTileEntity) tileEntity;
            if (generator.getProgress() > 0) {
                pollutionBonus = 50;
            }
        }

        return pollutionBonus;
    }
}