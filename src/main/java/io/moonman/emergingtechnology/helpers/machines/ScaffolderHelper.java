package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import net.minecraft.item.ItemStack;

/**
 * Provides useful methods for the Scaffolder
 */
public class ScaffolderHelper {

    public static boolean isItemStackValidSample(ItemStack itemStack) {
        return getPlannedStackFromItemStack(itemStack) != null;
    }

    public static boolean isItemStackValidScaffold(ItemStack itemStack) {
        return StackHelper.compareItemStacks(itemStack, new ItemStack(ModItems.plastictissuescaffold));
    }

    public static ItemStack getPlannedStackFromItemStack(ItemStack itemStack) {
        return RecipeProvider.getOutputForItemStackFromRecipes(itemStack, RecipeProvider.scaffolderRecipes);
    }
}