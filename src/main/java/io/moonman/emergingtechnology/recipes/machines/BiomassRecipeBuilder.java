package io.moonman.emergingtechnology.recipes.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;

public class BiomassRecipeBuilder {

    private static boolean removedAll = false;

    public static void removeAll() {
        removedAll = true;
    }

    public static void build() {
        
        if (EmergingTechnologyConfig.ELECTRICS_MODULE.BIOMASSGENERATOR.disabled || removedAll) return;

        RecipeProvider.biomassRecipes.add(new SimpleRecipe(ModItems.biochar, ModItems.biomass));
    }
}