package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BiomassRecipes {

    public final static ItemStack BIOMASS = new ItemStack(ModItems.biomass);
    public final static ItemStack BIOCHAR = new ItemStack(ModItems.biochar);

    private static List<IMachineRecipe> biomassRecipes = new ArrayList<IMachineRecipe>();

    private static boolean removedAll = false;

    private static List<ItemStack> recipesToRemove = new ArrayList<ItemStack>();

    public static List<IMachineRecipe> getRecipes() {
        return biomassRecipes;
    }

    public static void add(IMachineRecipe recipe) {
        biomassRecipes.add(recipe);
    }

    public static void removeAll() {
        removedAll = true;
    }

    public static ItemStack removeByOutput(ItemStack itemStack) {
        recipesToRemove.add(itemStack);
        return itemStack;
    }

    public static ItemStack getOutputByItemStack(ItemStack itemStack) {
        return RecipeBuilder.getOutputForItemStackFromRecipes(itemStack, getRecipes());
    }

    public static boolean isValidInput(ItemStack itemStack) {
        return getOutputByItemStack(itemStack) != null;
    }

    public static IMachineRecipe getRecipeByInputItemStack(ItemStack itemStack) {
        return RecipeBuilder.getMatchingRecipe(itemStack, getRecipes());
    }

    public static void build() {
        
        if (EmergingTechnologyConfig.ELECTRICS_MODULE.BIOMASSGENERATOR.disabled || removedAll) return;

        add(new SimpleRecipe(ModItems.biochar, ModItems.biomass));

        List<ItemStack> biomassOreEntries = OreDictionary.getOres("biomass");

        for (ItemStack itemStack : biomassOreEntries) {
            add(new SimpleRecipe(BIOCHAR.copy(), itemStack.copy()));
        }

        for (ItemStack itemStack : recipesToRemove) {
            RecipeBuilder.removeRecipesByOutput(biomassRecipes, itemStack);
        }
    }
}