package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;

/**
Provides useful methods for the Processor
*/
public class ProcessorHelper {

    public static boolean canProcessItemStack(ItemStack itemStack) {
        return getPlannedStackFromItemStack(itemStack) != null;
    }

    public static ItemStack getPlannedStackFromItemStack(ItemStack itemStack) {
        return RecipeProvider.getOutputForItemStackFromRecipes(itemStack, RecipeProvider.processorRecipes);
    }

    public static SimpleRecipe getRecipeFromInputItemStack(ItemStack itemStack) {
        return RecipeProvider.getMatchingRecipe(itemStack, RecipeProvider.processorRecipes);
    }
}