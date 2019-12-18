package io.moonman.emergingtechnology.recipes;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

/**
Provides useful methods and machine recipe lists
*/
public class RecipeProvider {

    public static List<SimpleRecipe> shredderRecipes = new ArrayList<>();
    public static List<SimpleRecipe> processorRecipes = new ArrayList<>();
    public static List<SimpleRecipe> cookerRecipes = new ArrayList<>();
    public static List<SimpleRecipe> fabricatorRecipes = new ArrayList<>();

    public static ItemStack getProcessorOutputForItemStack(ItemStack itemStack) {
        for (SimpleRecipe recipe : processorRecipes) {
            if (recipe.getInput().isItemEqual(itemStack)) {
                return recipe.getOutput();
            }
        }

        return null;
    }

    public static ItemStack getShredderOutputForItemStack(ItemStack itemStack) {
        for (SimpleRecipe recipe : shredderRecipes) {
            if (recipe.getInput().isItemEqual(itemStack)) {
                return recipe.getOutput();
            }
        }

        return null;
    }
    
    public static ItemStack getCookerOutputForItemStack(ItemStack itemStack) {
        ItemStack resultStack = FurnaceRecipes.instance().getSmeltingResult(itemStack);

        if (resultStack == null) {
            return null;
        }

        if (resultStack.getItem() instanceof ItemFood) {
            return resultStack;
        }

        return null;
    }
}