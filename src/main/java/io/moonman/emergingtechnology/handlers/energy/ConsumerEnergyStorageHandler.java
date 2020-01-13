package io.moonman.emergingtechnology.handlers.energy;

/**
 * An EnergyStorageHandler which can only accept energy from automation
 */
public class ConsumerEnergyStorageHandler extends EnergyStorageHandler {

    EnergyStorageHandler mainHandler;

    public ConsumerEnergyStorageHandler(EnergyStorageHandler mainHandler) {
        super(mainHandler.getMaxEnergyStored());
        this.mainHandler = mainHandler;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return this.mainHandler.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return this.mainHandler.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return this.mainHandler.getMaxEnergyStored();
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return this.mainHandler.canReceive();
    }

    @Override
    public void onContentsChanged() {
        this.mainHandler.onContentsChanged();
    }

}