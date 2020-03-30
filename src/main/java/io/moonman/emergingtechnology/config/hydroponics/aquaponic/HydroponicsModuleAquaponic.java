package io.moonman.emergingtechnology.config.hydroponics.aquaponic;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleAquaponic {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Aquaponic - Energy Usage")
    @Config.Comment("The amount of energy used per tick.")
    @RangeInt(min = 1, max = Integer.MAX_VALUE)
    public int aquaponicEnergyBaseUsage = 40;

    @Name("Aquaponic - Water Usage")
    @Config.Comment("The amount of water used per tick.")
    @RangeInt(min = 1, max = Integer.MAX_VALUE)
    public int aquaponicWaterBaseUsage = 60;
    
    @Name("Aquaponic - Nutrient Fluid generated")
    @Config.Comment("The amount of Nutrient Fluid generated per tick.")
    @RangeInt(min = 1, max = Integer.MAX_VALUE)
    public int aquaponicFluidGenerated = 60;
    
    @Name("Aquaponic - Transfer rate")
    @Config.Comment("The amount of Nutrient Fluid transferred per tick.")
    @RangeInt(min = 1, max = Integer.MAX_VALUE)
    public int aquaponicFluidTransferRate = 600;

    @Name("Aquaponic - Fish breed probability")
    @Config.Comment("The probability (out of 1000000) of two fish of the same breed to produce another fish per tick.")
    @RangeInt(min = 0, max = 1000000)
    public int aquaponicFishBreedProbability = 50;
}