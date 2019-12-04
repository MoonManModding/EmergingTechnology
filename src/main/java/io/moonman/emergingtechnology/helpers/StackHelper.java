package io.moonman.emergingtechnology.helpers;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class StackHelper {

    public static boolean isItemStackEmpty(ItemStack stack) {
        return compareItemStacks(stack, new ItemStack(Items.AIR)) || stack.isEmpty();
    }

    public static boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem()
                && (stack2.getMetadata() == OreDictionary.WILDCARD_VALUE || stack2.getMetadata() == stack1.getMetadata());
    }
}