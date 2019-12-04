package io.moonman.emergingtechnology.helpers;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.item.items.BlueBulb;
import io.moonman.emergingtechnology.item.items.BulbItem;
import io.moonman.emergingtechnology.item.items.GreenBulb;
import io.moonman.emergingtechnology.item.items.PurpleBulb;
import io.moonman.emergingtechnology.item.items.RedBulb;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LightHelper {

    public static final int BULB_COUNT = 4;

    public static boolean isItemStackValidBulb(ItemStack itemStack) {

        if (StackHelper.isItemStackEmpty(itemStack)) {
            return false;
        }

        if (itemStack.getItem() instanceof BulbItem) {
            return true;
        }

        return false;
    };

    public static int getBulbTypeIdFromStack(ItemStack itemStack) {
        if (isItemStackValidBulb(itemStack)) {

            Item item = itemStack.getItem();

            if (item instanceof RedBulb) {
                return 1;
            } else if (item instanceof GreenBulb) {
                return 2;
            } else if (item instanceof BlueBulb) {
                return 3;
            } else if (item instanceof PurpleBulb) {
                return 4;
            }
        }
        return 0;
    }

    public static int getGrowthProbabilityForBulbById(int bulbTypeId) {

        switch (bulbTypeId) {
        case 0:
            return 0;
        case 1:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthRedBulbModifier;
        case 2:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthGreenBulbModifier;
        case 3:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthBlueBulbModifier;
        case 4:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthPurpleBulbModifier;
        default:
            return 0;
        }
    }
    
    public static int getEnergyUsageModifierForBulbById(int bulbTypeId) {

        switch (bulbTypeId) {
        case 0:
            return 1;
        case 1:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyRedBulbModifier;
        case 2:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyGreenBulbModifier;
        case 3:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyBlueBulbModifier;
        case 4:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyPurpleBulbModifier;
        default:
            return 1;
        }
    }
}