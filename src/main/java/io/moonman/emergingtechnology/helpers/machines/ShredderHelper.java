package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.helpers.custom.loaders.OreDictionaryLoader;
import io.moonman.emergingtechnology.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
Provides useful methods for the Shredder
*/
public class ShredderHelper {

    public static Item[] shreddableItems = new Item[] {
        Items.REEDS
    };

    public static boolean canShredItem(ItemStack itemStack) {

        int[] oreDictIdsFromItemStack = OreDictionary.getOreIDs(itemStack);

        for (int i = 0; i < oreDictIdsFromItemStack.length; i++) {
            for (int j = 0; j < OreDictionaryLoader.PLASTIC_ORE_IDS.length; j++) {
                if (oreDictIdsFromItemStack[i] == OreDictionaryLoader.PLASTIC_ORE_IDS[j]) {
                    return true;
                }
            }
        }

        if (itemStack.getItem() == Items.REEDS) return true;

        return false;
    }

    public static ItemStack getPlannedStackFromItemStack(ItemStack itemStack) {
        return new ItemStack(ModItems.shreddedplastic);
    }
}