package io.moonman.emergingtechnology.machines.scaffolder;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.AutomationItemStackHandler;
import io.moonman.emergingtechnology.handlers.energy.ConsumerEnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.machines.classes.OptimiserPacket;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import io.moonman.emergingtechnology.machines.classes.tile.IOptimisableTile;
import io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase;
import io.moonman.emergingtechnology.recipes.machines.ScaffolderRecipes;
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
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class ScaffolderTileEntity extends MachineTileBase implements SimpleComponent, IOptimisableTile {

    private OptimiserPacket packet = new OptimiserPacket();
    
    @Override
    public OptimiserPacket getPacket() {
        return this.packet;
    }

    @Override
    public void addPacket(OptimiserPacket packet) {
        getPacket().merge(packet);
    }


    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.SCAFFOLDER_ENERGY_CAPACITY) {
        @Override
        public void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }
    };

    public ConsumerEnergyStorageHandler consumerEnergyHandler = new ConsumerEnergyStorageHandler(energyHandler);

    public ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
            super.onContentsChanged(slot);
        }

        @Override
        public int getSlotLimit(int slot) {
            if (slot == 2) {
                return 1;
            }

            return super.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack itemStack) {
            if (slot == 0) {
                return ScaffolderRecipes.isItemStackValidScaffold(itemStack);
            }

            if (slot == 2) {
                return ScaffolderRecipes.isValidInput(itemStack);
            }

            return false;
        }
    };

    public ItemStackHandler automationItemHandler = new AutomationItemStackHandler(itemHandler, 0, 1) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
            super.onContentsChanged(slot);
        }

        @Override
        public int getSlotLimit(int slot) {
            if (slot == 2) {
                return 1;
            }

            return super.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack itemStack) {
            if (slot == 0) {
                return ScaffolderRecipes.isItemStackValidScaffold(itemStack);
            }

            if (slot == 2) {
                return ScaffolderRecipes.isValidInput(itemStack);
            }

            return false;
        }
    };

    private int energy = this.energyHandler.getEnergyStored();

    private int progress = 0;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        if (capability == CapabilityEnergy.ENERGY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
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

        this.setEnergy(compound.getInteger("GuiEnergy"));
        this.setProgress(compound.getInteger("GuiProgress"));

        this.energyHandler.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Inventory", this.itemHandler.serializeNBT());

        compound.setInteger("GuiEnergy", energy);
        compound.setInteger("GuiProgress", progress);

        this.energyHandler.writeToNBT(compound);

        return compound;
    }

    @Override
    public void cycle() {
        setEnergy(getEnergy());
        doScaffoldingProcess();
        getPacket().reset();
    }

    public void doScaffoldingProcess() {

        ItemStack scaffoldStack = getScaffoldStack();
        ItemStack sampleStack = getSampleStack();

        // Nothing in input stack
        if (scaffoldStack.getCount() == 0 || sampleStack.getCount() == 0) {
            this.setProgress(0);
            return;
        }

        // Can't scaffold this item
        if (!ScaffolderRecipes.isItemStackValidScaffold(scaffoldStack)
                || !ScaffolderRecipes.isValidInput(sampleStack)) {
            this.setProgress(0);
            return;
        }

        ItemStack outputStack = getOutputStack();
        ItemStack plannedStack = ScaffolderRecipes.getOutputByItemStack(sampleStack);

        // This is probably unneccessary
        if (plannedStack == null || plannedStack.isEmpty()) {
            return;
        }

        // Output stack is full
        if (outputStack.getCount() == 64) {
            return;
        }

        // Output stack incompatible/non-empty
        if (!StackHelper.compareItemStacks(outputStack, plannedStack) && !StackHelper.isItemStackEmpty(outputStack)) {
            return;
        }

        // Not enough energy
        if (this.getEnergy() < getPacket().calculateEnergyUse(EmergingTechnologyConfig.SYNTHETICS_MODULE.SCAFFOLDER.scaffolderEnergyUsage)) {
            return;
        }

        this.energyHandler.extractEnergy(getPacket().calculateEnergyUse(EmergingTechnologyConfig.SYNTHETICS_MODULE.SCAFFOLDER.scaffolderEnergyUsage),
                false);

        // Not enough operations performed
        if (this.getProgress() < getPacket().calculateProgress(EmergingTechnologyConfig.SYNTHETICS_MODULE.SCAFFOLDER.scaffolderBaseTimeTaken)) {
            this.setProgress(this.getProgress() + 1);
            return;
        }

        itemHandler.insertItem(1, plannedStack.copy(), false);
        itemHandler.extractItem(0, 1, false);

        this.energyHandler.extractEnergy(getPacket().calculateEnergyUse(EmergingTechnologyConfig.SYNTHETICS_MODULE.SCAFFOLDER.scaffolderEnergyUsage),
                false);

        this.setProgress(0);
    }

    public ItemStack getScaffoldStack() {
        return itemHandler.getStackInSlot(0);
    }

    public ItemStack getSampleStack() {
        return itemHandler.getStackInSlot(2);
    }

    public ItemStack getOutputStack() {
        return itemHandler.getStackInSlot(1);
    }

    // Getters

    public int getEnergy() {
        return this.energyHandler.getEnergyStored();
    }

    public int getProgress() {
        return this.progress;
    }

    public int getMaxProgress() {
        return getPacket().calculateProgress(EmergingTechnologyConfig.SYNTHETICS_MODULE.SCAFFOLDER.scaffolderBaseTimeTaken);
    }

    // Setters

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
            case PROGRESS:
                return this.getProgress();
            default:
                return 0;
        }
    }

    public void setField(EnumTileField field, int value) {
        switch (field) {
            case ENERGY:
                this.setEnergy(value);
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
        return "etech_scaffolder";
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