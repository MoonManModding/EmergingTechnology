package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class SolarHelper {

    public static void generateEnergy(World world, BlockPos pos, LazyOptional<EnergyStorageHandler> energyHandler) {

        if (world.canBlockSeeSky(pos) && world.isDaytime()) {
            int generated = 50; // EmergingTechnologyConfig.ELECTRICS_MODULE.SOLAR.solarEnergyGenerated;

            if (world.isThundering() || world.isRaining()) {
                generated = Math.round(generated / 2);
            }

            energyHandler.ifPresent(e -> ((EnergyStorageHandler) e).addEnergy(50));
        }
    }
}