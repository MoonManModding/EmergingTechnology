package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.item.electrics.circuits.CircuitAdvanced;
import io.moonman.emergingtechnology.item.electrics.circuits.CircuitBasic;
import io.moonman.emergingtechnology.item.electrics.circuits.CircuitSuperior;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.classes.OptimiserRecipe;
import net.minecraft.item.ItemStack;

public class OptimiserRecipes {

    private static List<IMachineRecipe> optimiserRecipes = new ArrayList<IMachineRecipe>();

    private static boolean removedAll = false;

    private static List<ItemStack> recipesToRemove = new ArrayList<ItemStack>();

    public static List<IMachineRecipe> getRecipes() {
        return optimiserRecipes;
    }

    public static void add(OptimiserRecipe recipe) {
        optimiserRecipes.add(recipe);
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

    public static OptimiserRecipe getOptimiserRecipeByInputItemStack(ItemStack itemStack) {
        return (OptimiserRecipe) RecipeBuilder.getMatchingRecipe(itemStack, getRecipes());
    }

    public static void build() {
        
        if (EmergingTechnologyConfig.ELECTRICS_MODULE.OPTIMISER.disabled || removedAll) return;

        optimiserRecipes.add(new OptimiserRecipe(new ItemStack(ModItems.circuitbasic), CircuitBasic.baseCores));
        optimiserRecipes.add(new OptimiserRecipe(new ItemStack(ModItems.circuitadvanced), CircuitAdvanced.baseCores));
        optimiserRecipes.add(new OptimiserRecipe(new ItemStack(ModItems.circuitsuperior), CircuitSuperior.baseCores));

        for (ItemStack itemStack : recipesToRemove) {
            RecipeBuilder.removeRecipesByOutput(optimiserRecipes, itemStack);
        }

        optimiserRecipes = optimiserRecipes.stream().distinct()
                .collect(Collectors.toList());
    }
}