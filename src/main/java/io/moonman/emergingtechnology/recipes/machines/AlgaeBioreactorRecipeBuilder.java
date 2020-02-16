package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;

public class AlgaeBioreactorRecipeBuilder {

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
        
        if (EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.disabled || removedAll) return;

        
        RecipeProvider.algaeBioreactorRecipes.add(new SimpleRecipe(new ItemStack(ModItems.algae, 2), new ItemStack(ModItems.algae, 1)));

        for (ItemStack itemStack : getSlimeItems()) {
            RecipeProvider.algaeBioreactorRecipes.add(new SimpleRecipe(new ItemStack(ModItems.algae, 4), itemStack));
        }

        for (ItemStack itemStack : recipesToRemove) {
            RecipeProvider.removeRecipesByOutput(RecipeProvider.algaeBioreactorRecipes, itemStack);
        }
    }

    private static List<ItemStack> getSlimeItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();

        List<String> oreInputs = new ArrayList<String>();
        oreInputs.add("slimeball");

        List<ItemStack> inputs = RecipeBuilder.buildRecipeList(itemInputs, oreInputs);

        for (ItemStack itemStack : inputs) {
            itemStack.setCount(1);
        }

        return inputs;
    }
}