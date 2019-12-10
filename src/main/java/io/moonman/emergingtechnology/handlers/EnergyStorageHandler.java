package io.moonman.emergingtechnology.handlers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

/**
 * A customisable EnergyStorage used in Emerging Technology
 */
public class EnergyStorageHandler extends EnergyStorage {
    public EnergyStorageHandler(int capacity) {
        super(capacity, capacity, capacity, 0);
    }

    public EnergyStorageHandler(int capacity, int maxTransfer) {
        super(capacity, maxTransfer, maxTransfer, 0);
    }

    public EnergyStorageHandler(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract, 0);
    }

    public EnergyStorageHandler(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energy = super.receiveEnergy(maxReceive, simulate);

        if (energy > 0)
            onContentsChanged();

        return energy;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energy = super.extractEnergy(maxExtract, simulate);

        if (energy > 0)
            onContentsChanged();

        return energy;
    }

    @Override
    public int getEnergyStored() {
        return super.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return super.getMaxEnergyStored();
    }

    @Override
    public boolean canExtract() {
        return super.canExtract();
    }

    @Override
    public boolean canReceive() {
        return super.canReceive();
    }

    /**
     * Called when energy extracted/received
     */
    public void onContentsChanged() {

    }

    public void readFromNBT(NBTTagCompound compound) {
        this.energy = compound.getInteger("Energy");
        this.capacity = compound.getInteger("Capacity");
        this.maxReceive = compound.getInteger("MaxReceive");
        this.maxExtract = compound.getInteger("MaxExtract");
    }

    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("Energy", this.energy);
        compound.setInteger("Capacity", this.capacity);
        compound.setInteger("MaxReceive", this.maxReceive);
        compound.setInteger("MaxExtract", this.maxExtract);
    }
}