package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.helpers.PlantHelper;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.config.hydroponics.enums.CropTypeEnum;
import io.moonman.emergingtechnology.config.hydroponics.interfaces.IIdealBoostsConfiguration;
import io.moonman.emergingtechnology.config.hydroponics.enums.MediumTypeEnum;
import io.moonman.emergingtechnology.helpers.custom.classes.ModFluid;
import io.moonman.emergingtechnology.providers.ModFluidProvider;
import io.moonman.emergingtechnology.providers.ModMediumProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * Provides useful methods for the Hydroponic Grow Bed
 */
public class HydroponicHelper {

    public static boolean isItemStackValid(ItemStack itemStack) {
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

        return ModFluidProvider.getGrowthProbabilityForFluidByFluidStack(fluidStack);
    }

    public static int getSpecificPlantGrowthBoostForFluidStack(FluidStack fluidStack, IBlockState plantBlockState) {

        String plantName = plantBlockState.getBlock().getRegistryName().toString();

        if (checkForNetherwortOnLava(fluidStack, plantName)) {
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.lavaGrowthBoost;
        }

        return ModFluidProvider.getSpecificPlantGrowthBoostFromFluidStack(fluidStack, plantName);
    }

    public static int getSpecificPlantGrowthBoostForId(int id, IBlockState plantBlockState) {

        String plantName = plantBlockState.getBlock().getRegistryName().toString();

        if (id <= ModMediumProvider.BASE_MEDIUM_COUNT) {
            return getVanillaPlantBoost(id, plantName);
        }

        return ModMediumProvider.getSpecificPlantGrowthBoostForId(id, plantName);
    }

    public static int getVanillaPlantBoost(int mediumId, String plantName) {

        MediumTypeEnum mediumType = getMediumTypeEnumFromId(mediumId);
        CropTypeEnum cropType = PlantHelper.getCropTypeEnumFromRegistryName(plantName);

        if (mediumType == MediumTypeEnum.CUSTOM || cropType == CropTypeEnum.NONE) {
            return 0;
        }

        return getBoost(mediumType, cropType);
    }

    private static int getBoost(MediumTypeEnum mediumType, CropTypeEnum cropType) {
        IIdealBoostsConfiguration configuration = PlantHelper.getConfigurationForMediumType(mediumType);
        return PlantHelper.getBoostFromConfigurationForCropType(configuration, cropType);
    }

    private static boolean checkForNetherwortOnLava(FluidStack fluidStack, String name) {
        ModFluid fluid = ModFluidProvider.getFluidByFluidStack(fluidStack);

        if (fluid == null) {
            return false;
        }

        if (fluid.id == ModFluidProvider.LAVA_ID && name.equalsIgnoreCase(PlantHelper.NETHERWART)) {
            return true;
        }

        return false;
    }
}