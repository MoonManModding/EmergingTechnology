package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.machines.FabricatorHelper;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import net.minecraft.item.ItemStack;

public class FabricatorRecipes {

    private static List<IMachineRecipe> fabricatorRecipes = new ArrayList<IMachineRecipe>();

    private static boolean removedAll = false;

    private static List<ItemStack> recipesToRemove = new ArrayList<ItemStack>();

    public static List<IMachineRecipe> getRecipes() {
        return fabricatorRecipes;
    }

    public static void add(IMachineRecipe recipe) {
        fabricatorRecipes.add(recipe);
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

        if (EmergingTechnologyConfig.POLYMERS_MODULE.FABRICATOR.disabled || removedAll) return;

        buildFabricatorRecipeList();

        for (ItemStack itemStack : recipesToRemove) {
            RecipeBuilder.removeRecipesByOutput(fabricatorRecipes, itemStack);
        }
    }

    private static void buildFabricatorRecipeList() {

        FabricatorRecipe recipe = new FabricatorRecipe(1, new ItemStack(ModBlocks.plasticblock), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe2 = new FabricatorRecipe(2, new ItemStack(ModItems.plasticrod, 4), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe3 = new FabricatorRecipe(3, new ItemStack(ModItems.plasticsheet, 2), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe4 = new FabricatorRecipe(4, new ItemStack(ModBlocks.machinecase), FabricatorHelper.getFilamentWithAmount(2));
        FabricatorRecipe recipe5 = new FabricatorRecipe(5, new ItemStack(ModBlocks.frame), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe6 = new FabricatorRecipe(6, new ItemStack(ModBlocks.clearplasticblock), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe7 = new FabricatorRecipe(7, new ItemStack(ModBlocks.ladder, 2), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe8 = new FabricatorRecipe(8, new ItemStack(ModItems.plastictissuescaffold, 2), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe9 = new FabricatorRecipe(9, new ItemStack(ModItems.emptysyringe, 3), FabricatorHelper.getFilamentWithAmount(2));
        FabricatorRecipe recipe10 = new FabricatorRecipe(10, new ItemStack(ModItems.turbine, 3), FabricatorHelper.getFilamentWithAmount(2));
        FabricatorRecipe recipe11 = new FabricatorRecipe(11, new ItemStack(ModItems.nozzlecomponent, 1), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe12 = new FabricatorRecipe(12, new ItemStack(ModItems.plasticbottle, 1), FabricatorHelper.getFilamentWithAmount(1));
        add(recipe);
        add(recipe2);
        add(recipe3);
        add(recipe4);
        add(recipe5);
        add(recipe6);
        add(recipe7);
        add(recipe8);
        add(recipe9);
        add(recipe10);
        add(recipe11);
        add(recipe12);
    }
}