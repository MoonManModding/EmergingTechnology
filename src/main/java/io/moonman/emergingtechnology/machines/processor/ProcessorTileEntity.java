package io.moonman.emergingtechnology.machines.processor;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.AutomationItemStackHandler;
import io.moonman.emergingtechnology.handlers.energy.ConsumerEnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.fluid.FluidStorageHandler;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.machines.classes.OptimiserPacket;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import io.moonman.emergingtechnology.machines.classes.tile.IOptimisableTile;
import io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.machines.ProcessorRecipes;
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
public class ProcessorTileEntity extends MachineTileBase implements SimpleComponent, IOptimisableTile {

    private OptimiserPacket packet = new OptimiserPacket();

    @Override
    public OptimiserPacket getPacket() {
        return this.packet;
    }

    @Override
    public void setPacket(OptimiserPacket packet) {
        this.packet = packet;
    }

    public FluidTank fluidHandler = new FluidStorageHandler(Reference.PROCESSOR_FLUID_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }
    };

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.PROCESSOR_ENERGY_CAPACITY) {
        @Override
        public void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }
    };

    public ConsumerEnergyStorageHandler consumerEnergyHandler = new ConsumerEnergyStorageHandler(energyHandler);

    public ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
            super.onContentsChanged(slot);
        }
    };

    public ItemStackHandler automationItemHandler = new AutomationItemStackHandler(itemHandler, 0, 1) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
            super.onContentsChanged(slot);
        }
    };

    private int water = this.fluidHandler.getFluidAmount();
    private int energy = this.energyHandler.getEnergyStored();

    private int progress = 0;

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
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.automationItemHandler);
        if (capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(this.consumerEnergyHandler);
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.itemHandler.deserializeNBT(compound.getCompoundTag("Inventory"));

        this.setWater(compound.getInteger("GuiWater"));
        this.setEnergy(compound.getInteger("GuiEnergy"));
        this.setProgress(compound.getInteger("GuiProgress"));

        this.fluidHandler.readFromNBT(compound);
        this.energyHandler.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setTag("Inventory", this.itemHandler.serializeNBT());

        compound.setInteger("GuiWater", this.getWater());
        compound.setInteger("GuiEnergy", this.getEnergy());
        compound.setInteger("GuiProgress", this.getProgress());

        this.fluidHandler.writeToNBT(compound);
        this.energyHandler.writeToNBT(compound);

        return compound;
    }



    @Override
    public void cycle() {
        this.setEnergy(this.energyHandler.getEnergyStored());
        this.setWater(this.fluidHandler.getFluidAmount());

        doProcessing();
        getPacket().reset();
    }

    public void doProcessing() {

        ItemStack inputStack = getInputStack();

        // Nothing in input stack
        if (inputStack.getCount() == 0) {
            this.setProgress(0);
            return;
        }

        // Can't process this item
        if (!ProcessorRecipes.isValidInput(inputStack)) {
            this.setProgress(0);
            return;
        }

        ItemStack outputStack = getOutputStack();
        IMachineRecipe recipe = ProcessorRecipes.getRecipeByInputItemStack(inputStack);

        // This is probably unneccessary
        if (recipe == null) {
            return;
        }

        // Output stack is full
        if (outputStack.getCount() == 64) {
            return;
        }

        // Output stack incompatible/non-empty
        if (!StackHelper.compareItemStacks(outputStack, recipe.getOutput())
                && !StackHelper.isItemStackEmpty(outputStack)) {
            return;
        }

        // Not enough room in output stack
        if (outputStack.getCount() + recipe.getOutput().getCount() > recipe.getOutput().getMaxStackSize()) {
            return;
        }

        // Not enough items in input stack
        if (inputStack.getCount() < recipe.getInput().getCount()) {
            return;
        }

        // Not enough water
        if (this.getWater() < getPacket().calculateFluidUse(EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.processorWaterBaseUsage)) {
            return;
        }

        // Not enough energy
        if (this.getEnergy() < getPacket().calculateEnergyUse(EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.processorEnergyBaseUsage)) {
            return;
        }

        this.energyHandler.extractEnergy(getPacket().calculateEnergyUse(EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.processorEnergyBaseUsage),
                false);

        this.fluidHandler.drain(getPacket().calculateFluidUse(EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.processorWaterBaseUsage), true);

        this.setEnergy(this.energyHandler.getEnergyStored());
        this.setWater(this.fluidHandler.getFluidAmount());

        // Not enough operations performed
        if (this.getProgress() < getPacket().calculateProgress(EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.processorBaseTimeTaken)) {
            this.setProgress(this.getProgress() + 1);
            return;
        }

        itemHandler.insertItem(1, recipe.getOutput().copy(), false);
        itemHandler.extractItem(0, recipe.getInputCount(), false);

        this.setProgress(0);
    }

    public ItemStack getInputStack() {
        return itemHandler.getStackInSlot(0);
    }

    public ItemStack getOutputStack() {
        return itemHandler.getStackInSlot(1);
    }

    // Getters

    public int getWater() {
        return this.fluidHandler.getFluidAmount();
    }

    public int getEnergy() {
        return this.energyHandler.getEnergyStored();
    }

    public int getProgress() {
        return this.progress;
    }

    public int getMaxProgress() {
        return EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.processorBaseTimeTaken;
    }

    // Setters

    private void setWater(int quantity) {
        this.water = quantity;
    }

    private void setEnergy(int quantity) {
        this.energy = quantity;
    }

    private void setProgress(int quantity) {
        this.progress = quantity;
    }

    public int getField(EnumTileField field) {
        switch (field) {
            case ENERGY:
                return this.getEnergy();
            case FLUID:
                return this.getWater();
            case PROGRESS:
                return this.getProgress();
            case MAXPROGRESS:
                return this.getMaxProgress();
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
            case PROGRESS:
                this.setProgress(value);
                break;
            default:
                break;
        }
    }

    // OpenComputers

    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_processor";
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getWaterLevel(Context context, Arguments args) {
        int level = getWater();
        return new Object[] { level };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getEnergyLevel(Context context, Arguments args) {
        int level = getEnergy();
        return new Object[] { level };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getProgress(Context context, Arguments args) {
        int value = getProgress();
        return new Object[] { value };
    }
}