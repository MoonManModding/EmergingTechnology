package io.moonman.emergingtechnology.machines.harvester;

import java.util.List;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.AutomationItemStackHandler;
import io.moonman.emergingtechnology.handlers.energy.ConsumerEnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.helpers.PlantHelper;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.animation.HarvesterAnimationStateMachine;
import io.moonman.emergingtechnology.helpers.machines.HarvesterHelper;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.tile.AnimatedMachineTileBase;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import io.moonman.emergingtechnology.network.PacketHandler;
import io.moonman.emergingtechnology.network.animation.HarvesterStartAnimationPacket;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class HarvesterTileEntity extends AnimatedMachineTileBase implements SimpleComponent {

    public static final GameProfile HARVESTER_PROFILE = new GameProfile(
            UUID.fromString("36f373ac-29ef-4150-b654-e7e6006efcd8"), "[Harvester]");
    private static FakePlayer HARVESTER_PLAYER = null;

    public HarvesterTileEntity() {
        initialiseHarvesterAnimator(this);
        setMaxTick(10);
    }

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.HARVESTER_ENERGY_CAPACITY) {
        @Override
        public void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }
    };

    public ConsumerEnergyStorageHandler consumerEnergyHandler = new ConsumerEnergyStorageHandler(energyHandler);

    public ItemStackHandler itemHandler = new ItemStackHandler(4) {
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

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot == 0) {
                return ItemStack.EMPTY;
            }

            return itemHandler.extractItem(slot, amount, simulate);
        }
    };

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    private int energy = this.energyHandler.getEnergyStored();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        if (capability == CapabilityEnergy.ENERGY)
            return true;
        if (capability == CapabilityAnimation.ANIMATION_CAPABILITY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.automationItemHandler);
        if (capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(this.consumerEnergyHandler);
        if (capability == CapabilityAnimation.ANIMATION_CAPABILITY)
            return CapabilityAnimation.ANIMATION_CAPABILITY.cast(getAnimator());
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.itemHandler.deserializeNBT(compound.getCompoundTag("Inventory"));

        this.setEnergy(compound.getInteger("GuiEnergy"));
        // this.setRotation(compound.getInteger("AnimRotation"));

        this.energyHandler.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Inventory", this.itemHandler.serializeNBT());

        compound.setInteger("GuiEnergy", energy);
        // compound.setInteger("AnimRotation", RotationEnum.getId(this.rotation));

        this.energyHandler.writeToNBT(compound);

        return compound;
    }



    @Override
    public void cycle() {

        this.setEnergy(this.getEnergy());

        for (EnumFacing facing : EnumFacing.HORIZONTALS) {

            tryPlant(facing);

            if (canHarvest(facing)) {
                Harvester.rotateFacing(facing, world, pos);
                animateHarvest();
            }
        }

        doEnergyTransferProcess();
    }

    public boolean canHarvest(EnumFacing facing) {
        if (getTargetBlockState(facing) == null) {
        }

        if (!sufficientEnergy()) {
            return false;
        }

        if (outputsFull()) {
            return false;
        }

        if (HarvesterHelper.isInteractableCrop(getTargetBlockState(facing).getBlock())) {
            return HarvesterHelper.isInteractableCropReadyForHarvest(getTargetBlockState(facing), getWorld(),
                    getTarget(facing));
        }

        return PlantHelper.isCropAtPositionReadyForHarvest(getWorld(), getTarget(facing));
    }

    public void doHarvest() {

        EnumFacing facing = getFacing();

        if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
            return;
        }

        if (!canHarvest(facing)) {
            return;
        }

        if (getTargetBlockState(facing) == null) {
            return;
        }

        if (!sufficientEnergy()) {
            return;
        }

        if (HarvesterHelper.isInteractableCrop(getTargetBlockState(facing).getBlock())) {
            doInteractableHarvest(facing);
        } else {
            world.destroyBlock(getTarget(facing), true);
        }

        pullItems(facing);

        useEnergy();
    }

    private void doInteractableHarvest(EnumFacing facing) {
        getTargetBlockState(facing).getBlock().onBlockActivated(world, getTarget(facing), getTargetBlockState(facing),
                getFakePlayer(), EnumHand.MAIN_HAND, getFacing(), 0, 0, 0);
    }

    private boolean pullItems(EnumFacing facing) {

        if (getTargetBlockState(facing) == null) {
            return false;
        }

        // Includes target and directly above target
        AxisAlignedBB collectionArea = new AxisAlignedBB(getTarget(facing)).expand(0, 1, 0);

        List<EntityItem> entityItems = world.getEntitiesWithinAABB(EntityItem.class, collectionArea);
        insertItems(entityItems);

        return true;
    }

    private void insertItems(List<EntityItem> entityItems) {
        for (EntityItem entity : entityItems) {

            if (entity == null) {
                continue;
            } else {
                ItemStack itemStack = entity.getItem().copy();

                // Try insert seeds into seed slot
                if (PlantHelper.isSeedItem(itemStack.getItem()) && !inputFull() && (this.getInputStack().isEmpty()
                        || StackHelper.compareItemStacks(itemStack, getInputStack()))) {

                    ItemStack inserted = this.itemHandler.insertItem(0, itemStack, false);
                    handleEntity(entity, inserted);
                } else {
                    // Otherwise try insert to output
                    ItemStack inserted = this.tryInsertItemStackIntoOutput(itemStack);
                    handleEntity(entity, inserted);
                }
            }
        }
    }

    private ItemStack tryInsertItemStackIntoOutput(ItemStack itemStack) {

        if (this.outputsFull()) {
            return itemStack;
        }

        for (int i = 1; i < this.itemHandler.getSlots(); i++) {
            ItemStack outputStack = this.itemHandler.getStackInSlot(i);

            if (outputStack.getCount() == outputStack.getMaxStackSize()) {
                continue;
            }

            if (!outputStack.isEmpty() && !StackHelper.compareItemStacks(itemStack, outputStack)) {
                continue;
            }

            ItemStack remainder = this.itemHandler.insertItem(i, itemStack, false);

            return remainder;
        }

        return itemStack;
    }

    private void handleEntity(EntityItem entity, ItemStack itemStack) {
        if (StackHelper.isItemStackEmpty(itemStack)) {
            entity.setDead();
        } else {
            entity.setItem(itemStack);
        }
    }

    private void tryPlant(EnumFacing facing) {

        if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
            return;
        }

        // If non-break-harvest plant, return
        if (HarvesterHelper.isInteractableCrop(getTargetBlockState(facing).getBlock())) {
            return;
        }

        ItemStack inputStack = getInputStack();

        // If no seeds to plant, return
        if (StackHelper.isItemStackEmpty(inputStack)) {
            return;
        }

        // Probably not neccessary
        if (getTargetBlockState(facing) == null) {
            return;
        }

        // If crop space is occupied, return
        if (getTargetBlockState(facing).getBlock() != Blocks.AIR) {
            return;
        }

        BlockPos soilTarget = getTarget(facing).add(0, -1, 0);

        if (!PlantHelper.isValidSoil(getWorld(), soilTarget)) {
            return;
        }
    
        IBlockState blockStateToPlace = PlantHelper.getBlockStateFromItemStackForPlanting(inputStack, getWorld(),
                getTarget(facing));

        // No crop block associated with this item, return
        if (blockStateToPlace == null) {
            return;
        }

        world.setBlockState(getTarget(facing), blockStateToPlace, 3);

        this.itemHandler.extractItem(0, 1, false);
    }

    public void doEnergyTransferProcess() {
        int transferEnergy = EmergingTechnologyConfig.HYDROPONICS_MODULE.HARVESTER.harvesterEnergyTransferRate;

        for (EnumFacing facing : EnumFacing.HORIZONTALS) {

            if (this.getEnergy() < transferEnergy) {
                break;
            }

            TileEntity neighbour = this.world.getTileEntity(this.pos.offset(facing));

            if (neighbour instanceof HarvesterTileEntity) {
                HarvesterTileEntity targetTileEntity = (HarvesterTileEntity) neighbour;

                int accepted = targetTileEntity.energyHandler.receiveEnergy(transferEnergy, false);

                if (accepted > 0) {
                    this.energyHandler.extractEnergy(accepted, true);
                    this.setEnergy(this.energyHandler.getEnergyStored());
                }
            }

        }
    }

    private boolean inputFull() {
        return getInputStack().getCount() == this.getInputStack().getMaxStackSize();
    }

    private boolean outputsFull() {

        ItemStack[] outputStacks = this.getOutputStacks();

        boolean isFull = true;

        for (ItemStack itemStack : outputStacks) {
            if (itemStack.getCount() < itemStack.getMaxStackSize()) {
                isFull = false;
            }
        }

        return isFull;
    }

    private boolean sufficientEnergy() {
        return getEnergy() >= EmergingTechnologyConfig.HYDROPONICS_MODULE.HARVESTER.harvesterEnergyBaseUsage;
    }

    private int useEnergy() {
        return energyHandler
                .extractEnergy(EmergingTechnologyConfig.HYDROPONICS_MODULE.HARVESTER.harvesterEnergyBaseUsage, false);
    }

    private BlockPos getTarget(EnumFacing facing) {
        return getPos().offset(facing);
    }

    private IBlockState getTargetBlockState(EnumFacing facing) {
        return getWorld().getBlockState(getTarget(facing));
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

    public ItemStack[] getOutputStacks() {
        ItemStack[] stacks = new ItemStack[this.itemHandler.getSlots() - 1];

        for (int i = 1; i < this.itemHandler.getSlots(); i++) {
            stacks[i - 1] = this.itemHandler.getStackInSlot(i);
        }

        return stacks;
    }

    private FakePlayer getFakePlayer() {
        if (HARVESTER_PLAYER == null) {
            int dimension = getWorld().provider.getDimension();
            HARVESTER_PLAYER = new FakePlayer(getWorld().getMinecraftServer().getWorld(dimension), HARVESTER_PROFILE);
        }

        return HARVESTER_PLAYER;
    }

    @SideOnly(Side.CLIENT)
    public void animateHarvestClient() {

        if (!isClient()) {
            return;
        }

        getHarvesterAnimator().harvesterTransition(getPos());
    }

    private void animateHarvest() {

        TargetPoint targetPoint = getTargetPoint();

        if (targetPoint == null)
            return;

        PacketHandler.INSTANCE.sendToAllTracking(
                new HarvesterStartAnimationPacket(this.getPos()), targetPoint);
    }

    private HarvesterAnimationStateMachine getHarvesterAnimator() {
        return (HarvesterAnimationStateMachine) this.getAnimator();
    }

    // Getters

    public int getEnergy() {
        return this.energyHandler.getEnergyStored();
    }

    // Setters

    private void setEnergy(int quantity) {
        this.energy = quantity;
    }

    public int getField(EnumTileField field) {
        switch (field) {
            case ENERGY:
                return this.getEnergy();
            default:
                return 0;
        }
    }

    public void setField(EnumTileField field, int value) {
        switch (field) {
            case ENERGY:
                this.setEnergy(value);
                break;
            default:
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