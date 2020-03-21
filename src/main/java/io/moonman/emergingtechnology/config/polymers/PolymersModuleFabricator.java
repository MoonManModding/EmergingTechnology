package io.moonman.emergingtechnology.config.polymers;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class PolymersModuleFabricator {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Fabricator Operation Energy Cost")
    @Config.Comment("How much energy the Fabricator uses when fabricating per tick.")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int fabricatorEnergyBaseUsage = 25;

    @Name("Fabricator Operation Time")
    @Config.Comment("How long the Fabricator takes to fabricate items.")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int fabricatorBaseTimeTaken = 50;
}