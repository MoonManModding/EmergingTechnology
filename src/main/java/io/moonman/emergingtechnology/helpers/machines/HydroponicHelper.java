package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.custom.classes.CustomGrowthMedium;
import io.moonman.emergingtechnology.helpers.custom.helpers.CustomGrowthMediumHelper;
import io.moonman.emergingtechnology.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Provides useful methods for the Hydroponic Grow Bed
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
                if (itemStack.getItem().getRegistryName().toString()
                        .equalsIgnoreCase(customGrowthMedia[i].name.toString())) {
                    return customGrowthMedia[i].id;
                }
            }
        }

        return 0;
    }

    public static int getGrowthProbabilityForMedium(ItemStack itemStack) {
        int mediumId = getGrowthMediaIdFromStack(itemStack);

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
            return Blocks.HARDENED_CLAY.getDefaultState();
        }
    }
}