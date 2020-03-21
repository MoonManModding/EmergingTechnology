package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.providers.ModMediumProvider;
import io.moonman.emergingtechnology.providers.classes.ModMedium;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HydroponicRecipes {

    private static List<IMachineRecipe> hydroponicRecipes = new ArrayList<IMachineRecipe>();

    private static boolean removedAll = false;

    private static List<ItemStack> recipesToRemove = new ArrayList<ItemStack>();

    public static List<IMachineRecipe> getRecipes() {
        return hydroponicRecipes;
    }

    public static void add(IMachineRecipe recipe) {
        hydroponicRecipes.add(recipe);
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
        return getOutputByItemStack(itemStack) != ItemStack.EMPTY;
    }

    public static IMachineRecipe getRecipeByInputItemStack(ItemStack itemStack) {
        return RecipeBuilder.getMatchingRecipe(itemStack, getRecipes());
    }

    public static void build() {
        
        if (EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.disabled || removedAll) return;

        for (ModMedium medium : ModMediumProvider.getMedia()) {
            ItemStack itemStack = new ItemStack(Item.getByNameOrId(medium.name));

            if (!itemStack.isEmpty() && itemStack != null) {
                add(new SimpleRecipe(ItemStack.EMPTY, itemStack));
            }
        }

        for (ItemStack itemStack : recipesToRemove) {
            RecipeBuilder.removeRecipesByOutput(hydroponicRecipes, itemStack);
        }

        hydroponicRecipes = hydroponicRecipes.stream().distinct()
                .collect(Collectors.toList());
    }
}