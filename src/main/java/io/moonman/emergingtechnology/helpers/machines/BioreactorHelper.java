package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.recipes.RecipeProvider;
import net.minecraft.item.ItemStack;

/**
 * Provides useful methods for the Tissue Bioreactor
 */
public class BioreactorHelper {

    public static boolean isItemStackValid(ItemStack itemStack) {
        return getPlannedStackFromItemStack(itemStack) != null;
    }

    public static ItemStack getPlannedStackFromItemStack(ItemStack itemStack) {
        return RecipeProvider.getOutputForItemStackFromRecipes(itemStack, RecipeProvider.bioreactorRecipes);
    }
}