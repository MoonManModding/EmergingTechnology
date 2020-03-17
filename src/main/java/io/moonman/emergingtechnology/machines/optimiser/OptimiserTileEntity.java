package io.moonman.emergingtechnology.machines.optimiser;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.energy.ConsumerEnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.fluid.FluidStorageHandler;
import io.moonman.emergingtechnology.helpers.machines.OptimiserHelper;
import io.moonman.emergingtechnology.helpers.machines.classes.OptimiserPacket;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class OptimiserTileEntity extends MachineTileBase implements SimpleComponent {

    private final OptimiserPacket optimiserPacket = new OptimiserPacket();

    public FluidTank fluidHandler = new FluidStorageHandler(Reference.OPTIMISER_FLUID_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }
    };

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.OPTIMISER_ENERGY_CAPACITY) {
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
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return OptimiserHelper.getCoresFromItemStack(stack) > 0;
        }
    };

    private int water = this.fluidHandler.getFluidAmount();
    private int energy = this.energyHandler.getEnergyStored();

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
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.fluidHandler);
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

        this.setWater(compound.getInteger("GuiFluid"));
        this.setEnergy(compound.getInteger("GuiEnergy"));

        this.getPacket().energyModifier = compound.getInteger("PacketEnergy");
        this.getPacket().fluidModifier = compound.getInteger("PacketFluid");
        this.getPacket().gasModifier = compound.getInteger("PacketGas");
        this.getPacket().progressModifier = compound.getInteger("PacketProgress");

        this.fluidHandler.readFromNBT(compound);
        this.energyHandler.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setTag("Inventory", this.itemHandler.serializeNBT());

        compound.setInteger("GuiFluid", this.getWater());
        compound.setInteger("GuiEnergy", this.getEnergy());

        compound.setInteger("PacketEnergy", this.getPacket().energyModifier);
        compound.setInteger("PacketFluid", this.getPacket().fluidModifier);
        compound.setInteger("PacketGas", this.getPacket().gasModifier);
        compound.setInteger("PacketProgress", this.getPacket().progressModifier);

        this.fluidHandler.writeToNBT(compound);
        this.energyHandler.writeToNBT(compound);

        return compound;
    }

    @Override
    public void cycle() {
        this.setEnergy(this.energyHandler.getEnergyStored());
        this.setWater(this.fluidHandler.getFluidAmount());

        if (enoughResources()) {
            OptimiserHelper.pushPacketsToAdjacentMachines(getWorld(), getPos(), getPacket());
        }

        this.fluidHandler.drain(EmergingTechnologyConfig.ELECTRICS_MODULE.OPTIMISER.waterUsage, true);
        this.energyHandler.extractEnergy(EmergingTechnologyConfig.ELECTRICS_MODULE.OPTIMISER.energyUsage, false);
    }

    private OptimiserPacket getPacket() {
        return this.optimiserPacket;
    }

    private boolean enoughResources() {
        return this.energyHandler.getEnergyStored() >= EmergingTechnologyConfig.ELECTRICS_MODULE.OPTIMISER.energyUsage
                && this.fluidHandler.getFluidAmount() >= EmergingTechnologyConfig.ELECTRICS_MODULE.OPTIMISER.waterUsage;
    }

    public ItemStack getInputStack() {
        return itemHandler.getStackInSlot(0);
    }

    // Getters

    public int getWater() {
        return this.fluidHandler.getFluidAmount();
    }

    public int getEnergy() {
        return this.energyHandler.getEnergyStored();
    }

    public int getCores() {
        return OptimiserHelper.getCoresFromItemStack(this.itemHandler.getStackInSlot(0));
    }

    // Setters

    private void setWater(int quantity) {
        this.water = quantity;
    }

    private void setEnergy(int quantity) {
        this.energy = quantity;
    }

    public int getField(EnumTileField field) {
        switch (field) {
            case ENERGY:
                return this.getEnergy();
            case FLUID:
                return this.getWater();
            case OPTIMISERENERGY:
                return this.getPacket().energyModifier;
            case OPTIMISERPROGRESS:
                return this.getPacket().progressModifier;
            case OPTIMISERGAS:
                return this.getPacket().gasModifier;
            case OPTIMISERFLUID:
                return this.getPacket().fluidModifier;
            case OPTIMISERCORES:
                return this.getCores();
            default:
                return 0;
        }
    }

    public void setField(EnumTileField field, int value) {
        switch (field) {
            case ENERGY:
                this.setEnergy(value);
                break;
            case FLUID:
                this.setWater(value);
                break;
            case OPTIMISERENERGY:
                this.getPacket().energyModifier = value;
                break;
            case OPTIMISERPROGRESS:
                this.getPacket().progressModifier = value;
                break;
            case OPTIMISERGAS:
                this.getPacket().gasModifier = value;
                break;
            case OPTIMISERFLUID:
                this.getPacket().fluidModifier = value;
                break;
            default:
                break;
        }
    }

    // OpenComputers

    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_optimiser";
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getEnergyLevel(Context context, Arguments args) {
        int level = getEnergy();
        return new Object[] { level };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getWater(Context context, Arguments args) {
        int value = getWater();
        return new Object[] { value };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getCoresDistribution(Context context, Arguments args) {
        Object value = this.getPacket();
        return new Object[] { value };
    }
}