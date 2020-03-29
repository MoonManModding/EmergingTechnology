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

    @Name("Algae Bioreactor")
	@LangKey("config.emergingtechnology.synthetics.algaebioreactor.title")
    public final SyntheticsModuleAlgaeBioreactor ALGAEBIOREACTOR = new SyntheticsModuleAlgaeBioreactor();

    @Name("Tissue Scaffolder")
	@LangKey("config.emergingtechnology.synthetics.scaffolder.title")
    public final SyntheticsModuleScaffolder SCAFFOLDER = new SyntheticsModuleScaffolder();

    @Name("Synthetic Chicken Food Points")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int chickenHunger = 6;

    @Name("Synthetic Porkchop Food Points")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int porkchopHunger = 7;

    @Name("Synthetic Beef Food Points")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int beefHunger = 7;

    @Name("Algae Bar Food Points")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int algaeHunger = 5;

    @Name("Synthetic Chicken Food Saturation (double)")
    @RangeDouble(min = 0, max = Double.MAX_VALUE)
    public double chickenHungerSaturation = 1.66;

    @Name("Synthetic Porkchop Food Saturation (double)")
    @RangeDouble(min = 0, max = Double.MAX_VALUE)
    public double porkchopHungerSaturation = 1.82;

    @Name("Synthetic Beef Food Saturation (double)")
    @RangeDouble(min = 0, max = Double.MAX_VALUE)
    public double beefHungerSaturation = 1.82;

    @Name("Algae Food Saturation (double)")
    @RangeDouble(min = 0, max = Double.MAX_VALUE)
    public double algaeHungerSaturation = 1.28;
}