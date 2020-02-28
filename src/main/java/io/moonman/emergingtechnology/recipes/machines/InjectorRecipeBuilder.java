package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.integration.ModLoader;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InjectorRecipeBuilder {

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

        if (EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.disabled || removedAll)
            return;

        List<ItemStack> itemInputs = new ArrayList<ItemStack>();
        itemInputs.add(new ItemStack(ModItems.fertilizer));

        if (ModLoader.isAlchemistryLoaded()) {
            itemInputs.add(new ItemStack(Item.getByNameOrId("alchemistry:fertilizer")));
        }

        List<String> oreInputs = new ArrayList<String>();
        oreInputs.add("fertilizer");

        List<ItemStack> inputs = RecipeBuilder.buildRecipeList(itemInputs, oreInputs);

        registerFertilizerRecipes(new ItemStack(Blocks.DIRT), inputs);

        for (ItemStack itemStack : recipesToRemove) {
            RecipeProvider.removeRecipesByOutput(RecipeProvider.injectorRecipes, itemStack);
        }
    }

    private static void registerFertilizerRecipes(ItemStack output, List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            if (RecipeProvider.getOutputForItemStackFromRecipes(input, RecipeProvider.injectorRecipes) == null) {
                SimpleRecipe recipe = new SimpleRecipe(output, input);
                RecipeProvider.injectorRecipes.add(recipe);
            }
        }

        RecipeProvider.injectorRecipes = RecipeProvider.injectorRecipes.stream().distinct()
                .collect(Collectors.toList());
    }
}