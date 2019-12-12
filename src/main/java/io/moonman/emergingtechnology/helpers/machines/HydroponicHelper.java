package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.helpers.PlantHelper;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.config.hydroponics.enums.CropTypeEnum;
import io.moonman.emergingtechnology.config.hydroponics.interfaces.IIdealBoostsConfiguration;
import io.moonman.emergingtechnology.config.hydroponics.enums.MediumTypeEnum;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.custom.providers.ModFluidProvider;
import io.moonman.emergingtechnology.helpers.custom.providers.ModMediumProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Provides useful methods for the Hydroponic Grow Bed
 */
public class HydroponicHelper {

    public static boolean isItemStackValidGrowthMedia(ItemStack itemStack) {
        return ModMediumProvider.mediumExists(itemStack);
    };

    public static boolean isFluidValid(FluidStack fluidStack) {
        return ModFluidProvider.fluidExists(fluidStack);
    }

    public static int getGrowthMediaIdFromStack(ItemStack itemStack) {
        return ModMediumProvider.getMediumIdFromItemStack(itemStack);
    }

    public static int getGrowthProbabilityForMedium(ItemStack medium) {
        int id = getGrowthMediaIdFromStack(medium);

        switch (id) {
        case 1:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.DIRT.growthDirtModifier;
        case 2:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.SAND.growthSandModifier;
        case 3:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.GRAVEL.growthGravelModifier;
        case 4:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.CLAY.growthClayModifier;
        case 5:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.CLAY.growthClayModifier;
        default:
            return ModMediumProvider.getGrowthProbabilityForMediumById(id);
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
            return MediumTypeEnum.CUSTOM;
        }
    }

    public static int getFluidUseForMedium(int id) {
        switch (id) {
        case 1:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.DIRT.growthDirtFluidUsage;
        case 2:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.SAND.growthSandFluidUsage;
        case 3:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.GRAVEL.growthGravelFluidUsage;
        case 4:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.CLAY.growthClayFluidUsage;
        case 5:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.CLAY.growthClayFluidUsage;
        default:
            return ModMediumProvider.getFluidUsageForMediumById(id);
        }
    }

    public static int getGrowthProbabilityForFluid(FluidStack fluidStack) {
      return ModFluidProvider.getGrowthProbabilityForFluidByFluidStack(fluidStack)
    }

    public static int getSpecificPlantGrowthBoostFromFluid(Fluid fluid, IBlockState plantBlockState) {

        if (fluid == null) {
            return 0;
        }

        String plantName = plantBlockState.getBlock().getRegistryName().toString();

        if (fluid == FluidRegistry.LAVA && plantName.equalsIgnoreCase(PlantHelper.NETHERWART)) {
            int boost = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.lavaGrowthBoost;
            return boost;
        }

        CustomFluid customFluid = CustomFluidHelper.getCustomFluid(fluid);

        if (customFluid == null) {
            return 0;
        }

        if (customFluid.allPlants == true) {
            return 0;
        }

        for (String plant : customFluid.plants) {
            if (plantName.equalsIgnoreCase(plant)) {
                return customFluid.boostModifier;
            }
        }

        return 0;
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