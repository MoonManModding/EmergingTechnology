package io.moonman.emergingtechnology.recipes.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;

public class AlgaeBioreactorRecipeBuilder {

    public static void build() {
        
        if (EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.disabled) return;

        RecipeProvider.algaeBioreactorRecipes.add(new SimpleRecipe(new ItemStack(ModItems.algae, 8), "slimeball"));
        RecipeProvider.algaeBioreactorRecipes.add(new SimpleRecipe(new ItemStack(ModItems.algae, 2), new ItemStack(ModItems.algae, 1)));
    }
}