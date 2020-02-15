package io.moonman.emergingtechnology.recipes.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.machines.CollectorHelper;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CollectorRecipeBuilder {

    private static boolean removedAll = false;

    public static void removeAll() {
        removedAll = true;
    }

    public static void build() {
        
        if (EmergingTechnologyConfig.POLYMERS_MODULE.COLLECTOR.disabled || removedAll) return;

        for (ItemStack itemStack : CollectorHelper.getCollectorItems()) {
            RecipeProvider.collectorRecipes.add(new SimpleRecipe(itemStack.getItem(), Items.AIR));
        }
    }
}