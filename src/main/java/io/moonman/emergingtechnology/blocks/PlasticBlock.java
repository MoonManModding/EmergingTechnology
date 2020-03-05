package io.moonman.emergingtechnology.blocks;

import io.moonman.emergingtechnology.blocks.classes.SimpleBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class PlasticBlock extends SimpleBlock {

    public static final String name = "plasticblock";

    public PlasticBlock() {
        super(name, Material.CLAY, SoundType.STONE);
    }
}