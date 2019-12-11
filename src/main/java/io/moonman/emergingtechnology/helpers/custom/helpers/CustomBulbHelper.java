package io.moonman.emergingtechnology.helpers.custom.helpers;

import io.moonman.emergingtechnology.helpers.custom.classes.CustomBulb;
import net.minecraft.item.ItemStack;

/**
Provides useful methods for custom bulb manipulation
*/
public class CustomBulbHelper {

    public static CustomBulb[] customBulbs;

    public static CustomBulb[] getCustomBulbs() {
        return CustomBulbHelper.customBulbs;
    }

    public static boolean isItemStackInCustomBulbs(ItemStack itemStack) {
        for (CustomBulb bulb : getCustomBulbs()) {
            if (itemStack.getItem().getRegistryName().toString().equalsIgnoreCase(bulb.name.toString())) {
                return true;
            }
        }
        return false;
    }

    public static int getGrowthProbabilityForBulb(int bulbId) {

        CustomBulb bulb = getCustomBulbById(bulbId);

        if (bulb != null) {
            return bulb.growthModifier;
        } else {
            return 0;
        }

    }

    public static int getWaterUsageForMedium(int mediumId) {

        CustomBulb bulb = getCustomBulbById(mediumId);

        if (bulb != null) {
            return bulb.energyUsage;
        } else {
            return 0;
        }
    }

    public static CustomBulb getCustomBulbById(int mediumId) {

        CustomBulb[] customBulbs = getCustomBulbs();

        for (int i = 0; i < customBulbs.length; i++) {
            if (customBulbs[i].id == mediumId) {
                return customBulbs[i];
            }
        }

        return null;
    }

}