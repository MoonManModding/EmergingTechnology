package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import net.minecraft.item.ItemStack;

/**
 * Provides useful methods for the Fabricator
 */
public class FabricatorHelper {

    public static final ItemStack FILAMENT = new ItemStack(ModItems.filament);

    public static boolean isValidFilamentItemStack(ItemStack itemStack) {
        return StackHelper.compareItemStacks(FILAMENT, itemStack);
    }

    public static FabricatorRecipe getFabricatorRecipeByIndex(int id) {
        return RecipeProvider.getFabricatorRecipeByIndex(id);
    }
    
}