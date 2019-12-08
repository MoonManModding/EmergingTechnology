package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.custom.classes.CustomGrowthMedium;
import io.moonman.emergingtechnology.helpers.custom.helpers.CustomGrowthMediumHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
Provides useful methods for the Hydroponic Grow Bed
*/
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

        if (CustomGrowthMediumHelper.isItemStackInCustomGrowthMedia(itemStack)) {
            return true;
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

        CustomGrowthMedium[] customGrowthMedia = CustomGrowthMediumHelper.getCustomGrowthMedia();

        for (int i = 0; i < customGrowthMedia.length; i++) {

            if (CustomGrowthMediumHelper.isItemStackInCustomGrowthMedia(itemStack)) {
                if (itemStack.getItem().getRegistryName().toString().equalsIgnoreCase(customGrowthMedia[i].name.toString())) {
                    return customGrowthMedia[i].id;
                }
            }
        }

        return 0;
    }

    public static int getGrowthProbabilityForMedium(ItemStack itemStack) {
        int mediumId = getGrowthMediaIdFromStack(itemStack);

        if (mediumId == 0) return 0;
        if (mediumId == 1) return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growthDirtModifier;
        if (mediumId == 2) return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growthSandModifier;
        if (mediumId == 3) return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growthGravelModifier;
        if (mediumId == 4) return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growthClayModifier; 

        return CustomGrowthMediumHelper.getGrowthProbabilityForMedium(mediumId);
    }
}