package io.moonman.emergingtechnology.machines.piezoelectric;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.GeneratorEnergyStorageHandler;
import io.moonman.emergingtechnology.helpers.EnergyNetworkHelper;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class PiezoelectricTileEntity extends MachineTileBase implements SimpleComponent {

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.PIEZOELECTRIC_ENERGY_CAPACITY, 100,
            100) {
        @Override
        public void onContentsChanged() {
            markDirty();
            super.onContentsChanged();
        }
    };

    public GeneratorEnergyStorageHandler generatorEnergyHandler = new GeneratorEnergyStorageHandler(energyHandler) {

    };

    private int tick = 0;
    private int cooldown = 0;
    private int energy = 0;

    @Override
    public boolean isEnergyGeneratorTile() {
        return true;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(this.generatorEnergyHandler);
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.energyHandler.readFromNBT(compound);

        this.setEnergy(compound.getInteger("GuiEnergy"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("GuiEnergy", this.getEnergy());
        this.energyHandler.writeToNBT(compound);

        return compound;
    }



    @Override
    public void update() {

        if (this.isClient()) {
            return;
        }

        decreaseCooldown();
        spreadEnergy();
    }

    private void decreaseCooldown() {
        if (this.cooldown > 0) {
            this.cooldown--;
        }
    }

    public void walkedOn() {
        if (this.cooldown == 0) {
            this.energyHandler.receiveEnergy(EmergingTechnologyConfig.ELECTRICS_MODULE.PIEZOELECTRIC.piezoelectricEnergyGenerated, false);
            this.cooldown = EmergingTechnologyConfig.ELECTRICS_MODULE.PIEZOELECTRIC.piezoelectricStepCooldown;
        }
    }

    private void spreadEnergy() {
        EnergyNetworkHelper.pushEnergy(getWorld(), getPos(), this.generatorEnergyHandler);
    }

    // Getters

    public int getEnergy() {
        return this.energyHandler.getEnergyStored();
    }

    // Setters

    private void setEnergy(int quantity) {
        this.energy = quantity;
    }

    // OpenComputers

    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_piezoelectric_tile";
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] isSteppedOn(Context context, Arguments args) {
        boolean stepped = this.cooldown == EmergingTechnologyConfig.ELECTRICS_MODULE.PIEZOELECTRIC.piezoelectricStepCooldown;
        return new Object[] { stepped };
    }
}