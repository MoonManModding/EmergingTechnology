package io.moonman.emergingtechnology.helpers.machines;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
Provides useful methods for the Processor
*/
public class ProcessorHelper {

    public static Item[] processableItems = new Item[] {
        Items.SUGAR
    };

    public static boolean canProcessItem(ItemStack itemStack) {

        if (itemStack.getItem() == Items.SUGAR) return true;

        return false;
    }

    public static ItemStack getPlannedStackFromItemStack(ItemStack itemStack) {
        return new ItemStack(Items.PAPER);
    }
}