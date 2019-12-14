package io.moonman.emergingtechnology.config.hydroponics.lights;

import io.moonman.emergingtechnology.config.hydroponics.lights.boosts.IdealGrowthBoost;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleGrowLight {

    @Name("Crops Boost from Bulb")
	@Config.Comment("Configure the boost to growth specific crops get when grown under specific bulb")
    public final IdealGrowthBoost BOOSTS = new IdealGrowthBoost();

    @Name("Grow Light - Range")
    @Config.Comment("How many blocks below the grow light should be checked when growing plants.")
    @RangeInt(min = 0, max = 100)
    public int lightBlockRange = 2;

    @Name("Grow Light - Range Modifier Dropoff")
    @Config.Comment("If this value is greater than 0, it will be multiplied by the distance from the light, then subtracted from growth probability.")
    @RangeInt(min = 0, max = 100)
    public int lightBlockRangeDropoff = 0;

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

    @Name("UV Bulb Growth Modifier %")
    @Config.Comment("Probability of growth from a grow lamp containing a UV bulb per cycle (~10 ticks).")
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

    @Name("UV Bulb Energy Modifier")
    @Config.Comment("Energy Modifier of UV Bulb. This value is multiplied by the Light Base Energy Usage to get Light energy usage per cycle (~10 ticks)")
    @RangeInt(min = 0, max = 100)
    public int energyPurpleBulbModifier = 5;

    @Name("Grow Light - Energy Transfer Rate")
    @Config.Comment("The amount of energy transferred to other lights by a grow light per cycle (~10 ticks).")
    @RangeInt(min = 1, max = 100)
    public int growLightEnergyTransferRate = 100;
}