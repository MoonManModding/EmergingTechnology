package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;

public class ProcessorRecipeBuilder {

    private static boolean removedAll = false;

    public static void removeAll() {
        removedAll = true;
    }

    public static void build() {

        if (EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.disabled || removedAll) return;

        registerProcessorRecipes(new ItemStack(ModBlocks.plasticblock), getProcessorBlockItems());
        registerProcessorRecipes(new ItemStack(ModBlocks.clearplasticblock), getProcessorClearBlockItems());
        registerProcessorRecipes(new ItemStack(ModItems.paperpulp), getProcessorPaperItems());
    }

    private static List<ItemStack> getProcessorBlockItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();
        itemInputs.add(new ItemStack(ModItems.shreddedplastic, 4));
        itemInputs.add(new ItemStack(ModItems.shreddedstarch, 4));
        itemInputs.add(new ItemStack(ModBlocks.shreddedplasticblock, 1));
        itemInputs.add(new ItemStack(ModBlocks.shreddedstarchblock, 1));

        List<String> oreInputs = new ArrayList<String>();

        return RecipeBuilder.buildRecipeList(itemInputs, oreInputs);
    }

    private static List<ItemStack> getProcessorClearBlockItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();
        itemInputs.add(new ItemStack(ModItems.shreddedplant, 4));
        itemInputs.add(new ItemStack(ModBlocks.shreddedplantblock, 1));

        List<String> oreInputs = new ArrayList<String>();

        return RecipeBuilder.buildRecipeList(itemInputs, oreInputs);
    }
    
    private static List<ItemStack> getProcessorPaperItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();
        itemInputs.add(new ItemStack(ModItems.shreddedpaper, 1));

        List<String> oreInputs = new ArrayList<String>();

        return RecipeBuilder.buildRecipeList(itemInputs, oreInputs);
    }

    private static void registerProcessorRecipes(ItemStack output, List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            if (RecipeProvider.getOutputForItemStackFromRecipes(input, RecipeProvider.processorRecipes) == null) {
                SimpleRecipe recipe = new SimpleRecipe(output, input);
                RecipeProvider.processorRecipes.add(recipe);
            }
        }
    }

}