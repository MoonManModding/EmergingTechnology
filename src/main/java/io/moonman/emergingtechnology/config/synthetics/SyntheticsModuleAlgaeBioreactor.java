package io.moonman.emergingtechnology.config.synthetics;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class SyntheticsModuleAlgaeBioreactor {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Algae Bioreactor Energy Usage")
    @Config.Comment("How much energy the Algae Bioreactor uses per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 1000)
    public int bioreactorEnergyUsage = 25;
    
    @Name("Algae Bioreactor Water Usage")
    @Config.Comment("How much water the Algae Bioreactor uses per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 1000)
    public int bioreactorWaterUsage = 10;

    @Name("Algae Bioreactor CO2 Usage")
    @Config.Comment("How much CO2 the Algae Bioreactor uses per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 1000)
    public int bioreactorGasUsage = 5;

    @Name("Algae Bioreactor Operation Time")
    @Config.Comment("How long the Algae Bioreactor takes to grow Algae")
    @RangeInt(min = 0, max = 1000)
    public int bioreactorBaseTimeTaken = 100;

    @Name("Red Bulb Growth Speed Boost")
    @Config.Comment("Base operation time is divided by this amount when red bulb placed in a light above the bioreactor.")
    @RangeInt(min = 0, max = 100)
    public int growthRedBulbModifier = 2;

    @Name("Green Bulb Growth Speed Boost")
    @Config.Comment("Base operation time is divided by this amount when green bulb placed in a light above the bioreactor.")
    @RangeInt(min = 0, max = 100)
    public int growthGreenBulbModifier = 4;

    @Name("Blue Bulb Growth Speed Boost")
    @Config.Comment("Base operation time is divided by this amount when blue bulb placed in a light above the bioreactor.")
    @RangeInt(min = 0, max = 100)
    public int growthBlueBulbModifier = 8;

    @Name("UV Bulb Growth Speed Boost")
    @Config.Comment("Base operation time is divided by this amount when UV bulb placed in a light above the bioreactor.")
    @RangeInt(min = 0, max = 100)
    public int growthPurpleBulbModifier = 16;

}