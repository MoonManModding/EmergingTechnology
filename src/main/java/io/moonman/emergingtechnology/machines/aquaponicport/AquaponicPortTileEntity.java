package io.moonman.emergingtechnology.machines.aquaponicport;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModFluids;
import io.moonman.emergingtechnology.machines.aquaponic.AquaponicTileEntity;
import io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.items.CapabilityItemHandler;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class AquaponicPortTileEntity extends MachineTileBase implements SimpleComponent {

    private AquaponicTileEntity controller;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {

        if (this.isControllerValid()) {

            if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
                return true;
            if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                return true;
            if (capability == CapabilityEnergy.ENERGY)
                return true;

        }

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

        if (this.isControllerValid()) {

            if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.controller.fluidTanksHandler);
            if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.controller.automationItemHandler);
            if (capability == CapabilityEnergy.ENERGY)
                return CapabilityEnergy.ENERGY.cast(this.controller.consumerEnergyHandler);

        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void cycle() {
        pushToFluidConsumers();
    }

    private void pushToFluidConsumers() {

        if (!this.isControllerValid())
            return;

        for (EnumFacing facing : EnumFacing.VALUES) {

            if (this.controller
                    .getNutrientFluid() < EmergingTechnologyConfig.HYDROPONICS_MODULE.AQUAPONIC.aquaponicFluidTransferRate) {
                return;
            }

            TileEntity neighbour = this.world.getTileEntity(this.pos.offset(facing));

            // Return if no tile entity
            if (neighbour == null) {
                continue;
            }

            IFluidHandler neighbourFluidHandler = neighbour
                    .getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());

            // Return if neighbour has no fluid tank
            if (neighbourFluidHandler == null) {
                continue;
            }

            // Fill the neighbour
            int filled = neighbourFluidHandler.fill(new FluidStack(ModFluids.NUTRIENT,
                    EmergingTechnologyConfig.HYDROPONICS_MODULE.AQUAPONIC.aquaponicFluidTransferRate), true);

            this.controller.getNutrientFluidHandler().drain(filled, true);
        }
    }

    public void setController(AquaponicTileEntity controller) {
        if (this.controller != controller) {
            this.controller = controller;
            updateNeighbours();
        }
    }

    private void updateNeighbours() {
        if (getWorld() == null) {
            return;
        }

        IBlockState state = getWorld().getBlockState(getPos());
        getWorld().notifyBlockUpdate(getPos(), state, state, 3);
        getWorld().markBlockRangeForRenderUpdate(getPos(), getPos());
		getWorld().scheduleBlockUpdate(getPos(), this.getBlockType(), 0, 0);
    }

    private boolean isControllerValid() {
        if (this.controller != null) {
            return this.controller.getIsMultiblock();
        }

        return false;
    }

    // OpenComputers

    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_aquaponicport";
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getWaterLevel(Context context, Arguments args) {
        int level = this.controller.getWater();
        return new Object[] { level };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getEnergyLevel(Context context, Arguments args) {
        int level = this.controller.getEnergy();
        return new Object[] { level };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getProgress(Context context, Arguments args) {
        int value = this.controller.getProgress();
        return new Object[] { value };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getFluid(Context context, Arguments args) {
        int value = this.controller.getNutrientFluid();
        return new Object[] { value };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] isValidStructure(Context context, Arguments args) {
        boolean value = this.controller.getIsMultiblock();
        return new Object[] { value };
    }
}