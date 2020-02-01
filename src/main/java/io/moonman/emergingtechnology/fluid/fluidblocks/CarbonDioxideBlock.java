package io.moonman.emergingtechnology.fluid.fluidblocks;

import io.moonman.emergingtechnology.fluid.BlockFluidBase;
import io.moonman.emergingtechnology.init.ModFluids;
import net.minecraft.block.material.Material;

public class CarbonDioxideBlock extends BlockFluidBase {

    private static final String _name = "carbondioxideblock";

    public CarbonDioxideBlock() {
        super(_name, ModFluids.CARBON_DIOXIDE, Material.WATER);
    }
}