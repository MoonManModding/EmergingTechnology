package io.moonman.emergingtechnology.handlers.energy;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

/**
 * A customisable EnergyStorage used in Emerging Technology
 */
public class EnergyStorageHandler extends EnergyStorage implements INBTSerializable<CompoundNBT> {
    public EnergyStorageHandler(int capacity) {
        super(capacity, capacity, capacity, 0);
    }

    public EnergyStorageHandler(int capacity, int maxTransfer) {
        super(capacity, maxTransfer, maxTransfer, 0);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
        if (this.energy > getMaxEnergyStored()) {
            this.energy = getEnergyStored();
        }

        this.onContentsChanged();
    }

    public void consumeEnergy(int energy) {
        this.energy -= energy;
        if (this.energy < 0) {
            this.energy = 0;
        }

        this.onContentsChanged();
    }

    /**
     * Called when energy extracted/received
     */
    public void onContentsChanged() {

    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("energy", getEnergyStored());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setEnergy(nbt.getInt("energy"));
    }
}