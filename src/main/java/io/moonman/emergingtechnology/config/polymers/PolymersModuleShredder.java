package io.moonman.emergingtechnology.config.polymers;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class PolymersModuleShredder {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Shredder Operation Energy Cost")
    @Config.Comment("How much energy the Shredder uses when shredding per tick.")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int shredderEnergyBaseUsage = 15;

    @Name("Shredder Operation Time")
    @Config.Comment("How long the Shredder takes to shred items.")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int shredderBaseTimeTaken = 10;
}