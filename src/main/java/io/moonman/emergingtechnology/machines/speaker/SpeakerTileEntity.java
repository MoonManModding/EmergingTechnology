package io.moonman.emergingtechnology.machines.speaker;

import java.util.UUID;

import io.moonman.emergingtechnology.helpers.machines.SpeakerHelper;
import io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class SpeakerTileEntity extends MachineTileBase implements SimpleComponent {

    public String speakerName = "Speaker";

    public SpeakerTileEntity() {
        speakerName = UUID.randomUUID().toString();
    }

    // @Override
    // public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
    //     if (capability == CapabilityEnergy.ENERGY)
    //         return true;

    //     return super.hasCapability(capability, facing);
    // }

    // @Override
    // public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
    //     if (capability == CapabilityEnergy.ENERGY)
    //         return CapabilityEnergy.ENERGY.cast(this.generatorEnergyHandler);
    //     return super.getCapability(capability, facing);
    // }

    // @Override
    // public void readFromNBT(NBTTagCompound compound) {
    //     super.readFromNBT(compound);

    //     this.energyHandler.readFromNBT(compound);

    //     this.setEnergy(compound.getInteger("GuiEnergy"));
    // }

    // @Override
    // public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    //     super.writeToNBT(compound);
    //     compound.setInteger("GuiEnergy", this.getEnergy());
    //     this.energyHandler.writeToNBT(compound);

    //     return compound;
    // }

    @Override
    public void cycle() {

    }

    public void handleCommand(int commandId) {
        SpeakerHelper.handleCommand(this.speakerName, commandId);
    }

    public boolean getIsPowered() {
        return true;
    }


    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_speaker";
    }
}