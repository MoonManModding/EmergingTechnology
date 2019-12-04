package io.moonman.emergingtechnology.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.IPlantable;

public class PlantHelper {
    
    public static boolean isPlantItem(Item item) {
        return item instanceof IPlantable || item instanceof IGrowable || isItemInOverride(item);
    }

    public static boolean isPlantBlock(Block block) {
        return block instanceof IPlantable || block instanceof IGrowable;
    }

    private static boolean isItemInOverride(Item item) {
        if(item == Items.REEDS) return true;
        return false;
    }
}