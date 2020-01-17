package io.moonman.emergingtechnology.machines.battery;

import io.moonman.emergingtechnology.handlers.energy.ConsumerEnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.GeneratorEnergyStorageHandler;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.MachineTileBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Optional;
import li.cil.oc.api.network.SimpleComponent;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class BatteryTileEntity extends MachineTileBase implements ITickable, SimpleComponent {

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.BATTERY_ENERGY_CAPACITY) {
        @Override
        public void onContentsChanged() {
            markDirtyClient();
            super.onContentsChanged();
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            int energy = super.extractEnergy(maxExtract, simulate);

            int output = getTotalOutput() + energy;

            setTotalOutput(output);

            return energy;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            int energy = super.receiveEnergy(maxReceive, simulate);

            int input = getTotalInput() + energy;

            setTotalInput(input);

            return energy;
        }
    };

    public GeneratorEnergyStorageHandler generatorStorageHandler = new GeneratorEnergyStorageHandler(energyHandler);
    public ConsumerEnergyStorageHandler consumerStorageHandler = new ConsumerEnergyStorageHandler(energyHandler);

    int energy = 0;

    int totalInput = 0;
    int totalOutput = 0;

    int monitorCount = 0;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            if (facing == getFacing()) {
                return CapabilityEnergy.ENERGY.cast(this.consumerStorageHandler);
            } else {
                return CapabilityEnergy.ENERGY.cast(this.generatorStorageHandler);
            }
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.energyHandler.readFromNBT(compound);

        this.setEnergy(compound.getInteger("GuiEnergy"));
        this.setTotalInput(compound.getInteger("GuiTotalInput"));
        this.setTotalOutput(compound.getInteger("GuiTotalOutput"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("GuiEnergy", this.getEnergy());
        compound.setInteger("GuiTotalInput", this.getTotalInput());
        compound.setInteger("GuiTotalOutput", this.getTotalOutput());
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
        spreadEnergy();
        resetEnergyMonitor();
    }

    private void spreadEnergy() {
        for (EnumFacing side : EnumFacing.VALUES) {

            if (side == getFacing()) {
                continue;
            }

            TileEntity tileEntity = world.getTileEntity(pos.offset(side));

            if (tileEntity != null) {
                IEnergyStorage otherStorage = tileEntity.getCapability(CapabilityEnergy.ENERGY, side.getOpposite());

                if (otherStorage != null) {
                    if (otherStorage.canReceive()) {
                        if (this.getEnergy() > 0) {
                            int energySpread = otherStorage.receiveEnergy(this.getEnergy(), false);
                            this.energyHandler.extractEnergy(energySpread, false);
                        }
                    }
                }
            }
        }
    }

    private void resetEnergyMonitor() {
        if (monitorCount == 1) {
            this.totalInput = 0;
            this.totalOutput = 0;
            this.monitorCount = 0;
        } else {
            this.monitorCount++;
        }
    }

    // Getters

    public int getEnergy() {
        return this.energyHandler.getEnergyStored();
    }

    public int getTotalInput() {
        return this.totalInput;
    }

    public int getTotalOutput() {
        return this.totalOutput;
    }

    public EnumFacing getFacing() {
        int metadata = getBlockMetadata();

        return EnumFacing.VALUES[metadata];
    }

    // Setters

    private void setEnergy(int quantity) {
        this.energy = quantity;
    }

    private void setTotalInput(int quantity) {
            this.totalInput = quantity;
    }

    private void setTotalOutput(int quantity) {
            this.totalOutput = quantity;
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
            return this.getTotalInput();
        case 2:
            return this.getTotalOutput();
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
            this.setTotalInput(value);
            break;
        case 2:
            this.setTotalOutput(value);
            break;
        }
    }

    // OpenComputers

    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_battery";
    }
}