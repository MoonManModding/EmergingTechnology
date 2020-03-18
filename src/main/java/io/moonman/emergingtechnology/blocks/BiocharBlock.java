package io.moonman.emergingtechnology.blocks;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.blocks.classes.SimpleBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BiocharBlock extends SimpleBlock {

    public static final String name = "biocharblock";
    public static final String registryName = EmergingTechnology.MODID_REG + name;

    public BiocharBlock() {
        super(name, Material.WOOD, SoundType.WOOD);
    }
}