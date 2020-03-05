package io.moonman.emergingtechnology.machines;

import io.moonman.emergingtechnology.init.Reference;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.util.Constants;

public class MachineTileBase extends TileEntity implements ITickableTileEntity {

    public MachineTileBase(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    private int tick = 0;

    public boolean isClient() {
        IWorld world = getWorld();

        return world.isRemote();
    }

    public void markDirtyClient() {
        markDirty();

        if (world == null) {
            return;
        }

        BlockState state = getWorld().getBlockState(getPos());
        getWorld().notifyBlockUpdate(getPos(), state, state,
                Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
    }

    @Override
    public void tick() {
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

    /**
     * Whether this machine produces energy and can channel energy to peripheral
     * machines
     */
    public boolean isEnergyGeneratorTile() {
        return false;
    }
}