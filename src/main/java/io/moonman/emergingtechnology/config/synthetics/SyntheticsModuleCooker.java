package io.moonman.emergingtechnology.config.synthetics;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class SyntheticsModuleCooker {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Solar Cooker Operation Heat Loss")
    @Config.Comment("How much heat the Solar Cooker loses when not in sunlight per tick.")
    @RangeInt(min = 0, max = 100)
    public int cookerBaseHeatLoss = 1;
    
    @Name("Solar Cooker Operation Heat Gain")
    @Config.Comment("How much heat the Solar Cooker gains when in sunlight per tick.")
    @RangeInt(min = 0, max = 100)
    public int cookerBaseHeatGain = 3;

    @Name("Solar Cooker Operation Time")
    @Config.Comment("How long the Solar Cooker takes to cook items.")
    @RangeInt(min = 0, max = 100)
    public int cookerBaseTimeTaken = 75;

    @Name("Solar Cooker Required Heat")
    @Config.Comment("How much heat the solar cooker requires to cook items")
    @RangeInt(min = 0, max = 350)
    public int cookerRequiredCookingHeat = 473;

}