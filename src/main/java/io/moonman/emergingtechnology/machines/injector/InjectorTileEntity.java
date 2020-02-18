package io.moonman.emergingtechnology.machines.injector;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.AutomationItemStackHandler;
import io.moonman.emergingtechnology.handlers.energy.ConsumerEnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.fluid.InputOutputFluidStorageHandler;
import io.moonman.emergingtechnology.handlers.fluid.FluidStorageHandler;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.machines.InjectorHelper;
import io.moonman.emergingtechnology.init.ModFluids;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.MachineTileBase;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.fml.common.Optional;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class InjectorTileEntity extends MachineTileBase implements ITickable, SimpleComponent {

    private FluidTank waterHandler = new FluidStorageHandler(Reference.INJECTOR_FLUID_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }

    };

    private FluidTank nutrientFluidHandler = new FluidStorageHandler(Reference.INJECTOR_FLUID_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }

        @Override
        public boolean canFillFluidType(FluidStack fluidStack) {

            Fluid fluid = fluidStack.getFluid();

            if (fluid == null) {
                return false;
            }

            return fluid == ModFluids.NUTRIENT;
        }
    };

    public InputOutputFluidStorageHandler fluidTanksHandler = new InputOutputFluidStorageHandler(waterHandler,
            nutrientFluidHandler);

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.INJECTOR_ENERGY_CAPACITY) {
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

            if (slot == 1)
                return false;

            return InjectorHelper.canProcessItemStack(stack);
        }
    };

    public ItemStackHandler automationItemHandler = new AutomationItemStackHandler(itemHandler, 0, 1) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
            super.onContentsChanged(slot);
        }
    };

    private int water = this.waterHandler.getFluidAmount();
    private int fluid = this.nutrientFluidHandler.getFluidAmount();
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
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.fluidTanksHandler);
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
        this.setNutrientFluid(compound.getInteger("GuiFluid"));
        this.setEnergy(compound.getInteger("GuiEnergy"));
        this.setProgress(compound.getInteger("GuiProgress"));

        this.waterHandler.readFromNBT(compound.getCompoundTag("InputTank"));
        this.nutrientFluidHandler.readFromNBT(compound.getCompoundTag("OutputTank"));

        this.energyHandler.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setTag("Inventory", this.itemHandler.serializeNBT());

        compound.setInteger("GuiWater", this.getWater());
        compound.setInteger("GuiFluid", this.getNutrientFluid());
        compound.setInteger("GuiEnergy", this.getEnergy());
        compound.setInteger("GuiProgress", this.getProgress());

        NBTTagCompound fluidTag = new NBTTagCompound();
        NBTTagCompound nutrientTag = new NBTTagCompound();

        this.waterHandler.writeToNBT(fluidTag);
        this.nutrientFluidHandler.writeToNBT(nutrientTag);

        compound.setTag("InputTank", fluidTag);
        compound.setTag("OutputTank", nutrientTag);

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
    public void cycle() {
        doProcessing();
        pushToFluidConsumers();
    }

    public void doProcessing() {

        ItemStack inputStack = getInputStack();

        // Nothing in input stack
        if (inputStack.getCount() == 0) {
            this.setProgress(0);
            return;
        }

        // Can't process this item
        if (!InjectorHelper.canProcessItemStack(inputStack)) {
            this.setProgress(0);
            return;
        }

        ItemStack outputStack = getOutputStack();
        IMachineRecipe recipe = InjectorHelper.getRecipeFromInputItemStack(inputStack);

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

        // Fluid full
        if (this.getNutrientFluid() >= Reference.INJECTOR_FLUID_CAPACITY) {
            return;
        }

        // Not enough water
        if (this.getWater() < EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.injectorWaterBaseUsage) {
            return;
        }

        // Not enough energy
        if (this.getEnergy() < EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.injectorEnergyBaseUsage) {
            return;
        }

        this.energyHandler.extractEnergy(EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.injectorEnergyBaseUsage,
                false);

        // Not enough operations performed
        if (this.getProgress() < EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.injectorBaseTimeTaken) {
            this.setProgress(this.getProgress() + 1);
            return;
        }

        this.waterHandler.drain(EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.injectorWaterBaseUsage, true);
        this.nutrientFluidHandler.fill(new FluidStack(ModFluids.NUTRIENT,
                EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.injectorFluidGenerated), true);

        itemHandler.insertItem(1, recipe.getOutput().copy(), false);
        itemHandler.extractItem(0, recipe.getInputCount(), false);

        this.setProgress(0);
    }

    private void pushToFluidConsumers() {
        for (EnumFacing facing : EnumFacing.VALUES) {

            if (this.getNutrientFluid() < EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.injectorFluidTransferRate) {
                return;
            }

            TileEntity neighbour = this.world.getTileEntity(this.pos.offset(facing));

            // Return if no tile entity
            if (neighbour == null) {
                continue;
            }

            IFluidHandler neighbourFluidHandler = neighbour
                    .getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());

            // Return if neighbour has no fluid tank
            if (neighbourFluidHandler == null) {
                continue;
            }

            // Fill the neighbour
            int filled = neighbourFluidHandler.fill(new FluidStack(ModFluids.NUTRIENT,
                    EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.injectorFluidTransferRate), true);

            this.nutrientFluidHandler.drain(filled, true);
        }
    }

    public ItemStack getInputStack() {
        return itemHandler.getStackInSlot(0);
    }

    public ItemStack getOutputStack() {
        return itemHandler.getStackInSlot(1);
    }

    // Getters

    public int getWater() {
        return this.waterHandler.getFluidAmount();
    }

    public int getNutrientFluid() {
        return this.nutrientFluidHandler.getFluidAmount();
    }

    public int getEnergy() {
        return this.energyHandler.getEnergyStored();
    }

    public int getProgress() {
        return this.progress;
    }

    // Setters

    private void setWater(int quantity) {
        this.water = quantity;
    }

    private void setNutrientFluid(int quantity) {
        this.fluid = quantity;
    }

    private void setEnergy(int quantity) {
        this.energy = quantity;
    }

    private void setProgress(int quantity) {
        this.progress = quantity;
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
            return this.getWater();
        case 2:
            return this.getProgress();
        case 3:
            return this.getNutrientFluid();
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
            this.setWater(value);
            break;
        case 2:
            this.setProgress(value);
            break;
        case 3:
            this.setNutrientFluid(value);
            break;
        }
    }

    // OpenComputers

    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_injector";
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

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getFluid(Context context, Arguments args) {
        int value = getNutrientFluid();
        return new Object[] { value };
    }
}