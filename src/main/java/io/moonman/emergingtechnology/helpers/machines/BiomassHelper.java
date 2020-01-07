package io.moonman.emergingtechnology.helpers.machines;

import java.util.List;

import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Provides useful methods for the Biomass Generator
 */
public class BiomassHelper {

    public final static ItemStack BIOMASS = new ItemStack(ModItems.biomass);

    public static boolean isItemStackValid(ItemStack itemStack) {


        List<ItemStack> validItemStacks = OreDictionary.getOres("biomass");

        for (ItemStack validItemStack: validItemStacks) {
            if (StackHelper.compareItemStacks(validItemStack, itemStack)) {
                return true;
            }
        }

        return StackHelper.compareItemStacks(itemStack, BIOMASS);
    }
}