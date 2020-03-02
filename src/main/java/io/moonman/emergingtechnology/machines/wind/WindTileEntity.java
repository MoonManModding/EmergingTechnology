package io.moonman.emergingtechnology.machines.wind;

import com.google.common.collect.ImmutableMap;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.GeneratorEnergyStorageHandler;
import io.moonman.emergingtechnology.helpers.EnergyNetworkHelper;
import io.moonman.emergingtechnology.helpers.machines.WindHelper;
import io.moonman.emergingtechnology.helpers.machines.enums.TurbineSpeedEnum;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.MachineTileBase;
import io.moonman.emergingtechnology.network.PacketHandler;
import io.moonman.emergingtechnology.network.animation.WindGeneratorAnimationPacket;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import li.cil.oc.api.network.SimpleComponent;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class WindTileEntity extends MachineTileBase implements SimpleComponent {

    private final IAnimationStateMachine asm;

    public WindTileEntity() {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            asm = ModelLoaderRegistry.loadASM(new ResourceLocation(EmergingTechnology.MODID, "asms/block/wind.json"),
                    ImmutableMap.of());
        } else
            asm = null;
    }

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.WIND_ENERGY_CAPACITY) {
        @Override
        public void onContentsChanged() {
            markDirty();
            super.onContentsChanged();
        }
    };

    public GeneratorEnergyStorageHandler generatorEnergyHandler = new GeneratorEnergyStorageHandler(energyHandler) {

    };

    private int energy = 0;
    private TurbineSpeedEnum speed = TurbineSpeedEnum.OFF;

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    @Override
    public boolean isEnergyGeneratorTile() {
        return true;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return true;
        if (capability == CapabilityAnimation.ANIMATION_CAPABILITY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(this.generatorEnergyHandler);
        if (capability == CapabilityAnimation.ANIMATION_CAPABILITY)
            return CapabilityAnimation.ANIMATION_CAPABILITY.cast(asm);
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.energyHandler.readFromNBT(compound);

        this.setEnergy(compound.getInteger("GuiEnergy"));

        this.setTurbineState(TurbineSpeedEnum.getById(compound.getInteger("Speed")));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("GuiEnergy", this.getEnergy());
        compound.setInteger("Speed", TurbineSpeedEnum.getId(this.speed));
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
        if (WindHelper.isGeneratorInAir(getWorld(), getPos())) {

            int energy = EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.energyGenerated;

            if (WindHelper.isGeneratorAtOptimalHeight(getPos()) || getWorld().isThundering()) {
                energy *= 2;
                this.setTurbineState(TurbineSpeedEnum.FAST);
            } else {
                this.setTurbineState(TurbineSpeedEnum.SLOW);
            }

            this.energyHandler.receiveEnergy(energy, false);
        } else {
            this.setTurbineState(TurbineSpeedEnum.OFF);
        }
    }

    private void spreadEnergy() {
        EnergyNetworkHelper.pushEnergy(getWorld(), getPos(), this.generatorEnergyHandler);
    }

    @SideOnly(Side.CLIENT)
    public void setTurbineStateClient(TurbineSpeedEnum speed) {

        String state = this.asm.currentState();
        String newState = WindHelper.getTurbineStateFromSpeedEnum(speed);

        if (!state.equalsIgnoreCase(newState)) {
            this.asm.transition(newState);
        }
    }

    private void setTurbineState(TurbineSpeedEnum speed) {

        if (speed != this.speed) {

            TargetPoint targetPoint = PacketHandler.getTargetPoint(getWorld(), getPos());

            if (targetPoint == null) return;

            PacketHandler.INSTANCE.sendToAllTracking(new WindGeneratorAnimationPacket(this.getPos(), speed),
                    targetPoint);
        }

        this.speed = speed;
    }

    // Getters

    public int getEnergy() {
        return this.energyHandler.getEnergyStored();
    }

    // Setters

    private void setEnergy(int quantity) {
        this.energy = quantity;
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
        return "etech_wind_generator";
    }
}