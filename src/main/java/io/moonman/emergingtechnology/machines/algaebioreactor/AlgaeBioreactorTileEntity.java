package io.moonman.emergingtechnology.machines.algaebioreactor;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.AutomationItemStackHandler;
import io.moonman.emergingtechnology.handlers.energy.ConsumerEnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.fluid.FluidStorageHandler;
import io.moonman.emergingtechnology.handlers.fluid.SplitFluidStorageHandler;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.machines.AlgaeBioreactorHelper;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.fml.common.Optional;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class AlgaeBioreactorTileEntity extends MachineTileBase implements SimpleComponent {

    private static String[] fluidNames = new String[] { "water" };
    private static String[] gasNames = new String[] { "carbondioxide" };

    public FluidTank fluidHandler = new FluidStorageHandler(Reference.ALGAEBIOREACTOR_FLUID_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }
    };

    public FluidTank gasHandler = new FluidStorageHandler(Reference.ALGAEBIOREACTOR_GAS_CAPACITY) {
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

            return fluid == ModFluids.CARBON_DIOXIDE || fluid.getName() == "carbondioxide";
        }
    };

    public SplitFluidStorageHandler automationFluidHandler = new SplitFluidStorageHandler(fluidHandler, gasHandler,
            fluidNames, gasNames);

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.ALGAEBIOREACTOR_ENERGY_CAPACITY) {
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
    private int gas = this.gasHandler.getFluidAmount();
    private int energy = this.energyHandler.getEnergyStored();
    private int lightBoost = this.getLightBoost();

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
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.automationFluidHandler);
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
        this.setGas(compound.getInteger("GuiGas"));
        this.setProgress(compound.getInteger("GuiProgress"));
        this.setLightBoost(compound.getInteger("GuiLightBoost"));

        this.fluidHandler.readFromNBT(compound.getCompoundTag("FluidTank"));
        this.gasHandler.readFromNBT(compound.getCompoundTag("GasTank"));
        
        this.energyHandler.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setTag("Inventory", this.itemHandler.serializeNBT());

        compound.setInteger("GuiWater", this.getWater());
        compound.setInteger("GuiEnergy", this.getEnergy());
        compound.setInteger("GuiGas", this.getGas());
        compound.setInteger("GuiProgress", this.getProgress());
        compound.setInteger("GuiLightBoost", this.getLightBoost());

        NBTTagCompound fluidTag = new NBTTagCompound();
        NBTTagCompound gasTag = new NBTTagCompound();

        this.fluidHandler.writeToNBT(fluidTag);
        this.gasHandler.writeToNBT(gasTag);

        compound.setTag("FluidTank", fluidTag);
        compound.setTag("GasTank", gasTag);

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
        this.setEnergy(this.energyHandler.getEnergyStored());
        this.setWater(this.fluidHandler.getFluidAmount());
        this.setGas(this.gasHandler.getFluidAmount());
        this.setLightBoost(this.getLightBoost());

        doProcessing();
    }

    public void doProcessing() {

        ItemStack inputStack = getInputStack();

        // Nothing in input stack
        if (inputStack.getCount() == 0) {
            this.setProgress(0);
            return;
        }

        // Can't process this item
        if (!AlgaeBioreactorHelper.canProcessItemStack(inputStack)) {
            this.setProgress(0);
            return;
        }

        ItemStack outputStack = getOutputStack();
        IMachineRecipe recipe = AlgaeBioreactorHelper.getRecipeFromInputItemStack(inputStack);

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
        if (this.getWater() < EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorWaterUsage) {
            return;
        }

        // Not enough gas
        if (this.getGas() < EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorGasUsage) {
            return;
        }

        // Not enough energy
        if (this.getEnergy() < EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorEnergyUsage) {
            return;
        }

        this.energyHandler
                .extractEnergy(EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorEnergyUsage, false);

        this.fluidHandler.drain(EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorWaterUsage, true);
        this.gasHandler.drain(EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorGasUsage, true);

        this.setEnergy(this.energyHandler.getEnergyStored());
        this.setWater(this.fluidHandler.getFluidAmount());
        this.setGas(this.gasHandler.getFluidAmount());

        // Not enough operations performed
        if (this.getProgress() < AlgaeBioreactorHelper.getTimeTaken(this.getLightBoost())) {
            this.setProgress(this.getProgress() + 1);
            return;
        }

        itemHandler.insertItem(1, recipe.getOutput().copy(), false);
        itemHandler.extractItem(0, recipe.getInputCount(), false);

        this.setProgress(0);
    }

    public int getLightBoost() {
        return AlgaeBioreactorHelper.getLightBoost(getWorld(), getPos());
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

    public int getGas() {
        return this.gasHandler.getFluidAmount();
    }

    public int getEnergy() {
        return this.energyHandler.getEnergyStored();
    }

    public int getProgress() {
        return this.progress;
    }

    public int getBoost() {
        return this.getLightBoost();
    }

    // Setters

    private void setWater(int quantity) {
        this.water = quantity;
    }

    private void setGas(int quantity) {
        this.gas = quantity;
    }

    private void setEnergy(int quantity) {
        this.energy = quantity;
    }

    private void setProgress(int quantity) {
        this.progress = quantity;
    }

    private void setLightBoost(int quantity) {
        this.lightBoost = quantity;
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
            return this.getGas();
        case 3:
            return this.getProgress();
        case 4:
            return this.getLightBoost();
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
            this.setGas(value);
            break;
        case 3:
            this.setProgress(value);
            break;
        case 4:
            this.setLightBoost(value);
            break;
        }
    }

    // OpenComputers

    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_algaebioreactor";
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getWaterLevel(Context context, Arguments args) {
        int level = getWater();
        return new Object[] { level };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getGasLevel(Context context, Arguments args) {
        int level = getGas();
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