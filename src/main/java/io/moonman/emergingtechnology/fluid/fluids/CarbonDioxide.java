package io.moonman.emergingtechnology.fluid.fluids;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.fluid.FluidGasBase;

public class CarbonDioxide extends FluidGasBase {

    private static final String _name = "carbondioxide";

    public CarbonDioxide() {
        super(_name);
        this.setUnlocalizedName(EmergingTechnology.MODID + "." + _name);
    }
}