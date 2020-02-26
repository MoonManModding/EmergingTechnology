package io.moonman.emergingtechnology.machines.collector;

import java.util.Random;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.machines.CollectorHelper;
import io.moonman.emergingtechnology.machines.MachineTileBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CollectorTileEntity extends MachineTileBase {

    private boolean requiresUpdate = false;
    private boolean hasInventory = false;

    public ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
            super.onContentsChanged(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack itemStack) {
            return CollectorHelper.isValidItemStack(itemStack);
        }
    };

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.itemHandler);
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.itemHandler.deserializeNBT(compound.getCompoundTag("Inventory"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Inventory", this.itemHandler.serializeNBT());

        return compound;
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

    @Override
    public void cycle() {
        doCollectionProcess();
        checkHasInventory();
        if (this.requiresUpdate) {
            Collector.setState(this.hasInventory, getWorld(), getPos());
            this.requiresUpdate = false;
        }
    }

    public void doCollectionProcess() {

        if (!CollectorHelper.isInValidBiome(world.getBiome(getPos()))) {
            return;
        }

        int random = new Random().nextInt(1001);

        if (random < EmergingTechnologyConfig.POLYMERS_MODULE.COLLECTOR.plasticRecoveryProbability) {
            if (!CollectorHelper.isFloatingInWater(getWorld(), getPos())) {
                return;
            }

            ItemStack recoveredItemStack = CollectorHelper.getRandomRecoveredItemStack();

            for (int i = 0; i < 5; i++) {

                ItemStack remainder = this.itemHandler.insertItem(i, recoveredItemStack, false);

                if (StackHelper.isItemStackEmpty(remainder)) {
                    return;
                }
            }
        }
    }

    private void checkHasInventory() {
        boolean newHasInventory = hasInventory();
        if (newHasInventory != this.hasInventory) {
            this.requiresUpdate = true;
            this.hasInventory = newHasInventory;
        }
    }

    private boolean hasInventory() {
        for (int i = 0; i < 5; i++) {
            if (!StackHelper.isItemStackEmpty(this.itemHandler.getStackInSlot(i))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) != this ? false
                : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
                        (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }
}