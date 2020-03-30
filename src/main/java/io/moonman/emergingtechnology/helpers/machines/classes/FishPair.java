package io.moonman.emergingtechnology.helpers.machines.classes;

import io.moonman.emergingtechnology.helpers.StackHelper;
import net.minecraft.item.ItemStack;

/**
 * Used by Aquaponic Helper to handle fish breeding
 */

public class FishPair {

    public final ItemStack itemStack;
    public int quantity = 0;

    public FishPair(ItemStack stack) {
        this.itemStack = stack.copy();
        this.quantity = stack.getCount();
    }

    public boolean canAdd(ItemStack itemStack) {
        return StackHelper.compareItemStacks(this.itemStack, itemStack);
    }

    public void add(int quantity) {
        this.quantity += quantity;
    }
}