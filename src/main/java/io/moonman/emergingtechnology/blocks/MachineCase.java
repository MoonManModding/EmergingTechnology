package io.moonman.emergingtechnology.blocks;

import io.moonman.emergingtechnology.blocks.classes.SimpleBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MachineCase extends SimpleBlock {

    public static final String name = "machinecase";

    public MachineCase() {
        super(name, Material.CLAY, SoundType.STONE, 1.0f);
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }
}