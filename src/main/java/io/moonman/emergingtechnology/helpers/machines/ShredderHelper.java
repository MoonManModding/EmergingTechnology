package io.moonman.emergingtechnology.helpers.machines;

import java.util.ArrayList;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.custom.loaders.OreDictionaryLoader;
import io.moonman.emergingtechnology.item.items.BlueBulb;
import io.moonman.emergingtechnology.item.items.BulbItem;
import io.moonman.emergingtechnology.item.items.GreenBulb;
import io.moonman.emergingtechnology.item.items.PurpleBulb;
import io.moonman.emergingtechnology.item.items.RedBulb;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
Provides useful methods for the Shredder
*/
public class ShredderHelper {

    public static boolean canShredItem(ItemStack itemStack) {

        int[] oreDictIdsFromItemStack = OreDictionary.getOreIDs(itemStack);

        for (int i = 0; i < oreDictIdsFromItemStack.length; i++) {
            for (int j = 0; j < OreDictionaryLoader.PLASTIC_ORE_IDS.length; j++) {
                if (oreDictIdsFromItemStack[i] == OreDictionaryLoader.PLASTIC_ORE_IDS[j]) {
                    return true;
                }
            }
        }

        return false;
    }
    
}