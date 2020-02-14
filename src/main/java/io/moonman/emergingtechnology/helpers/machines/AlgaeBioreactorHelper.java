package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import net.minecraft.item.ItemStack;

/**
Provides useful methods for the Algae Bioreactor
*/
public class AlgaeBioreactorHelper {

    public static boolean canProcessItemStack(ItemStack itemStack) {
        return getPlannedStackFromItemStack(itemStack) != null;
    }

    public static ItemStack getPlannedStackFromItemStack(ItemStack itemStack) {
        return RecipeProvider.getOutputForItemStackFromRecipes(itemStack, RecipeProvider.algaeBioreactorRecipes);
    }

    public static IMachineRecipe getRecipeFromInputItemStack(ItemStack itemStack) {
        return RecipeProvider.getMatchingRecipe(itemStack, RecipeProvider.algaeBioreactorRecipes);
    }
}