package io.moonman.emergingtechnology.tile.tiles;

import java.util.Random;

import javax.annotation.Nullable;

import io.moonman.emergingtechnology.block.blocks.Hydroponic;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.HydroponicHelper;
import io.moonman.emergingtechnology.helpers.PlantHelper;
import io.moonman.emergingtechnology.helpers.custom.loaders.CustomGrowthMediumLoader;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.tile.handlers.EnergyStorageHandler;
import io.moonman.emergingtechnology.tile.handlers.FluidStorageHandler;
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
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
// import li.cil.oc.api.machine.Arguments;
// import li.cil.oc.api.machine.Callback;
// import li.cil.oc.api.machine.Context;
// import li.cil.oc.api.network.SimpleComponent;

// @Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class HydroponicTileEntity extends TileEntity implements ITickable {

    public FluidTank fluidHandler = new FluidStorageHandler(Reference.HYDROPONIC_FLUID_CAPACITY);

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.HYDROPONIC_ENERGY_CAPACITY);

    public ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
            super.onContentsChanged(slot);
        }
    };

    private int tick = 0;

    private int water = 0;
    private int energy = this.energyHandler.getEnergyStored();
    private int mediumId = 0;

    private boolean waterChanged = false;
    private boolean energyChanged = false;
    private boolean mediumChanged = false;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return true;
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        if (capability == CapabilityEnergy.ENERGY)
            return true;

        return false;
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

        // this.hasWater = compound.getBoolean("HasWater");
        this.water = compound.getInteger("GuiWater");
        this.energy = compound.getInteger("GuiEnergy");

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
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
        this.sendUpdates(true);
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

            // Set water, energy and medium from handlers
            this.setWater(this.fluidHandler.getFluidAmount());
            this.setEnergy(this.energyHandler.getEnergyStored());
            this.setGrowthMediumId(this.getGrowthMediumIdFromItemStack());

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

            Hydroponic.setState(this.water > 0, getGrowthMediumIdForTexture(), world, pos);

            this.sendUpdates(false);

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
                            new FluidStack(FluidRegistry.WATER,
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

        // // Set has water
        // this.hasWater = this.water > 0;
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

    public int getGrowthMediumId() {
        return this.mediumId;
    }

    public int getGrowthMediumIdFromItemStack() {
        int id = HydroponicHelper.getGrowthMediaIdFromStack(this.getItemStack());
        return id;
    }

    public ItemStack getItemStack() {
        return itemHandler.getStackInSlot(0);
    }

    public int getGrowthMediumIdForTexture() {
        int id = getGrowthMediumIdFromItemStack();
        return id >= CustomGrowthMediumLoader.STARTING_ID ? 5 : id;
    }

    private void setGrowthMediumId(int id) {
        this.mediumChanged = this.mediumId != id;
        this.mediumId = id;
    }

    public int getWater() {
        return this.water;
    }

    private void setWater(int quantity) {
        this.waterChanged = this.water != quantity;

        this.water = quantity;
    }

    public int getEnergy() {
        return this.energy;
    }

    private void setEnergy(int quantity) {
        this.energyChanged = this.energy != quantity;

        this.energy = quantity;
    }

    public int getField(int id) {
        switch (id) {
        case 0:
            return this.water;
        case 1:
            return this.energy;
        default:
            return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id) {
        case 0:
            this.water = value;
        case 1:
            this.energy = value;
            break;
        }
    }

    private void sendUpdates(boolean forceUpdates) {
        if (this.waterChanged || this.energyChanged || this.mediumChanged || forceUpdates) {
            world.markBlockRangeForRenderUpdate(pos, pos);
            world.notifyBlockUpdate(pos, getState(), getState(), 3);
            world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
            markDirty();
        }
    }

    private IBlockState getState() {
        return world.getBlockState(pos);
    }

    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) != this ? false
                : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
                        (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    // OpenComputers

    // @Override
    // @Optional.Method(modid = "opencomputers")
    // public String getComponentName() {
    // return EmergingTechnology.MODID + "_hydroponic";
    // }

    // @Callback
    // @Optional.Method(modid = "opencomputers")
    // public Object[] getWaterLevel(Context context, Arguments args) {
    // return new Object[] {getWater()};
    // }

    // @Callback
    // @Optional.Method(modid = "opencomputers")
    // public Object[] getEnergyLevel(Context context, Arguments args) {
    // return new Object[] {getEnergyLevel()};
    // }
}