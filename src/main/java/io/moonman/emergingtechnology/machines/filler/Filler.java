package io.moonman.emergingtechnology.machines.filler;

import javax.annotation.Nullable;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.machines.MachineBaseSimple;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class Filler extends MachineBaseSimple {

    public static final String name = "filler";
    public static final String registryName = EmergingTechnology.MODID_REG + name;

    public Filler() {
        super(name, Material.IRON);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FillerTile();
    }

}