package io.moonman.emergingtechnology.tile.tiles;

import java.util.Random;

import io.moonman.emergingtechnology.block.blocks.Light;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.LightHelper;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.tile.handlers.EnergyStorageHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class LightTileEntity extends TileEntity implements ITickable {

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.LIGHT_ENERGY_CAPACITY);
    public ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
            super.onContentsChanged(slot);
        }
    };

    private boolean powered;
    private int bulbtype = 0;
    private int energy = this.energyHandler.getEnergyStored();

    private int tick = 0;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return true;
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;

        return false;
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
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.itemHandler.deserializeNBT(compound.getCompoundTag("Inventory"));

        this.energyHandler.readFromNBT(compound);
        this.energy = compound.getInteger("GuiEnergy");

        this.energyHandler.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Inventory", this.itemHandler.serializeNBT());

        compound.setInteger("GuiEnergy", energy);
        this.energyHandler.writeToNBT(compound);

        return compound;
    }

    @Override
    public void update() {

        if (world.isRemote) {
            return;
        }

        this.energy = this.energyHandler.getEnergyStored();

        if (tick < 10) {
            tick++;
            return;
        } else {

            int bulbTypeId = getBulbTypeIdFromItemStack();

            // Use power
            boolean hasPower = doPowerUsageProcess(bulbTypeId);

            // If lamp has power, try to grow plants below
            if (hasPower) {
                doGrowthMultiplierProcess(bulbTypeId);
            }

            Light.setState(hasPower, bulbTypeId, world, pos);

            tick = 0;
        }

    }

    public boolean doPowerUsageProcess(int bulbTypeId) {
        // Get modifier for current bulb
        int bulbEnergyModifier = LightHelper.getEnergyUsageModifierForBulbById(bulbTypeId);

        // Calculate energy required
        int energyRequired = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightEnergyBaseUsage * bulbEnergyModifier;

        if (this.energy >= energyRequired) {
            this.energyHandler.extractEnergy(energyRequired, false);
            return true;
        }
        
        return false;
    }

    public void doGrowthMultiplierProcess(int bulbTypeId) {
        
    }

    public boolean rollForGrow(Block block, IBlockState blockState, BlockPos pos, int growthProbability) {
        // Grab yourself a fresh new random number from 0 to 100
        int random = new Random().nextInt(101);

        // If the shiny random number is smaller than the growth threshold...
        if (random < growthProbability) {
            // Winner winner chicken dinner
            block.updateTick(this.world, pos, blockState, new Random());
            return true;
        }

        // Zone of sadness
        return false;
    }

    public int getBulbTypeIdFromItemStack() {
        ItemStack stack = getItemStack();
        int id = LightHelper.getBulbTypeIdFromStack(stack);
        return id;
    }

    public ItemStack getItemStack() {
        return itemHandler.getStackInSlot(0);
    }

    public int getEnergy() {
        return this.energy;
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

    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) != this ? false
                : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
                        (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }
}