package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import net.minecraft.item.ItemStack;

/**
 * Provides useful methods for the Fabricator
 */
public class FabricatorHelper {

    public static final ItemStack FILAMENT = new ItemStack(ModItems.filament);

    public static boolean isValidItemStack(ItemStack itemStack) {
        return RecipeProvider.getFabricatorOutputForItemStack(itemStack) != null;
    }

    public static IMachineRecipe getFabricatorRecipeByIndex(int id) {
        return RecipeProvider.getFabricatorRecipeByIndex(id);
    }

    public static ItemStack getFilamentWithAmount(int amount) {
        return new ItemStack(ModItems.filament, amount);
    }
    
}