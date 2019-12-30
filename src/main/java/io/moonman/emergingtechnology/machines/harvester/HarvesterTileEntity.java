package io.moonman.emergingtechnology.machines.harvester;

import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.AutomationItemStackHandler;
import io.moonman.emergingtechnology.handlers.EnergyStorageHandler;
import io.moonman.emergingtechnology.helpers.PlantHelper;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.MachineTileBase;
import io.moonman.emergingtechnology.machines.hydroponic.Hydroponic;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
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
public class HarvesterTileEntity extends MachineTileBase implements ITickable, SimpleComponent {

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.HARVESTER_ENERGY_CAPACITY) {
        @Override
        public void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }
    };

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

    private int tick = 0;

    private int energy = this.energyHandler.getEnergyStored();

    private boolean isActive = false;
    private boolean requiresUpdate = false;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        if (capability == CapabilityEnergy.ENERGY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) this.automationItemHandler;
        if (capability == CapabilityEnergy.ENERGY)
            return (T) this.energyHandler;
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.itemHandler.deserializeNBT(compound.getCompoundTag("Inventory"));

        this.setEnergy(compound.getInteger("GuiEnergy"));

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

        if (this.isClient()) {
            return;
        }

        if (tick < 10) {
            tick++;
            return;
        } else {

            this.setEnergy(this.getEnergy());

            if (canHarvest()) {
                this.setIsActive(true);
                this.doHarvest();
            } else {
                this.setIsActive(false);
            }

            this.tryPlant();

            this.pullItems();

            if (this.requiresUpdate
                    && !EmergingTechnologyConfig.HYDROPONICS_MODULE.HARVESTER.harvesterDisableAnimations) {
                Harvester.setState(this.isActive, getWorld(), getPos());
                this.requiresUpdate = false;
            }

            tick = 0;
        }
    }

    public boolean canHarvest() {
        if (getTargetBlockState() == null) {
            return false;
        }

        if (!sufficientEnergy()) {
            return false;
        }

        if (outputFull()) {
            return false;
        }

        return PlantHelper.isCropAtPositionReadyForHarvest(getWorld(), getTarget());
    }

    public void doHarvest() {
        if (getTargetBlockState() == null) {
            return;
        }

        if (!sufficientEnergy()) {
            return;
        }

        world.destroyBlock(getTarget(), true);
        pullItems();

        useEnergy();
    }

    private boolean pullItems() {

        if (getTargetBlockState() == null)
            return false;

        List<EntityItem> entityItems = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(getTarget()));
        insertItems(entityItems);

        return true;
    }

    private void insertItems(List<EntityItem> entityItems) {
        for (EntityItem entity : entityItems) {

            if (entity == null) {
                continue;
            } else {
                ItemStack itemStack = entity.getItem().copy();

                int slot = PlantHelper.isSeedItem(itemStack.getItem()) ? 0 : 1;

                // If seed slot is full..
                if (slot == 0 && inputFull()) {
                    // Try to place in output slot
                    if (!outputFull()) {
                        slot = 1;
                    } else {
                        // Otherwise leave item alone
                        continue;
                    }
                }

                // If output slot is full, leave item alone
                if (slot == 1 && outputFull()) {
                    continue;
                }

                ItemStack itemStack1 = this.itemHandler.insertItem(slot, itemStack, false);

                if (itemStack1.isEmpty()) {
                    entity.setDead();
                } else {
                    entity.setItem(itemStack1);
                }

                continue;
            }
        }
    }

    private void tryPlant() {
        ItemStack inputStack = getInputStack();

        // If no seeds to plant, return
        if (StackHelper.isItemStackEmpty(inputStack)) {
            return;
        }

        // Probably not neccessary
        if (getTargetBlockState() == null) {
            return;
        }

        // If crop space is occupied, return
        if (getTargetBlockState().getBlock() != Blocks.AIR) {
            return;
        }

        BlockPos soilTarget = getTarget().add(0, -1, 0);
        IBlockState soilBlockTarget = getWorld().getBlockState(soilTarget);

        if (soilBlockTarget.getBlock() instanceof Hydroponic == false) {
            return;
        }

        IBlockState blockStateToPlace = PlantHelper.getBlockStateFromItemStackForPlanting(inputStack, getWorld(),
                getTarget());

        // No crop block associated with this item, return
        if (blockStateToPlace == null) {
            return;
        }

        world.setBlockState(getTarget(), blockStateToPlace, 3);
        getInputStack().shrink(1);
    }

    private boolean inputFull() {
        return getInputStack().getCount() == this.getInputStack().getMaxStackSize();
    }

    private boolean outputFull() {
        return getOutputStack().getCount() == this.getOutputStack().getMaxStackSize();
    }

    private boolean sufficientEnergy() {
        return getEnergy() >= EmergingTechnologyConfig.HYDROPONICS_MODULE.HARVESTER.harvesterEnergyBaseUsage;
    }

    private int useEnergy() {
        return energyHandler
                .extractEnergy(EmergingTechnologyConfig.HYDROPONICS_MODULE.HARVESTER.harvesterEnergyBaseUsage, false);
    }

    private BlockPos getTarget() {
        return getPos().offset(getFacing());
    }

    private IBlockState getTargetBlockState() {
        return getWorld().getBlockState(getTarget());
    }

    private EnumFacing getFacing() {
        IBlockState blockState = getWorld().getBlockState(getPos());

        if (blockState.getBlock() instanceof Harvester == false) {
            return EnumFacing.NORTH;
        }

        return blockState.getValue(Harvester.FACING);
    }

    public ItemStack getInputStack() {
        return itemHandler.getStackInSlot(0);
    }

    public ItemStack getOutputStack() {
        return itemHandler.getStackInSlot(1);
    }

    // Getters

    public int getEnergy() {
        return this.energyHandler.getEnergyStored();
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    // Setters

    public void setIsActive(boolean active) {
        if (active != this.isActive) {
            this.requiresUpdate = true;
        }

        this.isActive = active;
    }

    private void setEnergy(int quantity) {
        this.energy = quantity;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return !(oldState.getBlock() instanceof Harvester && newState.getBlock() instanceof Harvester);
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
        default:
            return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id) {
        case 0:
            this.setEnergy(value);
            break;
        }
    }

    // OpenComputers

    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_harvester";
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getEnergyLevel(Context context, Arguments args) {
        int level = getEnergy();
        return new Object[] { level };
    }
}