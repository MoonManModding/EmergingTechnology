package io.moonman.emergingtechnology.config.hydroponics.diffuser;

import io.moonman.emergingtechnology.config.hydroponics.diffuser.nozzle.HydroponicsModuleLongNozzle;
import io.moonman.emergingtechnology.config.hydroponics.diffuser.nozzle.HydroponicsModulePreciseNozzle;
import io.moonman.emergingtechnology.config.hydroponics.diffuser.nozzle.HydroponicsModuleSmartNozzle;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class HydroponicsModuleDiffuser {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Diffuser - Energy Usage")
    @Config.Comment("The amount of energy used per cycle (~10 ticks).")
    @RangeInt(min = 1, max = 1000)
    public int diffuserEnergyBaseUsage = 100;

    @Name("Diffuser - Gas Usage")
    @Config.Comment("The amount of gas used per cycle (~10 ticks).")
    @RangeInt(min = 1, max = 1000)
    public int diffuserGasBaseUsage = 10;

    @Name("Diffuser - Base Range")
    @Config.Comment("The range of effect of the Diffuser (in N,S,W,E directions)")
    @RangeInt(min = 1, max = 10)
    public int diffuserBaseRange = 4;
    
    @Name("Diffuser - Base Growth Probability")
    @Config.Comment("The base probability of plant growth when in range of the Diffuser")
    @RangeInt(min = 1, max = 100)
    public int diffuserBaseBoostProbability = 15;

    @Name("Long Nozzle")
	@Config.Comment("Configure the Long nozzle attachment")
    public final HydroponicsModuleLongNozzle LONG = new HydroponicsModuleLongNozzle();

    @Name("Precise Nozzle")
	@Config.Comment("Configure the Precise nozzle attachment")
    public final HydroponicsModulePreciseNozzle PRECISE = new HydroponicsModulePreciseNozzle();

    @Name("Smart Nozzle")
	@Config.Comment("Configure the Smart nozzle attachment")
    public final HydroponicsModuleSmartNozzle SMART = new HydroponicsModuleSmartNozzle();
}