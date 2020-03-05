package io.moonman.emergingtechnology.config.hydroponics.beds.boosts;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;

public class IdealGrowthBoost {

    @Name("Dirt")
	@Comment("Configure the boost to growth specific crops get when grown on Dirt")
    public final IdealGrowthBoostDirt DIRT = new IdealGrowthBoostDirt();
    
    @Name("Sand")
	@Comment("Configure the boost to growth specific crops get when grown on Sand")
    public final IdealGrowthBoostSand SAND = new IdealGrowthBoostSand();
    
    @Name("Gravel")
	@Comment("Configure the boost to growth specific crops get when grown on Gravel")
    public final IdealGrowthBoostGravel GRAVEL = new IdealGrowthBoostGravel();
    
    @Name("Clay")
	@Comment("Configure the boost to growth specific crops get when grown on Clay")
	public final IdealGrowthBoostClay CLAY = new IdealGrowthBoostClay();
}