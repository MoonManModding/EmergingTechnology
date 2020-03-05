package io.moonman.emergingtechnology.machines.solar;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.helpers.EnergyNetworkHelper;
import io.moonman.emergingtechnology.helpers.machines.SolarHelper;
import io.moonman.emergingtechnology.init.ModTiles;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.MachineTileBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

public class SolarTile extends MachineTileBase {

    public SolarTile() {
        super(ModTiles.SOLAR);
    }

    public LazyOptional<EnergyStorageHandler> energyHandler = LazyOptional.of(this::createEnergy);

    private EnergyStorageHandler createEnergy() {
        return new EnergyStorageHandler(Reference.SOLAR_ENERGY_CAPACITY){
            @Override
            public void onContentsChanged() {
                markDirty();
                super.onContentsChanged();
            }
        };
    }

    @Override
    public boolean isEnergyGeneratorTile() {
        return true;
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT energyTag = tag.getCompound("energy");
        energyHandler.ifPresent(h -> h.deserializeNBT(energyTag));
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        energyHandler.ifPresent(h -> {
            CompoundNBT compound = h.serializeNBT();
            tag.put("energy", compound);
        });
        return super.write(tag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return energyHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void cycle() {
        generateEnergy();
        spreadEnergy();
    }

    private void generateEnergy() {
        SolarHelper.generateEnergy(getWorld(), getPos(), this.energyHandler);
    }

    private void spreadEnergy() {
        EnergyNetworkHelper.pushEnergy(getWorld(), getPos(), this.energyHandler);
    }


}