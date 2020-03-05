package io.moonman.emergingtechnology.config.hydroponics.lights.boosts;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;

public class IdealGrowthBoost {

    @Name("Red Bulb")
	@Comment("Configure the boost to growth specific crops get when grown under the Red Bulb")
    public final IdealGrowthBoostRedBulb RED = new IdealGrowthBoostRedBulb();
    
    @Name("Green Bulb")
	@Comment("Configure the boost to growth specific crops get when grown under the Blue Bulb")
    public final IdealGrowthBoostGreenBulb GREEN = new IdealGrowthBoostGreenBulb();
    
    @Name("Blue Bulb")
	@Comment("Configure the boost to growth specific crops get when grown under the Green Bulb")
    public final IdealGrowthBoostBlueBulb BLUE = new IdealGrowthBoostBlueBulb();
    
    @Name("UV Bulb")
	@Comment("Configure the boost to growth specific crops get when grown under the UV Bulb")
	public final IdealGrowthBoostUVBulb UV = new IdealGrowthBoostUVBulb();
}