package io.moonman.emergingtechnology.config.hydroponics.injector;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleInjector {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Injector - Energy Usage")
    @Config.Comment("The amount of energy used per tick.")
    @RangeInt(min = 1, max = Integer.MAX_VALUE)
    public int injectorEnergyBaseUsage = 40;

    @Name("Injector - Water Usage")
    @Config.Comment("The amount of water used per tick.")
    @RangeInt(min = 1, max = Integer.MAX_VALUE)
    public int injectorWaterBaseUsage = 60;
    
    @Name("Injector - Nutrient Fluid generated")
    @Config.Comment("The amount of Nutrient Fluid generated per tick.")
    @RangeInt(min = 1, max = Integer.MAX_VALUE)
    public int injectorFluidGenerated = 60;

    @Name("Injector - Operation Time")
    @Config.Comment("The time taken for the Injector to process Fertilizer")
    @RangeInt(min = 1, max = Integer.MAX_VALUE)
    public int injectorBaseTimeTaken = 10;
    
    @Name("Injector - Transfer rate")
    @Config.Comment("The amount of Nutrient Fluid transferred per tick.")
    @RangeInt(min = 1, max = Integer.MAX_VALUE)
    public int injectorFluidTransferRate = 600;
}