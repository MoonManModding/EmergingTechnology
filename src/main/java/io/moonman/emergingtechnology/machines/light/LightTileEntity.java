package io.moonman.emergingtechnology.machines.light;

import java.util.Random;

import javax.annotation.Nullable;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.energy.ConsumerEnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.helpers.PlantHelper;
import io.moonman.emergingtechnology.helpers.machines.LightHelper;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.MachineTileBase;
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
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class LightTileEntity extends MachineTileBase implements ITickable {

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.LIGHT_ENERGY_CAPACITY) {
        @Override
        public void onContentsChanged() {
            markDirty();
            super.onContentsChanged();
        }
    };

    public ConsumerEnergyStorageHandler consumerEnergyHandler = new ConsumerEnergyStorageHandler(energyHandler);

    public ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
            super.onContentsChanged(slot);
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return LightHelper.isItemStackValidBulb(stack);
        }
    };

    private int energy = this.energyHandler.getEnergyStored();

    private int bulbTypeId = 0;

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
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.itemHandler);
        if (capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(this.consumerEnergyHandler);
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
    public void cycle() {
        this.energy = this.energyHandler.getEnergyStored();

        this.setBulbTypeId(getBulbTypeIdFromItemStack());

        doPowerUsageProcess(bulbTypeId);

        boolean hasPower = this.energyHandler.getEnergyStored() > 0;

        // If lamp has power, try to grow plants below
        if (hasPower) {
            doGrowthMultiplierProcess(this.bulbTypeId);
        }

        Light.setState(hasPower || this.isGlowstonePowered(), LightHelper.getBulbColourFromBulbId(bulbTypeId), world,
                pos);

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

        int transferEnergy = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growLightEnergyTransferRate;

        // If enough energy to transfer...
        if (this.energy >= transferEnergy) {

            // Get the direction this grow light is facing
            EnumFacing facing = this.world.getBlockState(this.pos).getValue(Light.FACING);

            // Grab the vector
            Vec3i vector = facing.getDirectionVec();

            // Get neighbour based on facing vector
            TileEntity neighbour = this.world.getTileEntity(this.pos.add(vector));

            // Is neighbour a grow light?
            if (neighbour instanceof LightTileEntity) {
                LightTileEntity targetTileEntity = (LightTileEntity) neighbour;

                // Send energy to the neighbour and get amount accepted
                int accepted = targetTileEntity.energyHandler.receiveEnergy(transferEnergy, false);

                if (accepted > 0) {
                    // Drain self from amount
                    this.energyHandler.extractEnergy(accepted, true);
                    this.setEnergy(this.energyHandler.getEnergyStored());
                }
            }
        }
    }

    public void doGrowthMultiplierProcess(int bulbTypeId) {
        // Get growth probability from bulb type
        int growthProbabilityThreshold = this.getGrowthProbabilityForBulb();

        // How many blocks below light?
        int blocksBelow = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightBlockRange;

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
                int growthProbabilityBoostModifier = LightHelper.getSpecificPlantGrowthBoostForId(bulbTypeId,
                        blockStateBelow);

                growthProbabilityThreshold += growthProbabilityBoostModifier;

                // If light dropoff set in config, calculate the penalty
                int dropoff = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightBlockRangeDropoff;
                int growthProbabilityDropoffPenalty = 0;

                if (dropoff > 0) {
                    growthProbabilityDropoffPenalty = dropoff * i;
                }
                ;

                growthProbabilityThreshold -= growthProbabilityDropoffPenalty;

                // If growth is impossible, exit
                if (growthProbabilityThreshold <= 0) {
                    break;
                }

                rollForGrow(blockBelow, blockStateBelow, position, growthProbabilityThreshold);

                break;
            } else if (blockBelow == Blocks.AIR) {
                continue;
            } else {
                break;
            }
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

    public int getGrowthProbabilityForBulb() {
        return LightHelper.getGrowthProbabilityForBulbById(this.getBulbTypeId());
    }

    public int getSpecificPlantGrowthBoostForBulb() {
        IBlockState plantBlockState = this.getFirstValidPlantBlockstate();

        if (plantBlockState == null) {
            return 0;
        }

        return LightHelper.getSpecificPlantGrowthBoostForId(this.getBulbTypeId(), plantBlockState);
    }

    private IBlockState getFirstValidPlantBlockstate() {
        int blocksBelow = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightBlockRange;

        for (int i = 1; i < blocksBelow + 1; i++) {

            BlockPos position = this.pos.add(0, -i, 0);

            IBlockState blockStateBelow = this.world.getBlockState(position);

            Block blockBelow = blockStateBelow.getBlock();

            if (PlantHelper.isPlantBlock(blockBelow)) {

                return blockStateBelow;
            } else if (blockBelow == Blocks.AIR) {
                continue;
            } else {
                break;
            }
        }

        return null;
    }

    private int getBulbTypeIdFromItemStack() {
        ItemStack stack = getItemStack();
        int id = LightHelper.getBulbTypeIdFromStack(stack);
        return id;
    }

    private void setBulbTypeId(int id) {
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
}