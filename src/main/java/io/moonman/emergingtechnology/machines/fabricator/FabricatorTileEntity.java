package io.moonman.emergingtechnology.machines.fabricator;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.AutomationItemStackHandler;
import io.moonman.emergingtechnology.handlers.EnergyStorageHandler;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.machines.FabricatorHelper;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.MachineTileBase;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.fml.common.Optional;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class FabricatorTileEntity extends MachineTileBase implements ITickable, SimpleComponent {

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.FABRICATOR_ENERGY_CAPACITY) {
        @Override
        public void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }
    };

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

    private int tick = 0;

    private int energy = this.energyHandler.getEnergyStored();

    private int progress = 0;

    private int selection = 0;

    private boolean printing = false;

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
            return (T) this.automationItemHandler;
        if (capability == CapabilityEnergy.ENERGY)
            return (T) this.energyHandler;
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

        this.energyHandler.writeToNBT(compound);

        return compound;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void update() {

        if (this.isClient()) {
            return;
        }

        if (tick < 10) {
            tick++;
            return;
        } else {

            this.setEnergy(this.getEnergy());

            this.doPrinting();

            tick = 0;
        }
    }

    public void doPrinting() {

        if (!this.printing) {
            return;
        }

        ItemStack inputStack = getInputStack();

        // Nothing in input stack
        if (inputStack.getCount() == 0) {
            this.setProgress(0);
            return;
        }

        ItemStack outputStack = getOutputStack();
        FabricatorRecipe recipe = FabricatorHelper.getFabricatorRecipeByIndex(selection);

        // Recipe validation just in case
        if (recipe == null || recipe.getInput() == null || recipe.getOutput() == null || recipe.cost == 0) {
            this.setProgress(0);
            return;
        }

        // Check is correct input
        if (!StackHelper.compareItemStacks(inputStack, recipe.getInput())) {
            return;
        }

        // Not enough items in input
        if (inputStack.getCount() < recipe.cost) {
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

        // Not enough energy
        if (this.getEnergy() < EmergingTechnologyConfig.POLYMERS_MODULE.FABRICATOR.fabricatorEnergyBaseUsage) {
            return;
        }

        this.energyHandler.extractEnergy(EmergingTechnologyConfig.POLYMERS_MODULE.FABRICATOR.fabricatorEnergyBaseUsage,
                false);

        this.setEnergy(this.energyHandler.getEnergyStored());

        // Not enough operations performed
        if (this.getProgress() < EmergingTechnologyConfig.POLYMERS_MODULE.FABRICATOR.fabricatorBaseTimeTaken) {
            this.setProgress(this.getProgress() + 1);
            return;
        }

        getInputStack().shrink(recipe.cost);

        if (outputStack.getCount() > 0) {
            outputStack.grow(recipe.getOutput().getCount());
        } else {
            itemHandler.insertItem(1, recipe.getOutput().copy(), false);
        }

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

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) != this ? false
                : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
                        (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public int getField(int id) {
        switch (id) {
        case 0:
            return this.getEnergy();
        case 1:
            return this.getProgress();
        case 2:
            return this.getSelection();
        case 3:
            return this.getIsPrinting();
        default:
            return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id) {
        case 0:
            this.setEnergy(value);
            break;
        case 1:
            this.setProgress(value);
            break;
        case 2:
            this.setSelection(value);
            break;
        case 3:
            this.setIsPrinting(value);
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

            int max = RecipeProvider.fabricatorRecipes.size() -1;

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