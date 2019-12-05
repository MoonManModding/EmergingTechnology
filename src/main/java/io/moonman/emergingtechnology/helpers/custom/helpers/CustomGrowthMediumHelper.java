package io.moonman.emergingtechnology.helpers.custom.helpers;

import io.moonman.emergingtechnology.helpers.custom.CustomGrowthMedium;
import net.minecraft.item.ItemStack;

public class CustomGrowthMediumHelper {

    public static CustomGrowthMedium[] customGrowthMedia;

    public static CustomGrowthMedium[] getCustomGrowthMedia() {
        return CustomGrowthMediumHelper.customGrowthMedia;
    }

    public static boolean isItemStackInCustomGrowthMedia(ItemStack itemStack) {
        for (CustomGrowthMedium medium : getCustomGrowthMedia()) {
            if (itemStack.getItem().getRegistryName().toString().equalsIgnoreCase(medium.name.toString())) {
                return true;
            }
        }
        return false;
    }

    public static int getGrowthProbabilityForMedium(int mediumId) {

        CustomGrowthMedium growthMedium = getCustomGrowthMediumById(mediumId);

        if (growthMedium != null) {
            return growthMedium.growthModifier;
        } else {
            return 0;
        }

    }

    public static int getWaterUsageForMedium(int mediumId) {

        CustomGrowthMedium growthMedium = getCustomGrowthMediumById(mediumId);

        if (growthMedium != null) {
            return growthMedium.waterUsage;
        } else {
            return 0;
        }
    }

    private static CustomGrowthMedium getCustomGrowthMediumById(int mediumId) {

        CustomGrowthMedium[] customGrowthMedia = getCustomGrowthMedia();

        for (int i = 0; i < customGrowthMedia.length; i++) {
            if (customGrowthMedia[i].id == mediumId) {
                return customGrowthMedia[i];
            }
        }

        return null;
    }

}