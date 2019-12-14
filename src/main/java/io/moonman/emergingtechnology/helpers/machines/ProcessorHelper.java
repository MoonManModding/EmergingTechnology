package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.Reference;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
Provides useful methods for the Processor
*/
public class ProcessorHelper {

    public static boolean canProcessItemStack(ItemStack itemStack) {

        int[] oreIds = OreDictionary.getOreIDs(itemStack);

        for (int id : oreIds) {
            if (id == OreDictionary.getOreID("dustPlastic") || id == OreDictionary.getOreID("itemPlastic")) {
                if (itemStack.getCount() >= Reference.PROCESSOR_REQUIRED_INPUT_COUNT) {
                    return true;
                }
            }
        }

        return false;
    }

    public static ItemStack getPlannedStackFromItemStack(ItemStack itemStack) {
        return new ItemStack(ModBlocks.plasticblock);
    }
}