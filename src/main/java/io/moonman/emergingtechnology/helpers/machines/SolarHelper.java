package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.helpers.EnergyNetworkHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class SolarHelper {

    public static void generateAndPushEnergy(World world, BlockPos pos,
            LazyOptional<EnergyStorageHandler> energyHandler) {

        if (world.canBlockSeeSky(pos) && world.isDaytime()) {

            energyHandler.ifPresent(e -> {

                int generated = 50; // EmergingTechnologyConfig.ELECTRICS_MODULE.SOLAR.solarEnergyGenerated;

                if (world.isThundering() || world.isRaining()) {
                    generated = Math.round(generated / 2);
                }

                ((EnergyStorageHandler) e).addEnergy(generated);
            });
        }

        EnergyNetworkHelper.pushEnergy(world, pos, energyHandler);
    }
}