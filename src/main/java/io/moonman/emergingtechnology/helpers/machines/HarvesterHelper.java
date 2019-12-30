package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import net.minecraft.item.ItemStack;

/**
 * Provides useful methods for the Harvester
 */
public class HarvesterHelper {

    public static final ItemStack FILAMENT = new ItemStack(ModItems.filament);

    public static boolean isItemStackValid(ItemStack itemStack) {
        return RecipeProvider.getFabricatorOutputForItemStack(itemStack) != null;
    }

    public static FabricatorRecipe getFabricatorRecipeByIndex(int id) {
        return RecipeProvider.getFabricatorRecipeByIndex(id);
    }

    public static ItemStack getFilamentWithAmount(int amount) {
        return new ItemStack(ModItems.filament, amount);
    }
    
}