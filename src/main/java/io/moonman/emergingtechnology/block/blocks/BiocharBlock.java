package io.moonman.emergingtechnology.block.blocks;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BiocharBlock extends Block {

    private final String _name = "biocharblock";
  
    public BiocharBlock() {
      super(Material.WOOD);
      this.setHardness(1.0f);
      this.setRegistryName(EmergingTechnology.MODID, _name);
      this.setUnlocalizedName(EmergingTechnology.MODID + "." + _name);
      this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
      this.setSoundType(SoundType.WOOD);
    }

  }