package io.moonman.emergingtechnology.recipes.machines;

import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.machines.CookerHelper;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class CookerRecipeBuilder {

    public static void build() {

        if (EmergingTechnologyConfig.SYNTHETICS_MODULE.COOKER.disabled) return;

        List<ItemStack> validCookedFoodItems = CookerHelper.getValidCookedFoodItems();
        registerCookerRecipes(validCookedFoodItems);
    }

    private static void registerCookerRecipes(List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            ItemStack output = FurnaceRecipes.instance().getSmeltingResult(input);
            SimpleRecipe recipe = new SimpleRecipe(output, input);
            RecipeProvider.cookerRecipes.add(recipe);
        }
    }

}