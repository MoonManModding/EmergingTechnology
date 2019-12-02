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
            if (compareItemStacks(growthMedia, itemStack)) {
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
            if (compareItemStacks(itemStack, validGrowthMedia[i])) {
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
            return EmergingTechnologyConfig.growthDirtModifier;
        case 2:
            return EmergingTechnologyConfig.growthSandModifier;
        case 3:
            return EmergingTechnologyConfig.growthGravelModifier;
        case 4:
            return EmergingTechnologyConfig.growthClayModifier;
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

    public static boolean isItemStackEmpty(ItemStack stack) {
        return compareItemStacks(stack, new ItemStack(Items.AIR)) || stack.isEmpty();
    }

    public static boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem()
                && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
}