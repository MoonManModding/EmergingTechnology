package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.machines.CookerHelper;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class CookerRecipes {

    private static boolean removedAll = false;

    private static List<ItemStack> recipesToRemove = new ArrayList<ItemStack>();

    public static void removeAll() {
        removedAll = true;
    }

    public static ItemStack removeByOutput(ItemStack itemStack) {
        recipesToRemove.add(itemStack);
        return itemStack;
    }

    public static void build() {

        if (EmergingTechnologyConfig.SYNTHETICS_MODULE.COOKER.disabled || removedAll) return;

        List<ItemStack> validCookedFoodItems = CookerHelper.getValidCookedFoodItems();
        registerCookerRecipes(validCookedFoodItems);

        for (ItemStack itemStack : recipesToRemove) {
            RecipeProvider.removeRecipesByOutput(RecipeProvider.cookerRecipes, itemStack);
        }
    }

    private static void registerCookerRecipes(List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            ItemStack output = FurnaceRecipes.instance().getSmeltingResult(input);
            SimpleRecipe recipe = new SimpleRecipe(output, input);
            RecipeProvider.cookerRecipes.add(recipe);
        }
    }

}