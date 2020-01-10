package io.moonman.emergingtechnology.handlers;

/**
 * An EnergyStorageHandler which can only provide energy to automation
 */
public class GeneratorEnergyStorageHandler extends EnergyStorageHandler {

    EnergyStorageHandler mainHandler;

    public GeneratorEnergyStorageHandler(EnergyStorageHandler mainHandler) {
        super(mainHandler.getMaxEnergyStored());
        this.mainHandler = mainHandler;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return this.mainHandler.extractEnergy(maxExtract, simulate);
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
        return this.mainHandler.canExtract();
    }

    @Override
    public boolean canReceive() {
        return false;
    }

    @Override
    public void onContentsChanged() {
        this.mainHandler.onContentsChanged();
    }

}