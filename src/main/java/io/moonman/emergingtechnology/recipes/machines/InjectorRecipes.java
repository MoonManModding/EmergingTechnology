package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.integration.ModLoader;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InjectorRecipes {

    private static List<IMachineRecipe> injectorRecipes = new ArrayList<IMachineRecipe>();

    private static boolean removedAll = false;

    private static List<ItemStack> recipesToRemove = new ArrayList<ItemStack>();

    public static List<IMachineRecipe> getRecipes() {
        return injectorRecipes;
    }

    public static void add(IMachineRecipe recipe) {
        injectorRecipes.add(recipe);
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
            RecipeBuilder.removeRecipesByOutput(injectorRecipes, itemStack);
        }
    }

    private static void registerFertilizerRecipes(ItemStack output, List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            if (RecipeBuilder.getOutputForItemStackFromRecipes(input, injectorRecipes) == null) {
                SimpleRecipe recipe = new SimpleRecipe(output, input);
                add(recipe);
            }
        }

        injectorRecipes = injectorRecipes.stream().distinct()
                .collect(Collectors.toList());
    }
}