package io.moonman.emergingtechnology.machines.battery;

import io.moonman.emergingtechnology.handlers.energy.ConsumerEnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.GeneratorEnergyStorageHandler;
import io.moonman.emergingtechnology.helpers.classes.BatteryConfiguration;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class BatteryTileEntity extends MachineTileBase implements SimpleComponent {

    private BatteryConfiguration configuration = new BatteryConfiguration() {
        @Override
        protected void onSideChanged() {
            updateBlockRender();
            getWorld().notifyNeighborsOfStateChange(getPos(), ModBlocks.battery, true);
        }
    };

    private void updateBlockRender() {
        getWorld().notifyBlockUpdate(getPos(), getState(), getState(), 3);
        getWorld().notifyNeighborsOfStateChange(getPos(), ModBlocks.battery, true);
        markDirty();
    }

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.BATTERY_ENERGY_CAPACITY) {
        @Override
        public void onContentsChanged() {
            markDirtyClient();
            super.onContentsChanged();
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            int energy = super.extractEnergy(maxExtract, simulate);

            int output = getTotalOutput() + energy;

            setTotalOutput(output);

            return energy;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            int energy = super.receiveEnergy(maxReceive, simulate);

            int input = getTotalInput() + energy;

            setTotalInput(input);

            return energy;
        }
    };

    public GeneratorEnergyStorageHandler generatorStorageHandler = new GeneratorEnergyStorageHandler(energyHandler);
    public ConsumerEnergyStorageHandler consumerStorageHandler = new ConsumerEnergyStorageHandler(energyHandler);

    int energy = 0;

    int totalInput = 0;
    int totalOutput = 0;

    int monitorCount = 0;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        
        if (capability == CapabilityEnergy.ENERGY && facing != null) {
            if (this.configuration.getSideInput(facing)) {
                return CapabilityEnergy.ENERGY.cast(this.consumerStorageHandler);
            } else {
                return CapabilityEnergy.ENERGY.cast(this.generatorStorageHandler);
            }
        }

        if (capability == CapabilityEnergy.ENERGY) {
        return CapabilityEnergy.ENERGY.cast(this.generatorStorageHandler);
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.energyHandler.readFromNBT(compound);

        this.setEnergy(compound.getInteger("GuiEnergy"));
        this.setTotalInput(compound.getInteger("GuiTotalInput"));
        this.setTotalOutput(compound.getInteger("GuiTotalOutput"));

        this.configuration.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("GuiEnergy", this.getEnergy());
        compound.setInteger("GuiTotalInput", this.getTotalInput());
        compound.setInteger("GuiTotalOutput", this.getTotalOutput());
        this.energyHandler.writeToNBT(compound);
        this.configuration.writeToNBT(compound);

        return compound;
    }

    @Override
    public void cycle() {
        spreadEnergy();
        resetEnergyMonitor();
    }

    private void spreadEnergy() {
        for (EnumFacing side : EnumFacing.VALUES) {

            if (this.configuration.getSideInput(side)) {
                continue;
            }

            TileEntity tileEntity = world.getTileEntity(pos.offset(side));

            if (tileEntity != null) {
                IEnergyStorage otherStorage = tileEntity.getCapability(CapabilityEnergy.ENERGY, side.getOpposite());

                if (otherStorage != null) {
                    if (otherStorage.canReceive()) {
                        if (this.getEnergy() > 0) {
                            int energySpread = otherStorage.receiveEnergy(this.getEnergy(), false);
                            this.energyHandler.extractEnergy(energySpread, false);
                        }
                    }
                }
            }
        }
    }

    private void resetEnergyMonitor() {
        if (monitorCount == 1) {
            this.totalInput = 0;
            this.totalOutput = 0;
            this.monitorCount = 0;
        } else {
            this.monitorCount++;
        }
    }

    // Getters

    public int getEnergy() {
        return this.energyHandler.getEnergyStored();
    }

    public int getTotalInput() {
        return this.totalInput;
    }

    public int getTotalOutput() {
        return this.totalOutput;
    }

    public BatteryConfiguration getConfiguration() {
        return this.configuration;
    }

    // Setters

    private void setEnergy(int quantity) {
        this.energy = quantity;
    }

    private void setTotalInput(int quantity) {
        this.totalInput = quantity;
    }

    private void setTotalOutput(int quantity) {
        this.totalOutput = quantity;
    }

    public int getField(EnumTileField field) {
        switch (field) {
            case ENERGY:
                return this.getEnergy();
            case BATTERYINPUT:
                return this.getTotalInput();
            case BATTERYOUTPUT:
                return this.getTotalOutput();
            case BATTERYUP:
                return this.configuration.getField(EnumFacing.UP);
            case BATTERYDOWN:
                return this.configuration.getField(EnumFacing.DOWN);
            case BATTERYNORTH:
                return this.configuration.getField(EnumFacing.NORTH);
            case BATTERYSOUTH:
                return this.configuration.getField(EnumFacing.SOUTH);
            case BATTERYEAST:
                return this.configuration.getField(EnumFacing.EAST);
            case BATTERYWEST:
                return this.configuration.getField(EnumFacing.WEST);
            default:
                return 0;
        }
    }

    public void setField(EnumTileField field, int value) {
        switch (field) {
            case ENERGY:
                this.setEnergy(value);
                break;
            case BATTERYINPUT:
                this.setTotalInput(value);
                break;
            case BATTERYOUTPUT:
                this.setTotalOutput(value);
                break;
            case BATTERYUP:
                this.configuration.setSideInput(EnumFacing.UP, value);
                break;
            case BATTERYDOWN:
                this.configuration.setSideInput(EnumFacing.DOWN, value);
                break;
            case BATTERYNORTH:
                this.configuration.setSideInput(EnumFacing.NORTH, value);
                break;
            case BATTERYSOUTH:
                this.configuration.setSideInput(EnumFacing.SOUTH, value);
                break;
            case BATTERYEAST:
                this.configuration.setSideInput(EnumFacing.EAST, value);
                break;
            case BATTERYWEST:
                this.configuration.setSideInput(EnumFacing.WEST, value);
                break;
            default:
                break;
        }
    }

    // OpenComputers

    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_battery";
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getEnergyLevel(Context context, Arguments args) {
        int level = getEnergy();
        return new Object[] { level };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getMaxEnergyLevel(Context context, Arguments args) {
        int level = Reference.BATTERY_ENERGY_CAPACITY;
        return new Object[] { level };
    }
}