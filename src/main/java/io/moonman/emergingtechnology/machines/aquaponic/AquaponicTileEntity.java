package io.moonman.emergingtechnology.machines.aquaponic;

import java.util.Arrays;

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
import io.moonman.emergingtechnology.machines.aquaponicport.AquaponicPortTileEntity;
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

    public ItemStackHandler itemHandler = new ItemStackHandler(12) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
            super.onContentsChanged(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return AquaponicRecipes.isValidInput(stack);
        }
    };

    public ItemStackHandler automationItemHandler = new AutomationItemStackHandler(itemHandler,
            Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8), Arrays.asList(9, 10, 11)) {
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
    private boolean fishOut = true;

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
        this.setFishOut(compound.getInteger("GuiFishOut") > 0);

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
        compound.setInteger("GuiMultiblock", this.getIsMultiblock() ? 1 : 0);
        compound.setInteger("GuiFishOut", this.getFishOut() ? 1 : 0);

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
        doFluidGeneration();
        pushToFluidConsumers();
        doFishBreeding();
        getPacket().reset();
    }

    private void doFishBreeding() {

        if (!enoughResources())
            return;

        AquaponicHelper.tryBreedFish(itemHandler, getFishOut());
    }

    private void doFluidGeneration() {

        if (!enoughResources())
            return;

        this.energyHandler.extractEnergy(getPacket().calculateEnergyUse(
                EmergingTechnologyConfig.HYDROPONICS_MODULE.AQUAPONIC.aquaponicEnergyBaseUsage), false);

        this.waterHandler.drain(getPacket().calculateFluidUse(
                EmergingTechnologyConfig.HYDROPONICS_MODULE.AQUAPONIC.aquaponicWaterBaseUsage), true);
        this.nutrientFluidHandler.fill(new FluidStack(ModFluids.NUTRIENT, getFluidGenerated()), true);
    }

    private boolean enoughResources() {
        // Output fluid full
        if (this.getNutrientFluid() >= Reference.AQUAPONIC_FLUID_CAPACITY) {
            return false;
        }

        // Not enough water
        if (this.getWater() < getPacket()
                .calculateFluidUse(EmergingTechnologyConfig.HYDROPONICS_MODULE.AQUAPONIC.aquaponicWaterBaseUsage)) {
            return false;
        }

        // Not enough energy
        if (this.getEnergy() < getPacket()
                .calculateEnergyUse(EmergingTechnologyConfig.HYDROPONICS_MODULE.AQUAPONIC.aquaponicEnergyBaseUsage)) {
            return false;
        }

        return true;
    }

    private void checkMuliblock() {
        boolean validMultiblock = AquaponicHelper.isValidMultiblockStructure(getWorld(), getPos(), getFacing());

        if (validMultiblock) {
            AquaponicHelper.setPortControllerBlocks(this, getWorld(), getPos(), getFacing());
            if (this.waterHandler.getFluidAmount() > 0) {
                AquaponicHelper.fillMultiblockStructure(getWorld(), getPos(), getFacing());
            }
        }

        setMultiblock(validMultiblock);
    }

    private void pushToFluidConsumers() {
        for (EnumFacing facing : EnumFacing.VALUES) {

            if (this.getNutrientFluid() < EmergingTechnologyConfig.HYDROPONICS_MODULE.AQUAPONIC.aquaponicFluidTransferRate) {
                return;
            }

            TileEntity neighbour = this.world.getTileEntity(this.pos.offset(facing));

            if (neighbour == null) {
                continue;
            }

            if (neighbour instanceof AquaponicPortTileEntity) {
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

    private int getFluidGenerated() {
        return AquaponicHelper.getFluidGeneratedFromFish(itemHandler);
    }

    private EnumFacing getFacing() {
        return EnumFacing.VALUES[getBlockMetadata()];
    }

    public FluidTank getNutrientFluidHandler() {
        return this.nutrientFluidHandler;
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

    public boolean getIsMultiblock() {
        return this.isMultiblock;
    }

    public boolean getFishOut() {
        return this.fishOut;
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

    private void setFishOut(boolean value) {
        this.fishOut = value;
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
                return this.getIsMultiblock() ? 1 : 0;
            case FISHOUTPUT:
                return this.getFishOut() ? 1 : 0;
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
            case FISHOUTPUT:
                this.setFishOut(value > 0);
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
        boolean value = getIsMultiblock();
        return new Object[] { value };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getFishOutput(Context context, Arguments args) {
        boolean value = getFishOut();
        return new Object[] { value };
    }
}