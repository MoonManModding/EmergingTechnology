package io.moonman.emergingtechnology.machines;

import io.moonman.emergingtechnology.init.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class MachineTileBase extends TileEntity implements ITickable {

    private int tick = 0;

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

    @Override
    public void update() {
        if (this.isClient()) {
            return;
        }

        if (tick < Reference.TICK_RATE) {
            tick++;
            return;
        } else {

            cycle();

            tick = 0;
        }
    }

    /**
     * Called from update(), timing is based on interval set in Reference.TICK_RATE
     */
    public void cycle() {

    }
}