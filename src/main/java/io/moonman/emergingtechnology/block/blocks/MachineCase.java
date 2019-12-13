package io.moonman.emergingtechnology.block.blocks;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class MachineCase extends Block {

    private final String _name = "machinecase";
  
    public MachineCase() {
      super(Material.IRON);
      this.setHardness(1.0f);
      this.setRegistryName(EmergingTechnology.MODID, _name);
      this.setUnlocalizedName(EmergingTechnology.MODID + "." + _name);
      this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
      this.setSoundType(SoundType.ANVIL);
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