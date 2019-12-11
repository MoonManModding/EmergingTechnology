package io.moonman.emergingtechnology.machines.light;

import java.util.Random;

import javax.annotation.Nullable;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.EnergyStorageHandler;
import io.moonman.emergingtechnology.helpers.PlantHelper;
import io.moonman.emergingtechnology.helpers.machines.LightHelper;
import io.moonman.emergingtechnology.init.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
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
public class LightTileEntity extends TileEntity implements ITickable, SimpleComponent {

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.LIGHT_ENERGY_CAPACITY) {
        @Override
        public void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }
    };
    public ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
            super.onContentsChanged(slot);
        }
    };

    private int energy = this.energyHandler.getEnergyStored();

    private int bulbTypeId = 0;
    private boolean bulbChanged = false;

    private int tick = 0;

    public void markDirtyClient() {
        markDirty();
        if (world != null) {
            IBlockState state = world.getBlockState(getPos());
            world.notifyBlockUpdate(getPos(), state, state, 3);
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return true;
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) this.itemHandler;
        if (capability == CapabilityEnergy.ENERGY)
            return (T) this.energyHandler;
        return super.getCapability(capability, facing);
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
        this.sendUpdates(false);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.itemHandler.deserializeNBT(compound.getCompoundTag("Inventory"));

        this.setBulbTypeId(compound.getInteger("BulbTypeId"));

        this.energyHandler.readFromNBT(compound);

        this.setEnergy(compound.getInteger("GuiEnergy"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Inventory", this.itemHandler.serializeNBT());

        compound.setInteger("BulbTypeId", this.getBulbTypeId());

        compound.setInteger("GuiEnergy", this.getEnergy());
        this.energyHandler.writeToNBT(compound);

        return compound;
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

            this.energy = this.energyHandler.getEnergyStored();

            this.setBulbTypeId(getBulbTypeIdFromItemStack());

            // Use power
            doPowerUsageProcess(bulbTypeId);

            boolean hasPower = this.energyHandler.getEnergyStored() > 0;

            // If lamp has power, try to grow plants below
            if (hasPower) {
                doGrowthMultiplierProcess(this.bulbTypeId);
            }

            this.sendUpdates(false);

            tick = 0;
        }

    }

    public void doPowerUsageProcess(int bulbTypeId) {

        // Get modifier for current bulb
        int bulbEnergyModifier = LightHelper.getEnergyUsageModifierForBulbById(bulbTypeId);

        // Calculate energy required
        int energyRequired = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightEnergyBaseUsage
                * bulbEnergyModifier;

        if (this.energy >= energyRequired) {
            // If enough energy, extract it
            this.energyHandler.extractEnergy(energyRequired, false);
        } else {
            // Otherwise empty all the power from the light.
            this.energyHandler.extractEnergy(this.energy, false);
        }

        this.setEnergy(this.energyHandler.getEnergyStored());
    }

    public void doGrowthMultiplierProcess(int bulbTypeId) {
        // Get growth probability from bulb type
        int growthProbabilityThreshold = LightHelper.getGrowthProbabilityForBulbById(bulbTypeId);

        // How many blocks below light?
        int blocksBelow = 2;

        for (int i = 1; i < blocksBelow + 1; i++) {
            // Get position
            BlockPos position = this.pos.add(0, -i, 0);

            // Get block state
            IBlockState blockStateBelow = this.world.getBlockState(position);

            // Get block
            Block blockBelow = blockStateBelow.getBlock();

            // If it's not a plant, and it's not air...
            if (PlantHelper.isPlantBlock(blockBelow)) {

                // If this medium works especially well on this plant, we can give it a little
                // boost
                int growthProbabilityBoostModifier = LightHelper.getSpecificPlantGrowthBoost(bulbTypeId,
                        blockStateBelow);

                growthProbabilityThreshold += growthProbabilityBoostModifier;

                rollForGrow(blockBelow, blockStateBelow, position, growthProbabilityThreshold);
                break;
            } else if (blockBelow == Blocks.AIR) {
                continue;
            } else {
                break;
            }

            // Let's roll

        }

        return;
    }

    public boolean rollForGrow(Block block, IBlockState blockState, BlockPos pos, int growthProbability) {
        // Grab yourself a fresh new random number from 0 to 100
        int random = new Random().nextInt(101);

        // If the shiny random number is smaller than the growth threshold then we roll
        if (random < growthProbability) {

            // This is probably not necessary but then neither are male nipples
            if (block == Blocks.AIR) {
                return false;
            } else {
                // Winner winner chicken dinner
                block.updateTick(this.world, pos, blockState, new Random());
                return true;
            }
        }

        // Zone of sadness
        return false;
    }

    public boolean isGlowstonePowered() {
        ItemStack stack = getItemStack();
        return LightHelper.isGlowstonePowered(stack);
    }

    private int getBulbTypeIdFromItemStack() {
        ItemStack stack = getItemStack();
        int id = LightHelper.getBulbTypeIdFromStack(stack);
        return id;
    }

    private void setBulbTypeId(int id) {

        this.bulbChanged = this.bulbTypeId != id;

        this.bulbTypeId = id;
    }

    public int getBulbTypeId() {
        return this.bulbTypeId;
    }

    private void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return this.energy;
    }

    public ItemStack getItemStack() {
        return itemHandler.getStackInSlot(0);
    }

    public int getField(int id) {
        switch (id) {
        case 0:
            return this.energy;
        default:
            return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id) {
        case 0:
            this.energy = value;
        }
    }

    private void sendUpdates(boolean forceUpdates) {
        if (this.bulbChanged || forceUpdates) {
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

    @Override
    public String getComponentName() {
        return "etech_grow_light";
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getEnergyLevel(Context context, Arguments args) {
        return new Object[] { getEnergy() };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getMaxEnergyLevel(Context context, Arguments args) {
        return new Object[] { Reference.LIGHT_ENERGY_CAPACITY };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getBulbName(Context context, Arguments args) {
        return new Object[] { getItemStack().getDisplayName() };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getBulbGrowthMultiplier(Context context, Arguments args) {
        return new Object[] { LightHelper.getGrowthProbabilityForBulbById(getBulbTypeId()) };
    }
}