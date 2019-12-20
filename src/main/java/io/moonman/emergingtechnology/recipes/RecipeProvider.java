package io.moonman.emergingtechnology.recipes;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
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
    public static List<FabricatorRecipe> fabricatorRecipes = new ArrayList<>();

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

    public static ItemStack getFabricatorOutputForItemStack(ItemStack itemStack) {
        for (FabricatorRecipe recipe : fabricatorRecipes) {
            if (recipe.getInput().isItemEqual(itemStack)) {
                return recipe.getOutput();
            }
        }

        return null;
    }

    public static ArrayList<ArrayList<FabricatorRecipe>> getSplitRecipes(int slots) {
        ArrayList<ArrayList<FabricatorRecipe>> splitRecipes = new ArrayList<ArrayList<FabricatorRecipe>>();

        int listCount = getRecipePagesCount(slots);

        for (int i = 0; i < listCount; i++) {
            ArrayList<FabricatorRecipe> subList = new ArrayList<FabricatorRecipe>(fabricatorRecipes.subList(i * 9, i + 9));
            splitRecipes.add(subList);
        }

        return splitRecipes;
    }

    public static int getRecipePagesCount(int slots) {
        return (int) Math.ceil((double)fabricatorRecipes.size() / slots);
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