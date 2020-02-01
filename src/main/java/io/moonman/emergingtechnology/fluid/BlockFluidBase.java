package io.moonman.emergingtechnology.fluid;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidBase extends BlockFluidClassic {

    public BlockFluidBase(String name, Fluid fluid, Material material) {
        super(fluid, material);
        this.setRegistryName(EmergingTechnology.MODID, name);
        this.setUnlocalizedName(EmergingTechnology.MODID + "." + name);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}