package io.moonman.emergingtechnology.machines.hydroponic;

import java.util.Random;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.energy.ConsumerEnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.fluid.FluidStorageHandler;
import io.moonman.emergingtechnology.helpers.PlantHelper;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.machines.HydroponicHelper;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase;
import io.moonman.emergingtechnology.machines.light.Light;
import io.moonman.emergingtechnology.machines.light.LightTileEntity;
import io.moonman.emergingtechnology.providers.ModMediumProvider;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class HydroponicTileEntity extends MachineTileBase implements SimpleComponent {

    public FluidTank fluidHandler = new FluidStorageHandler(Reference.HYDROPONIC_FLUID_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            markDirty();
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return HydroponicHelper.isFluidValid(fluid);
        }
    };

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.HYDROPONIC_ENERGY_CAPACITY) {
        @Override
        public void onContentsChanged() {
            super.onContentsChanged();
            markDirty();
        }
    };

    public ConsumerEnergyStorageHandler consumerEnergyHandler = new ConsumerEnergyStorageHandler(energyHandler);

    public ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            markDirtyClient();
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return HydroponicHelper.isItemStackValid(stack);
        }
    };

    private int tick = 0;

    private int water = this.fluidHandler.getFluidAmount();
    private int energy = this.energyHandler.getEnergyStored();
    private int mediumId = this.getGrowthMediumId();

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

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

        this.setWater(compound.getInteger("GuiWater"));
        this.setEnergy(compound.getInteger("GuiEnergy"));

        this.fluidHandler.readFromNBT(compound);

        this.energyHandler.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Inventory", this.itemHandler.serializeNBT());
        compound.setInteger("GuiWater", water);
        compound.setInteger("GuiEnergy", energy);

        this.fluidHandler.writeToNBT(compound);
        this.energyHandler.writeToNBT(compound);

        return compound;
    }



    @Override
    public void cycle() {

            this.setWater(this.fluidHandler.getFluidAmount());
            this.setEnergy(this.energyHandler.getEnergyStored());
            this.setGrowthMediumId(this.getGrowthMediumId());

            // Do all the plant growth work and let us know how it went
            boolean growSucceeded = doGrowthMultiplierProcess();

            if (growSucceeded) {
                this.doMediumDestroyProcess();
            }

            // If beds require power to pump
            if (EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growBedsRequireEnergy) {
                boolean enoughPower = doPowerUsageProcess();

                // Check power before pumping
                if (enoughPower) {
                    doWaterUsageProcess(growSucceeded);
                }

            } else {
                doWaterUsageProcess(growSucceeded);
            }
    }

    public boolean doPowerUsageProcess() {

        // Calculate energy required
        int energyRequired = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growBedsEnergyUsePerCycle;

        if (this.energy >= energyRequired) {
            this.energyHandler.extractEnergy(energyRequired, false);
            this.setEnergy(this.energyHandler.getEnergyStored());
            return true;
        }

        return false;
    }

    public void doWaterUsageProcess(boolean growSucceeded) {

        if (this.getFluid() == null)
            return;

        int waterFluidUse = HydroponicHelper.getFluidUseForMedium(this.getGrowthMediumId());

        int fluidUsage = growSucceeded ? waterFluidUse : 0;

        // Drain water
        this.fluidHandler
                .drain(EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growBedWaterUsePerCycle * fluidUsage, true);

        // If enough water to transfer...
        if (this.water >= EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growBedWaterTransferRate) {

            // Get the direction this grow bed is facing
            EnumFacing facing = this.world.getBlockState(this.pos).getValue(Hydroponic.FACING);

            // Grab the vector
            Vec3i vector = facing.getDirectionVec();

            // Get neighbour based on facing vector
            TileEntity neighbour = this.world.getTileEntity(this.pos.add(vector));

            // Is neighbour a grow bed?
            if (neighbour instanceof HydroponicTileEntity) {
                HydroponicTileEntity targetTileEntity = (HydroponicTileEntity) neighbour;

                FluidStack fluidStack = this.getFluidStack();

                if (fluidStack == null) {
                    return;
                }

                if (fluidStack.getFluid() == null) {
                    return;
                }

                // Fill the neighbour and get amount filled
                int filled = targetTileEntity.fluidHandler.fill(new FluidStack(this.fluidHandler.getFluid().getFluid(),
                        EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growBedWaterTransferRate), true);

                if (filled > 0) {
                    // Drain self from amount
                    this.fluidHandler.drain(filled, true);
                    this.setWater(this.fluidHandler.getFluidAmount());
                }
            }
        }
    }

    public boolean doGrowthMultiplierProcess() {

        // Get blockstate of whatever is on top of block
        IBlockState aboveBlockState = this.world.getBlockState(this.pos.add(0, 1, 0));

        // If there is no blockstate above, abandon ship
        if (aboveBlockState == null) {
            return false;
        }

        // Get the blockstate's gooey block center
        Block aboveBlock = aboveBlockState.getBlock();

        // Get internal growth medium
        ItemStack growthMedium = this.getItemStack();

        // Get threshold from medium
        int growthProbabilityThreshold = HydroponicHelper.getGrowthProbabilityForMedium(growthMedium);

        // If it ain't a plant, we ain't interested
        if (!PlantHelper.isPlantBlock(aboveBlock)) {
            return false;
        }

        // Check if fluids provide boost
        int totalFluidBoost = 0;

        // If this medium works especially well in this fluid, we can give it a little
        // boost
        int growthProbabilityBoostModifierFromFluid = HydroponicHelper
                .getSpecificPlantGrowthBoostForFluidStack(this.fluidHandler.getFluid(), aboveBlockState);
        int growthMultiplierFromFluid = HydroponicHelper.getGrowthProbabilityForFluid(this.fluidHandler.getFluid());

        // If no boost, just add regular growth modifier.
        if (growthProbabilityBoostModifierFromFluid == 0) {
            totalFluidBoost += growthMultiplierFromFluid;
        } else {
            totalFluidBoost += growthProbabilityBoostModifierFromFluid;
        }

        growthProbabilityThreshold += totalFluidBoost;

        // If this medium works especially well on this plant, we can give it a little
        // boost
        int growthProbabilityBoostModifier = HydroponicHelper.getSpecificPlantGrowthBoostForId(mediumId,
                aboveBlockState);

        growthProbabilityThreshold += growthProbabilityBoostModifier;

        // If impossible, skidaddle
        if (growthProbabilityThreshold == 0) {
            return false;
        }

        // If the above is one of those BlockCrops fellas or fancy IPlantable, roll the
        // dice
        if (aboveBlock instanceof BlockCrops || aboveBlock instanceof IPlantable) {
            return rollForGrow(aboveBlock, aboveBlockState, growthProbabilityThreshold);
        }

        return false;
    }

    public void doMediumDestroyProcess() {

        ItemStack growthMedium = this.getItemStack();

        if (StackHelper.isItemStackEmpty(growthMedium)) {
            return;
        }

        if (!EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growBedDestroyMedia) {
            return;
        }

        int destroyProbability = HydroponicHelper.getDestroyProbabilityForMedium(growthMedium);

        if (destroyProbability == 0) {
            return;
        }

        int random = new Random().nextInt(1001);

        if (random < destroyProbability) {
            this.itemHandler.extractItem(0, 1, false);
        }
    }

    public boolean rollForGrow(Block block, IBlockState blockState, int growthProbability) {
        // Grab yourself a fresh new random number from 0 to 100
        int random = new Random().nextInt(101);

        // If the shiny random number is smaller than the growth threshold...
        if (random < growthProbability) {
            // Winner winner chicken dinner
            block.updateTick(this.world, this.pos.add(0, 1, 0), blockState, new Random());
            return true;
        }

        // Zone of sadness
        return false;
    }

    public int getSpecificPlantGrowthBoostFromMedium() {
        // Get blockstate of whatever is on top of block
        IBlockState aboveBlockState = this.world.getBlockState(this.pos.add(0, 1, 0));

        // If there is no blockstate above, abandon ship
        if (aboveBlockState == null) {
            return 0;
        }

        return HydroponicHelper.getSpecificPlantGrowthBoostForId(this.getGrowthMediumId(), aboveBlockState);
    }

    public int getGrowthProbabilityForFluid() {
        return HydroponicHelper.getGrowthProbabilityForFluid(this.fluidHandler.getFluid());
    }

    public int getSpecificPlantGrowthBoostFromFluid() {
        // Get blockstate of whatever is on top of block
        IBlockState aboveBlockState = this.world.getBlockState(this.pos.add(0, 1, 0));

        // If there is no blockstate above, abandon ship
        if (aboveBlockState == null) {
            return 0;
        }

        return HydroponicHelper.getSpecificPlantGrowthBoostForFluidStack(this.getFluidStack(), aboveBlockState);
    }

    public int getTotalGrowthFromAdjacentLight() {

        for (int i = 1; i < EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightBlockRange; i++) {

            BlockPos pos = this.pos.add(0, i + 1, 0);

            Block aboveBlock = this.world.getBlockState(pos).getBlock();

            if (aboveBlock instanceof Light) {
                TileEntity tileEntity = this.world.getTileEntity(pos);
                if (tileEntity instanceof LightTileEntity) {
                    LightTileEntity lightTileEntity = (LightTileEntity) tileEntity;
                    int lightGrowthModifier = lightTileEntity.getGrowthProbabilityForBulb();
                    int lightBoostModifier = lightTileEntity.getSpecificPlantGrowthBoostForBulb();
                    return lightGrowthModifier + lightBoostModifier;
                }
            } else if (aboveBlock == Blocks.AIR || PlantHelper.isPlantBlock(aboveBlock)) {
                continue;
            } else {
                return 0;
            }
        }

        return 0;
    }

    public int getGrowthMediumId() {
        return HydroponicHelper.getGrowthMediaIdFromStack(this.getItemStack());
    }

    public ItemStack getItemStack() {
        return itemHandler.getStackInSlot(0);
    }

    // Getters
    public int getGrowthMediumIdForTexture() {
        int id = getGrowthMediumId();
        int max = ModMediumProvider.BASE_MEDIUM_COUNT;
        return id > max ? max + 1 : id;
    }

    public int getWater() {
        return this.water;
    }

    public int getEnergy() {
        return this.energy;
    }

    public Fluid getFluid() {
        if (this.fluidHandler.getFluid() != null) {
            return this.fluidHandler.getFluid().getFluid();
        } else {
            return null;
        }
    }

    public FluidStack getFluidStack() {
        return this.fluidHandler.getFluid();
    }

    public String getFluidName() {

        FluidStack fluid = this.getFluidStack();

        if (fluid == null) {
            return "No fluid";
        }

        if (fluid.getFluid() == null) {
            return "No fluid";
        }

        return fluid.getFluid().getName();
    }

    // Setters
    private void setGrowthMediumId(int id) {
        this.mediumId = id;
    }

    private void setWater(int quantity) {

        renderCheck(this.water, quantity);

        this.water = quantity;
    }

    private void setEnergy(int quantity) {
        this.energy = quantity;
    }

    public int getField(EnumTileField field) {
        switch (field) {
            case FLUID:
                return this.getWater();
            case MEDIUM:
                return this.getGrowthMediumId();
            default:
                return 0;
        }
    }

    public void setField(EnumTileField field, int value) {
        switch (field) {
            case FLUID:
                this.setWater(value);
                break;
            case MEDIUM:
                this.setGrowthMediumId(value);
                break;
            default:
                break;
        }
    }

    private void renderCheck(int oldValue, int newValue) {
        if (oldValue == 0 && newValue > 0) {
            this.markDirtyClient();
        }

        if (oldValue > 0 && newValue == 0) {
            this.markDirtyClient();
        }
    }

    // OpenComputers

    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_grow_bed";
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getWaterLevel(Context context, Arguments args) {
        int level = getWater();
        return new Object[] { level };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getMaxWaterLevel(Context context, Arguments args) {
        int level = Reference.HYDROPONIC_FLUID_CAPACITY;
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
    public Object[] getMaxEnergyLevel(Context context, Arguments args) {
        int level = Reference.HYDROPONIC_ENERGY_CAPACITY;
        return new Object[] { level };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getMediumName(Context context, Arguments args) {
        String name = getItemStack().getDisplayName();
        return new Object[] { name };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getMediumGrowthMultiplier(Context context, Arguments args) {
        int probability = HydroponicHelper.getGrowthProbabilityForMedium(getItemStack());
        return new Object[] { probability };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getLightLevel(Context context, Arguments args) {
        int level = PlantHelper.getPlantLightAtPosition(world, pos);
        return new Object[] { level };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getPlantGrowth(Context context, Arguments args) {
        int growth = PlantHelper.getPlantGrowthAtPosition(world, pos.add(0, 1, 0));
        return new Object[] { growth };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getPlantName(Context context, Arguments args) {
        String name = PlantHelper.getPlantNameAtPosition(world, pos);
        return new Object[] { name };
    }
}