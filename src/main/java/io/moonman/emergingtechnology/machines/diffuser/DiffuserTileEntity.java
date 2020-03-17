package io.moonman.emergingtechnology.machines.diffuser;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.energy.ConsumerEnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.fluid.FluidStorageHandler;
import io.moonman.emergingtechnology.helpers.machines.DiffuserHelper;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase;
import io.moonman.emergingtechnology.recipes.machines.ScrubberRecipes;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class DiffuserTileEntity extends MachineTileBase implements SimpleComponent {

    public FluidTank gasHandler = new FluidStorageHandler(Reference.DIFFUSER_GAS_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }

        @Override
        public boolean canFillFluidType(FluidStack fluidStack) {

            Fluid fluid = fluidStack.getFluid();

            return ScrubberRecipes.isValidGas(fluid);
        }
    };

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.DIFFUSER_ENERGY_CAPACITY) {
        @Override
        public void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }
    };

    public ConsumerEnergyStorageHandler consumerEnergyHandler = new ConsumerEnergyStorageHandler(energyHandler);

    public ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirtyClient();
            super.onContentsChanged(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return DiffuserHelper.isItemStackValid(stack);
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    private int gas = this.gasHandler.getFluidAmount();
    private int energy = this.energyHandler.getEnergyStored();
    private int plants = 0;
    private int nozzle = 0;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return true;
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        if (capability == CapabilityEnergy.ENERGY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.gasHandler);
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.itemHandler);
        if (capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(this.consumerEnergyHandler);
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.itemHandler.deserializeNBT(compound.getCompoundTag("Inventory"));

        this.setGas(compound.getInteger("GuiGas"));
        this.setEnergy(compound.getInteger("GuiEnergy"));
        this.setPlants(compound.getInteger("GuiPlants"));
        this.setNozzle(compound.getInteger("GuiNozzle"));

        this.gasHandler.readFromNBT(compound);
        this.energyHandler.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setTag("Inventory", this.itemHandler.serializeNBT());

        compound.setInteger("GuiGas", this.getGas());
        compound.setInteger("GuiEnergy", this.getEnergy());
        compound.setInteger("GuiPlants", this.getPlants());
        compound.setInteger("GuiNozzle", this.getNozzle());

        this.gasHandler.writeToNBT(compound);
        this.energyHandler.writeToNBT(compound);

        return compound;
    }



    @Override
    public void cycle() {
        this.setEnergy(this.energyHandler.getEnergyStored());
        this.setGas(this.gasHandler.getFluidAmount());

        doGrowthProcess();
    }

    public void doGrowthProcess() {

        // Insufficient gas
        if (this.getGas() < EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserGasBaseUsage) {
            return;
        }

        // Insufficient energy
        if (this.getEnergy() < EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserEnergyBaseUsage) {
            return;
        }

        int boostedPlants = this.doBoost();

        this.setPlants(boostedPlants);

        this.setGas(this.gasHandler.getFluidAmount());

        if (boostedPlants > 0) {

            this.energyHandler
                    .extractEnergy(EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserEnergyBaseUsage, false);

            this.setEnergy(this.energyHandler.getEnergyStored());
        }
    }

    public int doBoost() {
        return DiffuserHelper.boostSurroundingPlants(getWorld(), getPos(), this.gasHandler, this.getNozzle());
    }

    public ItemStack getInputStack() {
        return itemHandler.getStackInSlot(0);
    }

    // Getters

    public int getGas() {
        return this.gasHandler.getFluidAmount();
    }

    public int getEnergy() {
        return this.energyHandler.getEnergyStored();
    }

    public int getPlants() {
        return this.plants;
    }

    public int getNozzle() {
        return DiffuserHelper.getNozzleIdForItemStack(getInputStack());
    }

    // Setters

    private void setGas(int quantity) {
        this.gas = quantity;
    }

    private void setEnergy(int quantity) {
        this.energy = quantity;
    }

    private void setPlants(int quantity) {
        this.plants = quantity;
    }

    private void setNozzle(int id) {
        this.nozzle = id;
    }

    public int getField(EnumTileField field) {
        switch (field) {
            case ENERGY:
                return this.getEnergy();
            case GAS:
                return this.getGas();
            case PLANTS:
                return this.getPlants();
            case NOZZLE:
                return this.getNozzle();
            default:
                return 0;
        }
    }

    public void setField(EnumTileField field, int value) {
        switch (field) {
            case ENERGY:
                this.setEnergy(value);
                break;
            case GAS:
                this.setGas(value);
                break;
            case PLANTS:
                this.setPlants(value);
                break;
            case NOZZLE:
                this.setNozzle(value);
                break;
            default:
                break;
        }
    }

    // OpenComputers

    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_diffuser";
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getEnergyLevel(Context context, Arguments args) {
        int level = getEnergy();
        return new Object[] { level };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getGas(Context context, Arguments args) {
        int value = getGas();
        return new Object[] { value };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getPlants(Context context, Arguments args) {
        int value = getPlants();
        return new Object[] { value };
    }
}