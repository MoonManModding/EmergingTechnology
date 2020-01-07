package io.moonman.emergingtechnology.machines;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

/**
An Emerging Technology machine
*/
public class SimpleMachineBase extends Block {

    public SimpleMachineBase(Material material, String name) {
        super(material);
        this.setHardness(1.0f);
        this.setRegistryName(EmergingTechnology.MODID, name);
        this.setUnlocalizedName(EmergingTechnology.MODID + "." + name);
        this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
    }

    @Override
    public boolean isOpaqueCube(IBlockState iBlockState) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState iBlockState) {
        return false;
    }

}