package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.machines.CookerHelper;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class CookerRecipes {

    private static List<IMachineRecipe> cookerRecipes = new ArrayList<IMachineRecipe>();

    private static boolean removedAll = false;

    private static List<ItemStack> recipesToRemove = new ArrayList<ItemStack>();

    public static List<IMachineRecipe> getRecipes() {
        return cookerRecipes;
    }

    public static void add(IMachineRecipe recipe) {
        cookerRecipes.add(recipe);
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
        return getOutputByItemStack(itemStack) != null;
    }

    public static IMachineRecipe getRecipeByInputItemStack(ItemStack itemStack) {
        return RecipeBuilder.getMatchingRecipe(itemStack, getRecipes());
    }

    public static void build() {

        if (EmergingTechnologyConfig.SYNTHETICS_MODULE.COOKER.disabled || removedAll) return;

        List<ItemStack> validCookedFoodItems = CookerHelper.getValidCookedFoodItems();

        add(new SimpleRecipe(ModItems.syntheticcowcooked, ModItems.syntheticcowraw));
        add(new SimpleRecipe(ModItems.syntheticpigcooked, ModItems.syntheticpigraw));
        add(new SimpleRecipe(ModItems.syntheticchickencooked, ModItems.syntheticchickenraw));
        add(new SimpleRecipe(ModItems.algaebarcooked, ModItems.algaebarraw));

        registerCookerRecipes(validCookedFoodItems);

        for (ItemStack itemStack : recipesToRemove) {
            RecipeBuilder.removeRecipesByOutput(cookerRecipes, itemStack);
        }
    }

    private static void registerCookerRecipes(List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            ItemStack output = FurnaceRecipes.instance().getSmeltingResult(input);
            SimpleRecipe recipe = new SimpleRecipe(output, input);
            add(recipe);
        }
    }

}