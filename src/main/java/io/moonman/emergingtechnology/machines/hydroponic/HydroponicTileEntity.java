package io.moonman.emergingtechnology.machines.hydroponic;

import java.util.Random;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.EnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.FluidStorageHandler;
import io.moonman.emergingtechnology.helpers.PlantHelper;
import io.moonman.emergingtechnology.helpers.custom.loaders.CustomGrowthMediumLoader;
import io.moonman.emergingtechnology.helpers.machines.HydroponicHelper;
import io.moonman.emergingtechnology.init.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
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
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidRegistry;
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
public class HydroponicTileEntity extends TileEntity implements ITickable, SimpleComponent {

    public FluidTank fluidHandler = new FluidStorageHandler(Reference.HYDROPONIC_FLUID_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return true; // HydroponicHelper.isFluidValidByName(fluid.getFluid().getName());
        }
    };

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.HYDROPONIC_ENERGY_CAPACITY) {
        @Override
        public void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }
    };

    public ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            markDirty();

        }
    };

    public void markDirtyClient() {
        markDirty();
        if (world != null) {
            IBlockState state = world.getBlockState(getPos());
            world.notifyBlockUpdate(getPos(), state, state, 3);
        }
    }

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
            return (T) this.fluidHandler;
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) this.itemHandler;
        if (capability == CapabilityEnergy.ENERGY)
            return (T) this.energyHandler;
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
        compound.setInteger("EnergyWater", energy);

        this.fluidHandler.writeToNBT(compound);

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

        if (world.isRemote) {
            return;
        }

        if (tick < 10) {
            tick++;
            return;
        } else {

            this.setWater(this.fluidHandler.getFluidAmount());
            this.setEnergy(this.energyHandler.getEnergyStored());
            this.setGrowthMediumId(this.getGrowthMediumId());

            // Do all the plant growth work and let us know how it went
            boolean growSucceeded = doGrowthMultiplierProcess();

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

            tick = 0;
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

        int plantWaterUse = growSucceeded ? 2 : 1;

        // Drain water
        this.fluidHandler.drain(
                EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growBedWaterUsePerCycle * plantWaterUse, true);

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

                // Check if neighbour is facing toward this grow bed to avoid infinite loop
                EnumFacing neighbourFacing = this.world.getBlockState(targetTileEntity.getPos())
                        .getValue(Hydroponic.FACING);

                if (facing != neighbourFacing.getOpposite()) {

                    // Fill the neighbour and get amount filled
                    int filled = targetTileEntity.fluidHandler.fill(
                            new FluidStack(this.fluidHandler.getFluid().getFluid(),
                                    EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growBedWaterTransferRate),
                            true);

                    if (filled > 0) {
                        // Drain self from amount
                        this.fluidHandler.drain(filled, true);
                        this.setWater(this.fluidHandler.getFluidAmount());
                    }
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

        // Get growth medium validity
        boolean growthMediumIsValid = HydroponicHelper.isItemStackValidGrowthMedia(growthMedium);

        // If growth medium is invalid, get outta there
        if (!growthMediumIsValid) {
            return false;
        }

        // Get threshold from medium
        int growthProbabilityThreshold = HydroponicHelper.getGrowthProbabilityForMedium(growthMedium);

        // If impossible, skidaddle
        if (growthProbabilityThreshold == 0) {
            return false;
        }

        // If it ain't a plant, we ain't interested
        if (!PlantHelper.isPlantBlock(aboveBlock)) {
            return false;
        }

        // If this medium works especially well on this plant, we can give it a little
        // boost
        int growthProbabilityBoostModifier = HydroponicHelper.getSpecificPlantGrowthBoost(mediumId, aboveBlockState);

        growthProbabilityThreshold += growthProbabilityBoostModifier;

        // If the above is one of those BlockCrops fellas or fancy IPlantable, roll the
        // dice
        if (aboveBlock instanceof BlockCrops || aboveBlock instanceof IPlantable) {
            return rollForGrow(aboveBlock, aboveBlockState, growthProbabilityThreshold);
        }

        return false;
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

    public int getCurrentPlantGrowthBoost() {
        // Get blockstate of whatever is on top of block
        IBlockState aboveBlockState = this.world.getBlockState(this.pos.add(0, 1, 0));

        // If there is no blockstate above, abandon ship
        if (aboveBlockState == null) {
            return 0;
        }

        return HydroponicHelper.getSpecificPlantGrowthBoost(this.getGrowthMediumId(), aboveBlockState);
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
        return id >= CustomGrowthMediumLoader.STARTING_ID ? 5 : id;
    }

    public int getWater() {
        return this.water;
    }

    public int getEnergy() {
        return this.energy;
    }

    // Setters
    private void setGrowthMediumId(int id) {
        this.mediumId = id;
    }

    private void setWater(int quantity) {
        this.water = quantity;
    }

    private void setEnergy(int quantity) {
        this.energy = quantity;
    }

    public int getField(int id) {
        switch (id) {
        case 0:
            return this.getWater();
        case 1:
            return this.getGrowthMediumId();
        default:
            return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id) {
        case 0:
            this.setWater(value);
        case 1:
            this.setGrowthMediumId(value);
            break;
        }
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
        int growth = PlantHelper.getPlantGrowthAtPosition(world, pos);
        return new Object[] { growth };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getPlantName(Context context, Arguments args) {
        String name = PlantHelper.getPlantNameAtPosition(world, pos);
        return new Object[] { name };
    }
}