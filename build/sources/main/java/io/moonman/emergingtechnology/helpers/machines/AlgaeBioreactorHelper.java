package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.config.hydroponics.enums.BulbTypeEnum;
import io.moonman.emergingtechnology.machines.light.Light;
import io.moonman.emergingtechnology.machines.light.LightTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Provides useful methods for the Algae Bioreactor
 */
public class AlgaeBioreactorHelper {

    public static int getLightBoost(World world, BlockPos pos) {

        if (world == null || pos == null) {
            return 0;
        }

        BlockPos abovePos = pos.offset(EnumFacing.UP, 1);
        IBlockState aboveState = world.getBlockState(abovePos);

        if (aboveState.getBlock() instanceof Light) {
            boolean isLightPowered = aboveState.getValue(Light.POWERED);

            if (!isLightPowered)
                return 0;

            LightTileEntity lightTileEntity = (LightTileEntity) world.getTileEntity(abovePos);

            if (lightTileEntity == null)
                return 0;

            int bulbTypeId = lightTileEntity.getBulbTypeId();

            return getBoostFromBulbId(bulbTypeId);
        }

        return 0;
    }

    private static int getBoostFromBulbId(int id) {
        BulbTypeEnum bulb = LightHelper.getBulbTypeEnumFromId(id);

        switch (bulb) {
            case BLUE:
                return EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.growthBlueBulbModifier;
            case GREEN:
                return EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.growthGreenBulbModifier;
            case RED:
                return EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.growthRedBulbModifier;
            case UV:
                return EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.growthPurpleBulbModifier;
            default:
                return 0;
        }
    }

    public static int getTimeTaken(int boost) {

        if (boost == 0) boost = 1;

        return (int) EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorBaseTimeTaken / boost;
    }
}