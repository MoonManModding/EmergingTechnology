package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;

public class DiffuserRecipes {

    private static List<IMachineRecipe> diffuserRecipes = new ArrayList<IMachineRecipe>();

    private static boolean removedAll = false;

    private static List<ItemStack> recipesToRemove = new ArrayList<ItemStack>();

    public static List<IMachineRecipe> getRecipes() {
        return diffuserRecipes;
    }

    public static void add(IMachineRecipe recipe) {
        diffuserRecipes.add(recipe);
    }

    public static void removeAll() {
        removedAll = true;
    }

    public static ItemStack removeByOutput(ItemStack itemStack) {
        recipesToRemove.add(itemStack);
        return itemStack;
    }

    public static ItemStack getOutputByItemStack(ItemStack itemStack) {
        ItemStack stack = RecipeBuilder.getOutputForItemStackFromRecipes(itemStack, getRecipes());

        if (stack == null) {
            return ItemStack.EMPTY;
        }

        return stack.copy();
    }

    public static boolean isValidInput(ItemStack itemStack) {
        return getOutputByItemStack(itemStack) != ItemStack.EMPTY;
    }

    public static IMachineRecipe getRecipeByInputItemStack(ItemStack itemStack) {
        return RecipeBuilder.getMatchingRecipe(itemStack, getRecipes());
    }

    public static void build() {

        if (EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.disabled || removedAll)
            return;

        add(new SimpleRecipe(ItemStack.EMPTY, new ItemStack(ModItems.nozzlelong)));
        add(new SimpleRecipe(ItemStack.EMPTY, new ItemStack(ModItems.nozzleprecise)));
        add(new SimpleRecipe(ItemStack.EMPTY, new ItemStack(ModItems.nozzlesmart)));

        for (ItemStack itemStack : recipesToRemove) {
            RecipeBuilder.removeRecipesByOutput(diffuserRecipes, itemStack);
        }

        diffuserRecipes = diffuserRecipes.stream().distinct().collect(Collectors.toList());
    }
}