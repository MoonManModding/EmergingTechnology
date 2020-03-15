package io.moonman.emergingtechnology.machines.fabricator;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.AutomationItemStackHandler;
import io.moonman.emergingtechnology.handlers.energy.ConsumerEnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.machines.FabricatorHelper;
import io.moonman.emergingtechnology.helpers.machines.classes.OptimiserPacket;
import io.moonman.emergingtechnology.helpers.machines.enums.FabricatorStatusEnum;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import io.moonman.emergingtechnology.machines.classes.tile.IOptimisableTile;
import io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase;
import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import io.moonman.emergingtechnology.recipes.machines.FabricatorRecipes;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class FabricatorTileEntity extends MachineTileBase implements SimpleComponent, IOptimisableTile {

    private OptimiserPacket packet = new OptimiserPacket();

    @Override
    public OptimiserPacket getPacket() {
        return this.packet;
    }

    @Override
    public void setPacket(OptimiserPacket packet) {
        this.packet = packet;
    }

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.FABRICATOR_ENERGY_CAPACITY) {
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

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return FabricatorHelper.isValidItemStack(stack) && slot == 0;
        }
    };

    public ItemStackHandler automationItemHandler = new AutomationItemStackHandler(itemHandler, 0, 1) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
            super.onContentsChanged(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return FabricatorHelper.isValidItemStack(stack);
        }
    };

    private int energy = this.energyHandler.getEnergyStored();

    private int progress = 0;

    private int selection = 0;

    private boolean printing = false;

    private FabricatorStatusEnum status = FabricatorStatusEnum.IDLE;

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
        this.setSelection(compound.getInteger("GuiSelection"));
        this.setIsPrinting(compound.getInteger("GuiPrinting"));
        this.setStatus(compound.getInteger("GuiStatus"));

        this.energyHandler.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Inventory", this.itemHandler.serializeNBT());

        compound.setInteger("GuiEnergy", energy);
        compound.setInteger("GuiProgress", progress);
        compound.setInteger("GuiSelection", selection);
        compound.setInteger("GuiPrinting", getIsPrinting());
        compound.setInteger("GuiStatus", getStatus());

        this.energyHandler.writeToNBT(compound);

        return compound;
    }

    @Override
    public void cycle() {
        this.setEnergy(this.getEnergy());
        this.doPrinting();
        getPacket().reset();
    }

    public void doPrinting() {

        if (!this.printing) {
            status = FabricatorStatusEnum.IDLE;
            return;
        }

        ItemStack inputStack = getInputStack();

        // Nothing in input stack
        if (inputStack.getCount() == 0) {
            this.setProgress(0);
            status = FabricatorStatusEnum.INSUFFICIENT_INPUT;
            return;
        }

        ItemStack outputStack = getOutputStack();
        FabricatorRecipe recipe = (FabricatorRecipe) FabricatorHelper.getFabricatorRecipeByIndex(selection);

        // Recipe validation just in case
        if (recipe == null || recipe.getInput() == null || recipe.getOutput() == null || recipe.cost == 0) {
            this.setProgress(0);
            status = FabricatorStatusEnum.ERROR;
            return;
        }

        // Check is correct input
        if (!StackHelper.compareItemStacks(inputStack, recipe.getInput())) {
            status = FabricatorStatusEnum.INVALID_INPUT;
            return;
        }

        // Not enough items in input
        if (inputStack.getCount() < recipe.cost) {
            status = FabricatorStatusEnum.INSUFFICIENT_INPUT;
            return;
        }

        // Output stack is full
        if (outputStack.getCount() == 64) {
            status = FabricatorStatusEnum.OUTPUT_FULL;
            return;
        }

        // Output stack incompatible/non-empty
        if (!StackHelper.compareItemStacks(outputStack, recipe.getOutput())
                && !StackHelper.isItemStackEmpty(outputStack)) {
            status = FabricatorStatusEnum.INVALID_OUTPUT;
            return;
        }

        // Not enough room in output stack
        if (outputStack.getCount() + recipe.getOutput().getCount() > recipe.getOutput().getMaxStackSize()) {
            status = FabricatorStatusEnum.OUTPUT_FULL;
            return;
        }

        // Not enough energy
        if (this.getEnergy() < getPacket().calculateEnergyUse(EmergingTechnologyConfig.POLYMERS_MODULE.FABRICATOR.fabricatorEnergyBaseUsage)) {
            status = FabricatorStatusEnum.INSUFFICIENT_ENERGY;
            return;
        }

        this.energyHandler.extractEnergy(getPacket().calculateEnergyUse(EmergingTechnologyConfig.POLYMERS_MODULE.FABRICATOR.fabricatorEnergyBaseUsage),
                false);

        this.setEnergy(this.energyHandler.getEnergyStored());

        // Not enough operations performed
        if (this.getProgress() < getPacket().calculateProgress(EmergingTechnologyConfig.POLYMERS_MODULE.FABRICATOR.fabricatorBaseTimeTaken)) {
            this.setProgress(this.getProgress() + 1);
            status = FabricatorStatusEnum.RUNNING;
            return;
        }

        itemHandler.insertItem(1, recipe.getOutput().copy(), false);
        itemHandler.extractItem(0, recipe.cost, false);

        status = FabricatorStatusEnum.RUNNING;

        this.setProgress(0);
    }

    public ItemStack getInputStack() {
        return itemHandler.getStackInSlot(0);
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

    public int getSelection() {
        return this.selection;
    }

    public int getIsPrinting() {
        return this.printing ? 1 : 0;
    }

    public int getStatus() {
        return FabricatorStatusEnum.getId(status);
    }

    public int getMaxProgress() {
        return EmergingTechnologyConfig.POLYMERS_MODULE.FABRICATOR.fabricatorBaseTimeTaken;
    }

    // Setters

    private void setEnergy(int quantity) {
        this.energy = quantity;
    }

    private void setProgress(int quantity) {
        this.progress = quantity;
    }

    private void setSelection(int id) {

        if (this.selection != id) {
            this.setProgress(0);
        }

        this.selection = id;
    }

    private void setIsPrinting(int id) {
        this.printing = id > 0;
    }

    private void setStatus(int id) {
        this.status = FabricatorStatusEnum.getById(id);
    }

    public int getField(EnumTileField field) {
        switch (field) {
            case ENERGY:
                return this.getEnergy();
            case PROGRESS:
                return this.getProgress();
            case FABRICATORSELECTION:
                return this.getSelection();
            case FABRICATORISPRINTING:
                return this.getIsPrinting();
            case FABRICATORSTATUS:
                return this.getStatus();
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
            case FABRICATORSELECTION:
                this.setSelection(value);
                break;
            case FABRICATORISPRINTING:
                this.setIsPrinting(value);
                break;
            case FABRICATORSTATUS:
                this.setStatus(value);
                break;
            default:
                break;
        }
    }

    // OpenComputers

    private void startPrinting() {
        this.setIsPrinting(1);
        this.markDirty();
    }

    private void stopPrinting() {
        this.setIsPrinting(0);
        this.markDirty();
    }

    private void setProgram(int index) {
        this.setSelection(index);
        this.markDirty();
    }

    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_fabricator";
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

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getProgram(Context context, Arguments args) {
        int value = getSelection();
        return new Object[] { value };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getIsPrinting(Context context, Arguments args) {
        boolean value = getIsPrinting() > 0;
        return new Object[] { value };
    }

    // NON-FUNCTIONAL
    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] startPrint(Context context, Arguments args) {
        this.startPrinting();
        return new Object[] { "Printing started." };
    }

    // NON-FUNCTIONAL
    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] stopPrint(Context context, Arguments args) {
        this.stopPrinting();
        return new Object[] { "Printing stopped." };
    }

    // NON-FUNCTIONAL
    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] setProgram(Context context, Arguments args) {

        boolean success = false;
        String message = "There was an error setting the program";

        try {

            int max = FabricatorRecipes.getRecipes().size() - 1;

            Object[] arguments = args.toArray();

            if (arguments == null) {
                message = "Invalid argument. Must be integer between 0 and " + max;
                return new Object[] { success, message };
            }

            Double thing = (Double) arguments[0];

            int program = thing.intValue();

            if (program < 0 || program > max) {
                message = "Invalid argument. Must be integer between 0 and " + max;
            } else {
                this.setProgram(program);
                success = true;
                message = "Fabricator program set to " + program;
            }

        } catch (Exception ex) {
            message = ex.toString();
        }

        return new Object[] { success, message };
    }
}