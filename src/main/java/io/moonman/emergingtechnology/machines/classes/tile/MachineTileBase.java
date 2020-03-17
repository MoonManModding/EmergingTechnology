package io.moonman.emergingtechnology.machines.classes.tile;

import io.moonman.emergingtechnology.init.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
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

    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) != this ? false
                : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
                        (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    public int getField(EnumTileField field) {
        return 0;
    }

    public void setField(EnumTileField field, int value) {
        
    }
}