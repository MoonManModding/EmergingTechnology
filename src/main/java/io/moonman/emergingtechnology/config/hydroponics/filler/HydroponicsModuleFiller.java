package io.moonman.emergingtechnology.config.hydroponics.filler;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleFiller {

    @Name("Disable Machine")
    @Config.Comment("Prevent this machine from being crafted?")
    public boolean disabled = false;

    @Name("Filler - Fluid transfer rate")
    @Config.Comment("The amount of fluid transferred to neighbours per cycle (~10 ticks).")
    @RangeInt(min = 1, max = 1000)
    public int fillerFluidTransferRate = 100;
}