package io.moonman.emergingtechnology.recipes.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.custom.wrappers.CustomRecipeWrapper;
import io.moonman.emergingtechnology.helpers.machines.FabricatorHelper;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FabricatorRecipeBuilder {

    public static void build() {

        if (EmergingTechnologyConfig.POLYMERS_MODULE.FABRICATOR.disabled) return;

        buildFabricatorRecipeList();
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
        RecipeProvider.fabricatorRecipes.add(recipe);
        RecipeProvider.fabricatorRecipes.add(recipe2);
        RecipeProvider.fabricatorRecipes.add(recipe3);
        RecipeProvider.fabricatorRecipes.add(recipe4);
        RecipeProvider.fabricatorRecipes.add(recipe5);
        RecipeProvider.fabricatorRecipes.add(recipe6);
        RecipeProvider.fabricatorRecipes.add(recipe7);
        RecipeProvider.fabricatorRecipes.add(recipe8);
        RecipeProvider.fabricatorRecipes.add(recipe9);
        RecipeProvider.fabricatorRecipes.add(recipe10);
        RecipeProvider.fabricatorRecipes.add(recipe11);
        addCustomFabricatorRecipes(12);
    }

    private static void addCustomFabricatorRecipes(int id) {
        for (CustomRecipeWrapper wrapper: RecipeProvider.customRecipes.fabricator) {
            ItemStack inputStack = GameRegistry.makeItemStack(wrapper.input, wrapper.inputMeta, wrapper.inputCount, wrapper.inputNBTData);
            ItemStack outputStack = GameRegistry.makeItemStack(wrapper.output, wrapper.outputMeta, wrapper.outputCount, wrapper.outputNBTData);
            FabricatorRecipe recipe = new FabricatorRecipe(id, outputStack, inputStack);
            RecipeProvider.fabricatorRecipes.add(recipe);
            id++;
        }
    }

}