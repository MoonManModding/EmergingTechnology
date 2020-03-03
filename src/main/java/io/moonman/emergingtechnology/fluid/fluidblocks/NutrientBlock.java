package io.moonman.emergingtechnology.fluid.fluidblocks;

import io.moonman.emergingtechnology.fluid.BlockFluidBase;
import io.moonman.emergingtechnology.init.ModFluids;
import net.minecraft.block.material.Material;

public class NutrientBlock extends BlockFluidBase {

    private static final String _name = "nutrientblock";

    public NutrientBlock() {
        super(_name, ModFluids.NUTRIENT, Material.WATER);
    }
}