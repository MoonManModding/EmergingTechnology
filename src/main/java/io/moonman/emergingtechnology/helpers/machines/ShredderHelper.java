package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.recipes.RecipeHandler;
import net.minecraft.item.ItemStack;

/**
 * Provides useful methods for the Shredder
 */
public class ShredderHelper {

    public static boolean canShredItemStack(ItemStack itemStack) {
        return getPlannedStackFromItemStack(itemStack) != null;
    }

    public static ItemStack getPlannedStackFromItemStack(ItemStack itemStack) {
        return RecipeHandler.getShredderOutputForItemStack(itemStack);
    }
}