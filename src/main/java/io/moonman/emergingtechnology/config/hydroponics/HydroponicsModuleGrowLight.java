package io.moonman.emergingtechnology.config.hydroponics;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleGrowLight {

    @Name("Red Bulb Growth Modifier %")
    @Config.Comment("Probability of growth from a grow lamp containing a red bulb per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthRedBulbModifier = 1;

    @Name("Green Bulb Growth Modifier %")
    @Config.Comment("Probability of growth from a grow lamp containing a green bulb per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthGreenBulbModifier = 2;

    @Name("Blue Bulb Growth Modifier %")
    @Config.Comment("Probability of growth from a grow lamp containing a blue bulb per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthBlueBulbModifier = 4;

    @Name("Purple Bulb Growth Modifier %")
    @Config.Comment("Probability of growth from a grow lamp containing a purple bulb per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int growthPurpleBulbModifier = 8;
    
    @Name("Light Base Energy Usage")
    @Config.Comment("Base power usage of light per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int lightEnergyBaseUsage = 10;

    @Name("Red Bulb Energy Modifier")
    @Config.Comment("Energy Modifier of Red Bulb. This value is multiplied by the Light Base Energy Usage to get Light energy usage per cycle (~10 ticks)")
    @RangeInt(min = 0, max = 100)
    public int energyRedBulbModifier = 2;

    @Name("Green Bulb Energy Modifier")
    @Config.Comment("Energy Modifier of Green Bulb. This value is multiplied by the Light Base Energy Usage to get Light energy usage per cycle (~10 ticks)")
    @RangeInt(min = 0, max = 100)
    public int energyGreenBulbModifier = 3;

    @Name("Blue Bulb Energy Modifier")
    @Config.Comment("Energy Modifier of Blue Bulb. This value is multiplied by the Light Base Energy Usage to get Light energy usage per cycle (~10 ticks)")
    @RangeInt(min = 0, max = 100)
    public int energyBlueBulbModifier = 4;

    @Name("Purple Bulb Energy Modifier")
    @Config.Comment("Energy Modifier of Purple Bulb. This value is multiplied by the Light Base Energy Usage to get Light energy usage per cycle (~10 ticks)")
    @RangeInt(min = 0, max = 100)
    public int energyPurpleBulbModifier = 5;
}