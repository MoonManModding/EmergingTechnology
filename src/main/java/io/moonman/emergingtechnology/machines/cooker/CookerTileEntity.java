package io.moonman.emergingtechnology.machines.cooker;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.AutomationItemStackHandler;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.machines.CookerHelper;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase;
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
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class CookerTileEntity extends MachineTileBase implements SimpleComponent {

    public ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
            super.onContentsChanged(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack itemStack) {
            return CookerHelper.canCookItemStack(itemStack);
        }
    };

    public ItemStackHandler automationItemHandler = new AutomationItemStackHandler(itemHandler, 0, 1) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
            super.onContentsChanged(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack itemStack) {
            return CookerHelper.canCookItemStack(itemStack);
        }
    };

    private int heat = 0;

    private int progress = 0;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.automationItemHandler);
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.itemHandler.deserializeNBT(compound.getCompoundTag("Inventory"));

        this.setProgress(compound.getInteger("GuiProgress"));
        this.setHeat(compound.getInteger("GuiHeat"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Inventory", this.itemHandler.serializeNBT());

        compound.setInteger("GuiProgress", progress);
        compound.setInteger("GuiHeat", heat);

        return compound;
    }



    @Override
    public void cycle() {
        doHeatProcess();
        doCookingProcess();
    }

    public void doHeatProcess() {

        boolean canSeeSky = world.canSeeSky(getPos());
        boolean isDayTime = world.isDaytime();

        if (canSeeSky && isDayTime) {
            int heatAdded = EmergingTechnologyConfig.SYNTHETICS_MODULE.COOKER.cookerBaseHeatGain;
            this.setHeat(this.getHeat() + heatAdded);
        } else {
            int heatRemoved = EmergingTechnologyConfig.SYNTHETICS_MODULE.COOKER.cookerBaseHeatLoss;
            this.setHeat(this.getHeat() - heatRemoved);
        }
    }

    public void doCookingProcess() {

        ItemStack inputStack = getInputStack();

        // Nothing in input stack
        if (inputStack.getCount() == 0) {
            this.setProgress(0);
            return;
        }

        // Can't cook this item
        if (!CookerHelper.canCookItemStack(inputStack)) {
            this.setProgress(0);
            return;
        }

        ItemStack outputStack = getOutputStack();
        ItemStack plannedStack = CookerHelper.getPlannedStackFromItemStack(inputStack);

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
        if (this.getHeat() < EmergingTechnologyConfig.SYNTHETICS_MODULE.COOKER.cookerRequiredCookingHeat) {
            return;
        }

        // Not enough operations performed
        if (this.getProgress() < EmergingTechnologyConfig.SYNTHETICS_MODULE.COOKER.cookerBaseTimeTaken) {
            this.setProgress(this.getProgress() + 1);
            return;
        }

        itemHandler.insertItem(1, plannedStack.copy(), false);
        itemHandler.extractItem(0, 1, false);

        this.setProgress(0);
    }

    public ItemStack getInputStack() {
        return itemHandler.getStackInSlot(0);
    }

    public ItemStack getOutputStack() {
        return itemHandler.getStackInSlot(1);
    }

    // Getters

    public int getHeat() {
        return this.heat;
    }

    public int getProgress() {
        return this.progress;
    }

    public int getMaxProgress() {
        return EmergingTechnologyConfig.SYNTHETICS_MODULE.COOKER.cookerBaseTimeTaken;
    }

    // Setters

    private void setHeat(int quantity) {
        if (quantity > Reference.COOKER_HEAT_CAPACITY) {
            quantity = Reference.COOKER_HEAT_CAPACITY;
        }

        if (quantity < 0) {
            quantity = 0;
        }
        this.heat = quantity;
    }

    private void setProgress(int quantity) {
        this.progress = quantity;
    }

    public int getField(EnumTileField field) {
        switch (field) {
            case HEAT:
                return this.getHeat();
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
            case HEAT:
                this.setHeat(value);
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
        return "etech_cooker";
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getHeatLevel(Context context, Arguments args) {
        int level = getHeat();
        return new Object[] { level };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getProgress(Context context, Arguments args) {
        int value = getProgress();
        return new Object[] { value };
    }
}