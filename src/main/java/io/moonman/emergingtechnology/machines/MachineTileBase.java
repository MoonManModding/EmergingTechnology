package io.moonman.emergingtechnology.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;

public class MachineTileBase extends TileEntity {

    public boolean isClient() {
        return world.isRemote;
    }

    public void markDirtyClient() {
        markDirty();

        if (world == null) {
            return;
        }

        IBlockState state = world.getBlockState(getPos());
        world.notifyBlockUpdate(getPos(), state, state, 3);
    }
}