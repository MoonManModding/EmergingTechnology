package io.moonman.emergingtechnology.machines.wind;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.GeneratorEnergyStorageHandler;
import io.moonman.emergingtechnology.helpers.EnergyNetworkHelper;
import io.moonman.emergingtechnology.helpers.machines.WindHelper;
import io.moonman.emergingtechnology.helpers.machines.enums.TurbineSpeedEnum;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.tile.AnimatedMachineTileBase;
import io.moonman.emergingtechnology.network.PacketHandler;
import io.moonman.emergingtechnology.network.animation.WindGeneratorAnimationPacket;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class WindTileEntity extends AnimatedMachineTileBase implements SimpleComponent {

    public WindTileEntity() {
        initialiseAnimator(this, "wind");
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
            return CapabilityAnimation.ANIMATION_CAPABILITY.cast(getAnimator());
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

        String state = this.getAnimator().currentState();
        String newState = WindHelper.getTurbineStateFromSpeedEnum(speed);

        if (!state.equalsIgnoreCase(newState)) {
            this.getAnimator().transition(newState);
        }
    }

    private void setTurbineState(TurbineSpeedEnum speed) {

        if (speed != this.speed) {

            TargetPoint targetPoint = this.getTargetPoint();

            if (targetPoint == null) return;

            PacketHandler.INSTANCE.sendToAllTracking(new WindGeneratorAnimationPacket(this.getPos(), speed),
                    targetPoint);
        }

        this.speed = speed;
    }

    @Override
    public void notifyPlayer(EntityPlayerMP player) {
            PacketHandler.INSTANCE.sendTo(new WindGeneratorAnimationPacket(this.getPos(), speed), player);
    }

    // Getters

    public int getEnergy() {
        return this.energyHandler.getEnergyStored();
    }

    // Setters

    private void setEnergy(int quantity) {
        this.energy = quantity;
    }

    // OpenComputers

    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_wind_generator";
    }
}