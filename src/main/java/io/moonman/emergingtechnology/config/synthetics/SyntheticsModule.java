package io.moonman.emergingtechnology.config.synthetics;

import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.Config.RangeInt;

public class SyntheticsModule {

    @Name("Solar Cooker")
	@LangKey("config.emergingtechnology.synthetics.cooker.title")
    public final SyntheticsModuleCooker COOKER = new SyntheticsModuleCooker();

    @Name("Tissue Bioreactor")
	@LangKey("config.emergingtechnology.synthetics.bioreactor.title")
    public final SyntheticsModuleBioreactor BIOREACTOR = new SyntheticsModuleBioreactor();

    @Name("Tissue Scaffolder")
	@LangKey("config.emergingtechnology.synthetics.scaffolder.title")
    public final SyntheticsModuleScaffolder SCAFFOLDER = new SyntheticsModuleScaffolder();

    @Name("Synthetic Chicken Food Points")
    @RangeInt(min = 0, max = 15)
    public int chickenHunger = 6;

    @Name("Synthetic Porkchop Food Points")
    @RangeInt(min = 0, max = 15)
    public int porkchopHunger = 7;

    @Name("Synthetic Beef Food Points")
    @RangeInt(min = 0, max = 15)
    public int beefHunger = 7;

    @Name("Synthetic Chicken Food Saturation")
    @RangeDouble(min = 0, max = 25)
    public double chickenHungerSaturation = 10;

    @Name("Synthetic Porkchop Food Saturation")
    @RangeDouble(min = 0, max = 25)
    public double porkchopHungerSaturation = 12.8;

    @Name("Synthetic Beef Food Saturation")
    @RangeDouble(min = 0, max = 25)
    public double beefHungerSaturation = 12.8;
}