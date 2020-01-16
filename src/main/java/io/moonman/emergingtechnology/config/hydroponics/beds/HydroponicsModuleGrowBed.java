package io.moonman.emergingtechnology.config.hydroponics.beds;

import io.moonman.emergingtechnology.config.hydroponics.beds.boosts.IdealGrowthBoost;
import io.moonman.emergingtechnology.config.hydroponics.beds.media.HydroponicsModuleBiochar;
import io.moonman.emergingtechnology.config.hydroponics.beds.media.HydroponicsModuleClay;
import io.moonman.emergingtechnology.config.hydroponics.beds.media.HydroponicsModuleDirt;
import io.moonman.emergingtechnology.config.hydroponics.beds.media.HydroponicsModuleGravel;
import io.moonman.emergingtechnology.config.hydroponics.beds.media.HydroponicsModuleSand;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleGrowBed {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Crops Boost from Media")
	@Config.Comment("Configure the boost to growth specific crops get when grown on specific media")
    public final IdealGrowthBoost BOOSTS = new IdealGrowthBoost();

    @Name("Medium - Dirt")
	@Config.Comment("Configure the growth multiplier and fluid usage of Dirt in the grow bed")
    public final HydroponicsModuleDirt DIRT = new HydroponicsModuleDirt();

    @Name("Medium - Sand")
	@Config.Comment("Configure the growth multiplier and fluid usage of Sand in the grow bed")
    public final HydroponicsModuleSand SAND = new HydroponicsModuleSand();

    @Name("Medium - Clay")
	@Config.Comment("Configure the growth multiplier and fluid usage of Clay in the grow bed")
    public final HydroponicsModuleClay CLAY = new HydroponicsModuleClay();

    @Name("Medium - Gravel")
	@Config.Comment("Configure the growth multiplier and fluid usage of Gravel in the grow bed")
    public final HydroponicsModuleGravel GRAVEL = new HydroponicsModuleGravel();

    @Name("Medium - Biochar")
	@Config.Comment("Configure the growth multiplier and fluid usage of Biochar in the grow bed")
    public final HydroponicsModuleBiochar BIOCHAR = new HydroponicsModuleBiochar();

    @Name("Grow Bed - Energy Required for Transfer")
    @Config.Comment("Do grow beds require energy to transfer water to other beds? (~10 ticks).")
    public boolean growBedsRequireEnergy = false;

    @Name("Grow Bed - Energy Usage")
    @Config.Comment("If 'Energy Required for Transfer' is true, a grow bed will use this amount of energy per cycle (~10 ticks).")
    @RangeInt(min = 1, max = 100)
    public int growBedsEnergyUsePerCycle = 10;
    
    @Name("Grow Bed - Water Usage")
    @Config.Comment("The amount of water used by a grow bed per cycle (~10 ticks).")
    @RangeInt(min = 1, max = 100)
    public int growBedWaterUsePerCycle = 1;

    @Name("Grow Bed - Water Transfer Rate")
    @Config.Comment("The amount of water transferred to other beds by a grow bed per cycle (~10 ticks).")
    @RangeInt(min = 1, max = 100)
    public int growBedWaterTransferRate = 100;

    @Name("Grow Bed - Destroy Media")
    @Config.Comment("Can growth media be destroyed during use in Grow Bed (configured for each medium)?")
    public boolean growBedDestroyMedia = false;
    
    @Name("Netherwart on Lava Modifier %")
    @Config.Comment("Probability of growth for Netherwart when grow bed contains lava per cycle (~10 ticks).")
    @RangeInt(min = 0, max = 100)
    public int lavaGrowthBoost = 50;
}