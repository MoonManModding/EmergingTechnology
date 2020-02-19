package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ProcessorRecipeBuilder {

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

        if (EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.disabled || removedAll) return;

        registerProcessorRecipes(new ItemStack(ModBlocks.plasticblock), getProcessorBlockItems());
        registerProcessorRecipes(new ItemStack(ModBlocks.clearplasticblock), getProcessorClearBlockItems());
        registerProcessorRecipes(new ItemStack(ModItems.paperpulp), getProcessorPaperItems());
        registerProcessorRecipes(new ItemStack(ModItems.fertilizer), getProcessorFertilizerItems());

        for (ItemStack itemStack : recipesToRemove) {
            RecipeProvider.removeRecipesByOutput(RecipeProvider.processorRecipes, itemStack);
        }
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

    private static List<ItemStack> getProcessorFertilizerItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();

        itemInputs.add(new ItemStack(ModItems.algae, 1));
        itemInputs.add(new ItemStack(Items.EGG, 1));
        itemInputs.add(new ItemStack(Items.BONE, 1));
        itemInputs.add(new ItemStack(Items.LEATHER, 1));
        itemInputs.add(new ItemStack(Items.FISH, 1));
        itemInputs.add(new ItemStack(Items.DYE, 1, 15));

        List<String> oreInputs = new ArrayList<String>();

        oreInputs.add("blockWool");

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