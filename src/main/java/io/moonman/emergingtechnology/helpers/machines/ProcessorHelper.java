package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.init.ModBlocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
Provides useful methods for the Processor
*/
public class ProcessorHelper {

    public static Item[] processableItems = new Item[] {
        Items.SUGAR
    };

    public static boolean canProcessItem(ItemStack itemStack) {

        int[] oreIds = OreDictionary.getOreIDs(itemStack);

        for (int id : oreIds) {
            if (id == OreDictionary.getOreID("dustPlastic") || id == OreDictionary.getOreID("itemPlastic")) {
                return true;
            }
        }

        return false;
    }

    public static ItemStack getPlannedStackFromItemStack(ItemStack itemStack) {
        return new ItemStack(ModBlocks.plasticblock);
    }
}