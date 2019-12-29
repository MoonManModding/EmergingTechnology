package io.moonman.emergingtechnology.config.synthetics;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class SyntheticsModuleScaffolder {

    @Name("Tissue Scaffolder Energy Usage")
    @Config.Comment("How much energy the Tissue Scaffolder uses per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int scaffolderEnergyUsage = 75;

    @Name("Tissue Scaffolder Operation Time")
    @Config.Comment("How long the Tissue Scaffolder takes to create samples from syringes.")
    @RangeInt(min = 0, max = 1000)
    public int scaffolderBaseTimeTaken = 150;
}