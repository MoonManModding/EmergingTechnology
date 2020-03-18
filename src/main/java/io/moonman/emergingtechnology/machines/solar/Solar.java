package io.moonman.emergingtechnology.machines.solar;

import javax.annotation.Nullable;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.machines.MachineBaseSimple;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class Solar extends MachineBaseSimple {

    public static final String name = "solar";
    public static final String registryName = EmergingTechnology.MODID_REG + name;
    

    public Solar() {
        super(name, Material.IRON);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new SolarTile();
    }

}