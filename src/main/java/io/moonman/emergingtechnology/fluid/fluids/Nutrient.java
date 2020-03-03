package io.moonman.emergingtechnology.fluid.fluids;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.fluid.FluidBase;

public class Nutrient extends FluidBase {

    private static final String _name = "nutrient";

    public Nutrient() {
        super(_name, true);
        this.setUnlocalizedName(EmergingTechnology.MODID + "." + _name);
    }

}