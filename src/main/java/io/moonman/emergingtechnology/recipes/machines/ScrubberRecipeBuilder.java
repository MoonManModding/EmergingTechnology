package io.moonman.emergingtechnology.recipes.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;

public class ScrubberRecipeBuilder {

    private static boolean removedAll = false;

    public static void removeAll() {
        removedAll = true;
    }

    public static void build() {

        if (EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.disabled || removedAll) return;

        RecipeProvider.scrubberRecipes.add(new SimpleRecipe(ItemStack.EMPTY, new ItemStack(ModItems.biochar)));
    }
}