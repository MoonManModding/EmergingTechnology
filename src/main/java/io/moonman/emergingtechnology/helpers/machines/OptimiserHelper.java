package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.machines.classes.OptimiserPacket;
import io.moonman.emergingtechnology.item.electrics.circuits.CircuitBase;
import io.moonman.emergingtechnology.machines.classes.tile.IOptimisableTile;
import io.moonman.emergingtechnology.recipes.classes.OptimiserRecipe;
import io.moonman.emergingtechnology.recipes.machines.OptimiserRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OptimiserHelper {

    public static void pushPacketsToAdjacentMachines(World world, BlockPos pos, OptimiserPacket packet) {

        for (EnumFacing facing : EnumFacing.VALUES) {

            for (int i = 1; i < EmergingTechnologyConfig.ELECTRICS_MODULE.OPTIMISER.range; i++) {

                TileEntity tile = world.getTileEntity(pos.offset(facing, i));

                if (tile == null)
                    continue;
                if (tile instanceof IOptimisableTile == false)
                    continue;

                IOptimisableTile machine = (IOptimisableTile) tile;

                machine.getPacket().merge(packet);
            }
        }
    }

    public static int getCoresFromItemStack(ItemStack itemStack) {

        if (itemStack == null || itemStack.isEmpty()) return 0;

        OptimiserRecipe recipe = OptimiserRecipes.getOptimiserRecipeByInputItemStack(itemStack);

        if (recipe == null) return 0;

        return recipe.getCores();
    }
}