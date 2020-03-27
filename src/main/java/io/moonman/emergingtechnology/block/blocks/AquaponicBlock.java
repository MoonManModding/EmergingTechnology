package io.moonman.emergingtechnology.block.blocks;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class AquaponicBlock extends Block {

    private final String _name = "aquaponicblock";
  
    public AquaponicBlock() {
      super(Material.ROCK);
      this.setHardness(2.0f);
      this.setRegistryName(EmergingTechnology.MODID, _name);
      this.setUnlocalizedName(EmergingTechnology.MODID + "." + _name);
      this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
      this.setSoundType(SoundType.STONE);
    }
  
  }