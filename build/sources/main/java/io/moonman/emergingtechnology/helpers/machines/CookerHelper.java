package io.moonman.emergingtechnology.helpers.machines;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

/**
 * Provides useful methods for the Cooker
 */
public class CookerHelper {

    public static boolean canCookItemStack(ItemStack itemStack) {
        return getPlannedStackFromItemStack(itemStack) != null;
    }

    public static ItemStack getPlannedStackFromItemStack(ItemStack itemStack) {
        return RecipeBuilder.getCookerOutputForItemStack(itemStack);
    }

    public static List<ItemStack> getValidCookedFoodItems() {

        Map<ItemStack, ItemStack> furnaceRecipes = FurnaceRecipes.instance().getSmeltingList();

        List<ItemStack> validInputs = furnaceRecipes.keySet().stream().filter(x -> x.getItemUseAction() == EnumAction.EAT).collect(Collectors.toList());

        return validInputs;
    }
}