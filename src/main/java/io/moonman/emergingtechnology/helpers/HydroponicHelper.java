package io.moonman.emergingtechnology.helpers;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;

public class HydroponicHelper {

    private static ItemStack[] validGrowthMedia = new ItemStack[] { new ItemStack(Blocks.DIRT),
            new ItemStack(Blocks.SAND), new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.CLAY),
            new ItemStack(Items.CLAY_BALL) };

    public static ItemStack[] getValidGrowthMedia() {
        return validGrowthMedia;
    };

    public static boolean isItemStackValidGrowthMedia(ItemStack itemStack) {
        ItemStack[] validGrowthMedia = getValidGrowthMedia();

        for (ItemStack growthMedia : validGrowthMedia) {
            if (StackHelper.compareItemStacks(growthMedia, itemStack)) {
                return true;
            }
        }

        return false;
    };

    public static int getGrowthMediaIdFromStack(ItemStack itemStack) {
        if (!isItemStackValidGrowthMedia(itemStack)) {
            return 0;
        }

        ItemStack[] validGrowthMedia = getValidGrowthMedia();

        for (int i = 0; i < validGrowthMedia.length; i++) {
            if (StackHelper.compareItemStacks(itemStack, validGrowthMedia[i])) {
                return i + 1;
            }
        }

        return 0;
    }

    public static int getGrowthProbabilityForMedium(ItemStack itemStack) {
        int mediumId = getGrowthMediaIdFromStack(itemStack);

        switch (mediumId) {
        case 0:
            return 0;
        case 1:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growthDirtModifier;
        case 2:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growthSandModifier;
        case 3:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growthGravelModifier;
        case 4:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growthClayModifier;
        default:
            return 0;
        }
    }

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