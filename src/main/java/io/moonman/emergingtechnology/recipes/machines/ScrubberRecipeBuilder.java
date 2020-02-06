package io.moonman.emergingtechnology.recipes.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;

public class ScrubberRecipeBuilder {

    public static void build() {

        if (EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.disabled) return;

        RecipeProvider.scrubberRecipes.add(new SimpleRecipe(ItemStack.EMPTY, new ItemStack(ModItems.biochar)));
    }
}