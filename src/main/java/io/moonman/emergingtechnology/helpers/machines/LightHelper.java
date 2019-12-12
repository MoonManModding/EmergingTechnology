package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.config.hydroponics.enums.BulbTypeEnum;
import io.moonman.emergingtechnology.config.hydroponics.enums.CropTypeEnum;
import io.moonman.emergingtechnology.config.hydroponics.interfaces.IIdealBoostsConfiguration;
import io.moonman.emergingtechnology.helpers.PlantHelper;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.custom.loaders.CustomBulbLoader;
import io.moonman.emergingtechnology.helpers.custom.providers.ModBulbProvider;
import io.moonman.emergingtechnology.helpers.custom.providers.ModMediumProvider;
import io.moonman.emergingtechnology.item.hydroponics.BlueBulb;
import io.moonman.emergingtechnology.item.hydroponics.BulbItem;
import io.moonman.emergingtechnology.item.hydroponics.GreenBulb;
import io.moonman.emergingtechnology.item.hydroponics.PurpleBulb;
import io.moonman.emergingtechnology.item.hydroponics.RedBulb;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
Provides useful methods for the Hydroponic Grow Light
*/
public class LightHelper {

    public static boolean isGlowstonePowered(ItemStack itemStack) {
        return ModBulbProvider.getBulbIdFromItemStack(itemStack) == 5;
    }

    public static boolean isItemStackValidBulb(ItemStack itemStack) {
        return ModBulbProvider.bulbExists(itemStack);
    };

    public static int getBulbTypeIdFromStack(ItemStack itemStack) {
        return ModBulbProvider.getBulbIdFromItemStack(itemStack);
    }

    public static int getGrowthProbabilityForBulbById(int id) {

        switch (id) {
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
            return ModBulbProvider.getGrowthProbabilityForBulbById(id);
        }
    }

    public static BulbTypeEnum getBulbTypeEnumFromId(int id) {
        switch (id) {
        case 1:
            return BulbTypeEnum.RED;
        case 2:
            return BulbTypeEnum.GREEN;
        case 3:
            return BulbTypeEnum.BLUE;
        case 4:
            return BulbTypeEnum.UV;
        default:
            return BulbTypeEnum.INVALID;
        }
    }

    public static int getBulbColourFromBulbId(int id) {
        if (id <= BULB_COUNT) {
            return id;
        } else {
            CustomBulb bulb = CustomBulbHelper.getCustomBulbById(id);
            
            if (bulb == null) {
                return 0;
            }

            if (bulb.color > BULB_COUNT) {
                return 0;
            }
            
            return bulb.color;
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

    public static int getSpecificPlantGrowthBoost(int bulbId, IBlockState plantBlockState) {

        String plantName = plantBlockState.getBlock().getRegistryName().toString();

        if (bulbId < CustomBulbLoader.STARTING_ID) {

            return getVanillaPlantBoost(bulbId, plantName);

        } else {
            CustomBulb bulb = CustomBulbHelper.getCustomBulbById(bulbId);

            if (bulb == null) {
                return 0;
            }

            if (bulb.allPlants == true) {
                return 0;
            }

            for (String plant : bulb.plants) {
                if (plantName.equalsIgnoreCase(plant)) {
                    return bulb.boostModifier;
                }
            }
        }

        return 0;
    }

    public static int getVanillaPlantBoost(int bulbId, String plantName) {

        BulbTypeEnum mediumType = getBulbTypeEnumFromId(bulbId);
        CropTypeEnum cropType = PlantHelper.getCropTypeEnumFromRegistryName(plantName);

        if (mediumType == BulbTypeEnum.INVALID || cropType == CropTypeEnum.NONE) {
            return 0;
        }

        return getBoost(mediumType, cropType);
    }

    private static int getBoost(BulbTypeEnum bulbType, CropTypeEnum cropType) {
        IIdealBoostsConfiguration configuration = PlantHelper.getConfigurationForBulbType(bulbType);
        return PlantHelper.getBoostFromConfigurationForCropType(configuration, cropType);
    }
}