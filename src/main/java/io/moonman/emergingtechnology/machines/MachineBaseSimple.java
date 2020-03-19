package io.moonman.emergingtechnology.machines;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraftforge.common.extensions.IForgeBlockState;

/**
A basic Emerging Technology machine
*/

public class MachineBaseSimple extends Block implements IForgeBlockState {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public MachineBaseSimple(String name, Material material) {
        super(Properties.create(material).sound(SoundType.METAL).hardnessAndResistance(2.0f));
        setRegistryName(name);
    }

    public MachineBaseSimple(String name, Material material, SoundType sound) {
        super(Properties.create(material).sound(sound).hardnessAndResistance(2.0f));
        setRegistryName(name);

    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
     }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}