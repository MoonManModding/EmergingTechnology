package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class ScrubberRecipes {

    private static List<IMachineRecipe> scrubberRecipes = new ArrayList<IMachineRecipe>();

    private static boolean removedAll = false;

    private static List<ItemStack> recipesToRemove = new ArrayList<ItemStack>();

    private static List<String> gasNames = new ArrayList<String>();

    public static void add(IMachineRecipe recipe) {
        scrubberRecipes.add(recipe);
    }

    public static List<IMachineRecipe> getRecipes() {
        return scrubberRecipes;
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

        if (EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.disabled || removedAll) return;

        gasNames.add("carbondioxide");
        gasNames.add("carbon_dioxide");
        gasNames.add("co2");

        scrubberRecipes.add(new SimpleRecipe(ItemStack.EMPTY, new ItemStack(ModItems.biochar)));

        for (ItemStack itemStack : recipesToRemove) {
            RecipeBuilder.removeRecipesByOutput(scrubberRecipes, itemStack);
        }
    }

    public static boolean isValidGas(Fluid fluid) {
        if (fluid == null) return false;

        for (String name : gasNames) {
            if (name.equalsIgnoreCase(fluid.getName())) {
                return true;
            }
        }

        return false;
    }
}