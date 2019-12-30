package io.moonman.emergingtechnology.helpers;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.config.hydroponics.enums.BulbTypeEnum;
import io.moonman.emergingtechnology.config.hydroponics.enums.CropTypeEnum;
import io.moonman.emergingtechnology.config.hydroponics.interfaces.IIdealBoostsConfiguration;
import io.moonman.emergingtechnology.config.hydroponics.enums.MediumTypeEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockReed;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

/**
Provides useful methods to manipulate and validate plant items and blocks
*/
public class PlantHelper {
    
    public static final String WHEAT = "minecraft:wheat";
    public static final String CARROTS = "minecraft:carrots";
    public static final String POTATOES = "minecraft:potatoes";
    public static final String BEETROOT = "minecraft:beetroots";
    public static final String REEDS = "minecraft:reeds";
    public static final String CACTUS = "minecraft:cactus";
    public static final String MELON = "minecraft:melon_stem";
    public static final String PUMPKIN = "minecraft:pumpkin_stem";
    public static final String NETHERWART = "minecraft:nether_wart";

    public static boolean isPlantItem(Item item) {
        return item instanceof IPlantable || item instanceof IGrowable || isItemInOverride(item);
    }

    public static boolean isPlantBlock(Block block) {
        return block instanceof IPlantable || block instanceof IGrowable;
    }

    public static int getPlantLightAtPosition(World world, BlockPos position) {
        return world.getLightFromNeighbors(position.add(0, 1, 0));
    }

    public static int getPlantGrowthAtPosition(World world, BlockPos position) {
        IBlockState state = world.getBlockState(position.add(0, 1, 0));

        if (state == null) return 0;

        Block block = state.getBlock();

        if (block instanceof BlockCrops) {
            return state.getValue(BlockCrops.AGE);
        };

        if (block instanceof BlockReed || block instanceof BlockCactus) {
            int growth = 0;

            Block[] aboveBlocks = new Block[] {
                world.getBlockState(position.add(0, 1, 0)).getBlock(),
                world.getBlockState(position.add(0, 2, 0)).getBlock(),
                world.getBlockState(position.add(0, 3, 0)).getBlock()
            };

            for (Block aboveBlock : aboveBlocks) {
                if (aboveBlock instanceof BlockReed || block instanceof BlockCactus) {
                    growth++;
                } else {
                    return growth;
                };
            }

            return growth;
        };

        return 0;
    }

    public static String getPlantNameAtPosition(World world, BlockPos position) {
        IBlockState state = world.getBlockState(position.add(0, 1, 0));

        if (state == null) return "Nothing";

        return state.getBlock().getLocalizedName();
    }

    public static String getPlantRegistryNameAtPosition(World world, BlockPos position) {
        IBlockState state = world.getBlockState(position.add(0, 1, 0));

        if (state == null) return "Nothing";

        return state.getBlock().getRegistryName().getResourcePath();
    }

    private static boolean isItemInOverride(Item item) {
        if (item == Items.REEDS) return true;
        return false;
    }

    public static CropTypeEnum getCropTypeEnumFromRegistryName(String plantName) {
        if (plantName.equalsIgnoreCase(WHEAT)) {
            return CropTypeEnum.WHEAT;
        }

        if (plantName.equalsIgnoreCase(CARROTS)) {
            return CropTypeEnum.CARROTS;
        }

        if (plantName.equalsIgnoreCase(POTATOES)) {
            return CropTypeEnum.POTATOES;
        }

        if (plantName.equalsIgnoreCase(BEETROOT)) {
            return CropTypeEnum.BEETROOT;
        }

        if (plantName.equalsIgnoreCase(REEDS)) {
            return CropTypeEnum.REEDS;
        }

        if (plantName.equalsIgnoreCase(CACTUS)) {
            return CropTypeEnum.CACTUS;
        }

        if (plantName.equalsIgnoreCase(PUMPKIN)) {
            return CropTypeEnum.PUMPKIN;
        }

        if (plantName.equalsIgnoreCase(MELON)) {
            return CropTypeEnum.MELON;
        }

        if (plantName.equalsIgnoreCase(NETHERWART)) {
            return CropTypeEnum.NETHERWART;
        }

        return CropTypeEnum.NONE;
    }

    public static IIdealBoostsConfiguration getConfigurationForMediumType(MediumTypeEnum mediumType) {
        switch (mediumType) {
        case DIRT:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.BOOSTS.DIRT;
        case SAND:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.BOOSTS.SAND;
        case GRAVEL:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.BOOSTS.GRAVEL;
        case CLAY:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.BOOSTS.CLAY;
        default:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.BOOSTS.DIRT;
        }
    }

    public static IIdealBoostsConfiguration getConfigurationForBulbType(BulbTypeEnum bulbType) {
        switch (bulbType) {
        case RED:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.BOOSTS.RED;
        case GREEN:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.BOOSTS.GREEN;
        case BLUE:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.BOOSTS.BLUE;
        case UV:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.BOOSTS.UV;
        default:
            return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.BOOSTS.RED;
        }
    }

    public static int getBoostFromConfigurationForCropType(IIdealBoostsConfiguration configuration, CropTypeEnum cropType) {
        return configuration.getBoost(cropType);
    }

    // public static ItemStack getSeedFromBlock(Block block) {
    //     if (block instanceof BlockCrops == false) {
    //         return null;
    //     }

    //     return null;
    // }

    // public static ItemStack getCropFromBlock(Block block) {
    //     if (block instanceof BlockCrops == false) {
    //         return null;
    //     }

    //     return null;
    // }
}