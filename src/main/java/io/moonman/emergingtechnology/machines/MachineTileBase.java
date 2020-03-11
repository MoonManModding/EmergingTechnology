package io.moonman.emergingtechnology.machines;

import io.moonman.emergingtechnology.init.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class MachineTileBase extends TileEntity implements ITickable {

    private int tick = 0;
    private int maxTick = Reference.TICK_RATE;

    public boolean isClient() {
        return getWorld().isRemote;
    }

    public void markDirtyClient() {
        markDirty();

        if (world == null) {
            return;
        }

        IBlockState state = getWorld().getBlockState(getPos());
        getWorld().notifyBlockUpdate(getPos(), state, state, 3);
    }

    public void setMaxTick(int max) {
        this.maxTick = max;
    }

    @Override
    public void update() {
        if (this.isClient()) {
            return;
        }

        if (tick < maxTick) {
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

    /**
     * Whether this machine produces energy and can channel energy to peripheral machines
     */
    public boolean isEnergyGeneratorTile() {
        return false;
    }
}