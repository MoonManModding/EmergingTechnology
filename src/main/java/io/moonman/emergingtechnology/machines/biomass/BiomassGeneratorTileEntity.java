package io.moonman.emergingtechnology.machines.biomass;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.AutomationItemStackHandler;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.GeneratorEnergyStorageHandler;
import io.moonman.emergingtechnology.helpers.EnergyNetworkHelper;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.machines.BiomassHelper;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.MachineTileBase;
import io.moonman.emergingtechnology.recipes.machines.BiomassRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import li.cil.oc.api.network.SimpleComponent;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class BiomassGeneratorTileEntity extends MachineTileBase implements SimpleComponent {

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.BIOMASS_ENERGY_CAPACITY) {
        @Override
        public void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }
    };
    
    public GeneratorEnergyStorageHandler generatorEnergyHandler = new GeneratorEnergyStorageHandler(energyHandler) {

    };

    public ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            markDirty();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack itemStack) {
            return BiomassRecipes.isValidInput(itemStack);
        }
    };

    public ItemStackHandler automationItemHandler = new AutomationItemStackHandler(itemHandler, 0, 1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            markDirty();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack itemStack) {
            return BiomassRecipes.isValidInput(itemStack);
        }
    };

    private int energy = 0;
    private int progress = 0;

    @Override
    public boolean isEnergyGeneratorTile() {
        return true;
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
        if (capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(this.generatorEnergyHandler);
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.automationItemHandler);
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.itemHandler.deserializeNBT(compound.getCompoundTag("Inventory"));

        this.setEnergy(compound.getInteger("GuiEnergy"));
        this.setProgress(compound.getInteger("GuiProgress"));

        this.energyHandler.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Inventory", this.itemHandler.serializeNBT());

        compound.setInteger("GuiEnergy", this.getEnergy());
        compound.setInteger("GuiProgress", this.getProgress());

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
    public void cycle() {
        generate();
        spreadEnergy();
    }

    public void generate() {

        int energy = EmergingTechnologyConfig.ELECTRICS_MODULE.BIOMASSGENERATOR.biomassEnergyGenerated;

        // Generator full - stop processing
        if (getEnergy() + energy > Reference.BIOMASS_ENERGY_CAPACITY) {
            return;
        }

        ItemStack inputStack = getInputStack();

        if (this.getProgress() == 0) {
            if (inputStack.getCount() == 0) {
                return;
            }

            if (!BiomassRecipes.isValidInput(inputStack)) {
                return;
            }

            itemHandler.extractItem(0, 1, false);
        }

        ItemStack outputStack = getOutputStack();
        ItemStack plannedStack = BiomassRecipes.getOutputByItemStack(inputStack);

        // This is probably unneccessary
        if (plannedStack == null) {
            return;
        }

        // Output stack is full
        if (outputStack.getCount() == 64) {
            return;
        }

        // Output stack incompatible/non-empty
        if (!StackHelper.compareItemStacks(outputStack, plannedStack) && !StackHelper.isItemStackEmpty(outputStack)) {
            return;
        }

        this.energyHandler.receiveEnergy(energy, false);

        // Not enough operations performed
        if (this.getProgress() < getMaxProgress()) {
            this.setProgress(this.getProgress() + 1);
            return;
        }

        itemHandler.insertItem(1, plannedStack, false);
        
        this.setProgress(0);
    }

    private void spreadEnergy() {
        EnergyNetworkHelper.pushEnergy(getWorld(), getPos(), this.generatorEnergyHandler);
    }

    // Getters

    public ItemStack getInputStack() {
        return itemHandler.getStackInSlot(0);
    }
    
    public ItemStack getOutputStack() {
        return itemHandler.getStackInSlot(1);
    }

    public int getEnergy() {
        return this.energyHandler.getEnergyStored();
    }

    public int getProgress() {
        return this.progress;
    }

    public int getMaxProgress() {
        return EmergingTechnologyConfig.ELECTRICS_MODULE.BIOMASSGENERATOR.baseTimeTaken;
    }

    // Setters

    private void setEnergy(int quantity) {
        this.energy = quantity;
    }

    private void setProgress(int quantity) {
        this.progress = quantity;
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

    public int getField(int id) {
        switch (id) {
        case 0:
            return this.getEnergy();
        case 1:
            return this.getProgress();
        default:
            return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id) {
        case 0:
            this.setEnergy(value);
        break;
        case 1:
            this.setProgress(value);
        break;
        }
    }

    // OpenComputers

    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_biomass_generator";
    }
}