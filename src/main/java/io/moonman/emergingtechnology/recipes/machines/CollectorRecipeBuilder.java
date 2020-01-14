package io.moonman.emergingtechnology.recipes.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.init.Items;

public class CollectorRecipeBuilder {

    public static void build() {
        
        if (EmergingTechnologyConfig.POLYMERS_MODULE.COLLECTOR.disabled) return;

        RecipeProvider.collectorRecipes.add(new SimpleRecipe(Items.AIR, ModItems.plasticwaste));
        RecipeProvider.collectorRecipes.add(new SimpleRecipe(Items.AIR, ModItems.paperwaste));
    }
}