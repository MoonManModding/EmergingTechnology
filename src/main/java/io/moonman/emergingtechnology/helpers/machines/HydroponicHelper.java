package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.helpers.PlantHelper;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.config.hydroponics.enums.CropTypeEnum;
import io.moonman.emergingtechnology.config.hydroponics.interfaces.IIdealBoostsConfiguration;
import io.moonman.emergingtechnology.config.hydroponics.enums.MediumTypeEnum;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.custom.classes.CustomGrowthMedium;
import io.moonman.emergingtechnology.helpers.custom.helpers.CustomGrowthMediumHelper;
import io.moonman.emergingtechnology.helpers.custom.loaders.CustomGrowthMediumLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Provides useful methods for the Hydroponic Grow Bed
 */
public class HydroponicHelper {

    private static ItemStack[] validGrowthMedia = new ItemStack[] { new ItemStack(Blocks.DIRT),
            new ItemStack(Blocks.SAND), new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.CLAY),
            new ItemStack(Items.CLAY_BALL) };

    private static String[] validFluids = new String[] {
        "water",
        "lava"
    };

    public static ItemStack[] getValidGrowthMedia() {
        return validGrowthMedia;
    };

    public static String[] getValidFluids() {
        return validFluids;
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

    public static boolean isFluidValidByName(String name) {
        String[] validFluidNames = getValidFluids();

        for (String validFluidName : validFluidNames) {
            if (name.equalsIgnoreCase(validFluidName)) {
                return true;
            }
        }

        return false;
    }

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
                if (itemStack.getItem().getRegistryName().toString()
                        .equalsIgnoreCase(customGrowthMedia[i].name.toString())) {
                    return customGrowthMedia[i].id;
                }
            }
        }

        return 0;
    }

    public static int getGrowthProbabilityForMedium(ItemStack medium) {
        int mediumId = getGrowthMediaIdFromStack(medium);

        switch (mediumId) {
        case 1:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growthDirtModifier;
        case 2:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growthSandModifier;
        case 3:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growthGravelModifier;
        case 4:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growthClayModifier;
        case 5:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growthClayModifier;
        default:
            return CustomGrowthMediumHelper.getGrowthProbabilityForMedium(mediumId);
        }
    }

    public static IBlockState getMediumBlockStateFromId(int id) {
        switch (id) {
        case 1:
            return Blocks.DIRT.getDefaultState();
        case 2:
            return Blocks.SAND.getDefaultState();
        case 3:
            return Blocks.GRAVEL.getDefaultState();
        case 4:
            return Blocks.CLAY.getDefaultState();
        case 5:
            return Blocks.CLAY.getDefaultState();
        default:
            return Blocks.WOOL.getDefaultState();
        }
    }

    public static MediumTypeEnum getMediumTypeEnumFromId(int id) {
        switch (id) {
        case 1:
            return MediumTypeEnum.DIRT;
        case 2:
            return MediumTypeEnum.SAND;
        case 3:
            return MediumTypeEnum.GRAVEL;
        case 4:
            return MediumTypeEnum.CLAY;
        case 5:
            return MediumTypeEnum.CLAY;
        default:
            return MediumTypeEnum.INVALID;
        }
    }

    public static int getSpecificPlantGrowthBoost(int mediumId, IBlockState plantBlockState) {

        String plantName = plantBlockState.getBlock().getRegistryName().toString();

        if (mediumId < CustomGrowthMediumLoader.STARTING_ID) {
            return getVanillaPlantBoost(mediumId, plantName);

        } else {
            CustomGrowthMedium medium = CustomGrowthMediumHelper.getCustomGrowthMediumById(mediumId);

            if (medium == null) {
                return 0;
            }

            if (medium.allPlants == true) {
                return 0;
            }

            for (String plant : medium.plants) {
                if (plantName.equalsIgnoreCase(plant)) {
                    return medium.boostModifier;
                }
            }
        }

        return 0;
    }

    public static int getVanillaPlantBoost(int mediumId, String plantName) {

        MediumTypeEnum mediumType = getMediumTypeEnumFromId(mediumId);
        CropTypeEnum cropType = PlantHelper.getCropTypeEnumFromRegistryName(plantName);

        if (mediumType == MediumTypeEnum.INVALID || cropType == CropTypeEnum.NONE) {
            return 0;
        }

        return getBoost(mediumType, cropType);
    }

    private static int getBoost(MediumTypeEnum mediumType, CropTypeEnum cropType) {
        IIdealBoostsConfiguration configuration = PlantHelper.getConfigurationForMediumType(mediumType);
        return PlantHelper.getBoostFromConfigurationForCropType(configuration, cropType);
    }

}