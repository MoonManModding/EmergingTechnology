package io.moonman.emergingtechnology.machines.aquaponic;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.AutomationItemStackHandler;
import io.moonman.emergingtechnology.handlers.energy.ConsumerEnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.fluid.FluidStorageHandler;
import io.moonman.emergingtechnology.handlers.fluid.InputOutputFluidStorageHandler;
import io.moonman.emergingtechnology.helpers.machines.AquaponicHelper;
import io.moonman.emergingtechnology.helpers.machines.classes.OptimiserPacket;
import io.moonman.emergingtechnology.init.ModFluids;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import io.moonman.emergingtechnology.machines.classes.tile.IOptimisableTile;
import io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase;
import io.moonman.emergingtechnology.recipes.machines.AquaponicRecipes;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class AquaponicTileEntity extends MachineTileBase implements SimpleComponent, IOptimisableTile {

    private OptimiserPacket packet = new OptimiserPacket();

    @Override
    public OptimiserPacket getPacket() {
        return this.packet;
    }

    @Override
    public void addPacket(OptimiserPacket packet) {
        getPacket().merge(packet);
    }

    private FluidTank waterHandler = new FluidStorageHandler(Reference.AQUAPONIC_FLUID_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }

    };

    private FluidTank nutrientFluidHandler = new FluidStorageHandler(Reference.AQUAPONIC_FLUID_CAPACITY) {
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

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.AQUAPONIC_ENERGY_CAPACITY) {
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

            return AquaponicRecipes.isValidInput(stack);
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
    private boolean isMultiblock = false;

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
        this.setMultiblock(compound.getInteger("GuiMultiblock") > 0);

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
        compound.setInteger("GuiMultiblock", this.getMultiblock() ? 1 : 0);

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
    public void cycle() {
        checkMuliblock();
        pushToFluidConsumers();
        getPacket().reset();
    }

    private void checkMuliblock() {
        boolean validMultiblock = AquaponicHelper.isValidMultiblockStructure(getWorld(), getPos(), getFacing());

        if (validMultiblock && this.waterHandler.getFluidAmount() > 0) {
            AquaponicHelper.fillMultiblockStructure(getWorld(), getPos(), getFacing());
        }

        setMultiblock(validMultiblock);
    }

    private void pushToFluidConsumers() {
        for (EnumFacing facing : EnumFacing.VALUES) {

            if (this.getNutrientFluid() < EmergingTechnologyConfig.HYDROPONICS_MODULE.AQUAPONIC.aquaponicFluidTransferRate) {
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
                    EmergingTechnologyConfig.HYDROPONICS_MODULE.AQUAPONIC.aquaponicFluidTransferRate), true);

            this.nutrientFluidHandler.drain(filled, true);
        }
    }

    private EnumFacing getFacing() {
        return EnumFacing.VALUES[getBlockMetadata()];
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

    public boolean getMultiblock() {
        return this.isMultiblock;
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

    private void setMultiblock(boolean value) {
        this.isMultiblock = value;
    }

    public int getField(EnumTileField field) {
        switch (field) {
            case ENERGY:
                return this.getEnergy();
            case FLUID:
                return this.getWater();
            case PROGRESS:
                return this.getProgress();
            case NUTRIENT:
                return this.getNutrientFluid();
            case MULTIBLOCK:
                return this.getMultiblock() ? 1 : 0;
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
            case NUTRIENT:
                this.setNutrientFluid(value);
                break;
            case MULTIBLOCK:
                this.setMultiblock(value > 0);
            default:
                break;
        }
    }

    // OpenComputers

    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_aquaponic";
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

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] isValidStructure(Context context, Arguments args) {
        boolean value = getMultiblock();
        return new Object[] { value };
    }
}