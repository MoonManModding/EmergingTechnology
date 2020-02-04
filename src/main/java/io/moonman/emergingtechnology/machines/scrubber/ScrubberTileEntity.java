package io.moonman.emergingtechnology.machines.scrubber;

import com.google.common.collect.ImmutableMap;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.AutomationItemStackHandler;
import io.moonman.emergingtechnology.handlers.energy.ConsumerEnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.handlers.FluidStorageHandler;
import io.moonman.emergingtechnology.helpers.machines.ScrubberHelper;
import io.moonman.emergingtechnology.helpers.machines.WindHelper;
import io.moonman.emergingtechnology.helpers.machines.enums.TurbineSpeedEnum;
import io.moonman.emergingtechnology.init.ModFluids;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.MachineTileBase;
import io.moonman.emergingtechnology.network.PacketHandler;
import io.moonman.emergingtechnology.network.ScrubberAnimationPacket;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class ScrubberTileEntity extends MachineTileBase implements ITickable, SimpleComponent {

    private final IAnimationStateMachine asm;

    public ScrubberTileEntity() {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            asm = ModelLoaderRegistry.loadASM(
                    new ResourceLocation(EmergingTechnology.MODID, "asms/block/scrubber.json"), ImmutableMap.of());
        } else
            asm = null;
    }

    public FluidTank fluidHandler = new FluidStorageHandler(Reference.SCRUBBER_FLUID_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }
    };

    public FluidTank gasHandler = new FluidStorageHandler(Reference.SCRUBBER_GAS_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }

        @Override
        public boolean canFillFluidType(FluidStack fluidStack) {

            Fluid fluid = fluidStack.getFluid();

            return fluid == ModFluids.CARBON_DIOXIDE || fluid.getName() == "carbondioxide";
        }
    };

    public EnergyStorageHandler energyHandler = new EnergyStorageHandler(Reference.SCRUBBER_ENERGY_CAPACITY) {
        @Override
        public void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }
    };

    public ConsumerEnergyStorageHandler consumerEnergyHandler = new ConsumerEnergyStorageHandler(energyHandler);

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

    private int water = this.fluidHandler.getFluidAmount();
    private int gas = this.gasHandler.getFluidAmount();
    private int energy = this.energyHandler.getEnergyStored();

    private int progress = 0;
    private TurbineSpeedEnum speed = TurbineSpeedEnum.OFF;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return true;
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
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.fluidHandler);
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.automationItemHandler);
        if (capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(this.consumerEnergyHandler);
        if (capability == CapabilityAnimation.ANIMATION_CAPABILITY)
            return CapabilityAnimation.ANIMATION_CAPABILITY.cast(asm);
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.itemHandler.deserializeNBT(compound.getCompoundTag("Inventory"));

        this.setWater(compound.getInteger("GuiWater"));
        this.setGas(compound.getInteger("GuiGas"));
        this.setEnergy(compound.getInteger("GuiEnergy"));
        this.setProgress(compound.getInteger("GuiProgress"));
        this.setTurbineState(TurbineSpeedEnum.getById(compound.getInteger("Speed")));

        this.fluidHandler.readFromNBT(compound);
        this.gasHandler.readFromNBT(compound);
        this.energyHandler.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setTag("Inventory", this.itemHandler.serializeNBT());

        compound.setInteger("GuiWater", this.getWater());
        compound.setInteger("GuiGas", this.getGas());
        compound.setInteger("GuiEnergy", this.getEnergy());
        compound.setInteger("GuiProgress", this.getProgress());
        compound.setInteger("Speed", TurbineSpeedEnum.getId(this.speed));

        this.fluidHandler.writeToNBT(compound);
        this.gasHandler.writeToNBT(compound);
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
        this.setEnergy(this.energyHandler.getEnergyStored());
        this.setWater(this.fluidHandler.getFluidAmount());
        this.setGas(this.gasHandler.getFluidAmount());

        doProcessing();
    }

    public void doProcessing() {

        // Gas full
        if (this.getGas() + EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.scrubberGasGenerated >= Reference.SCRUBBER_GAS_CAPACITY) {
            this.setTurbineState(TurbineSpeedEnum.OFF);
            return;
        }

        // Not enough water
        if (this.getWater() < EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.scrubberWaterBaseUsage) {
            this.setTurbineState(TurbineSpeedEnum.OFF);
            return;
        }

        // Not enough energy
        if (this.getEnergy() < EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.scrubberEnergyBaseUsage) {
            this.setTurbineState(TurbineSpeedEnum.OFF);
            return;
        }

        this.setTurbineState(TurbineSpeedEnum.FAST);

        this.energyHandler.extractEnergy(EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.scrubberEnergyBaseUsage,
                false);

        this.fluidHandler.drain(EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.scrubberWaterBaseUsage, true);

        this.setEnergy(this.energyHandler.getEnergyStored());
        this.setWater(this.fluidHandler.getFluidAmount());

        // Not enough operations performed
        if (this.getProgress() < EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.scrubberBaseTimeTaken) {
            this.setProgress(this.getProgress() + 1);
            return;
        }

        this.gasHandler.fill(new FluidStack(ModFluids.CARBON_DIOXIDE,
                EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.scrubberGasGenerated), true);

        this.setGas(this.gasHandler.getFluidAmount());

        this.setProgress(0);
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
            PacketHandler.INSTANCE.sendToAll(new ScrubberAnimationPacket(this.getPos(), speed));
        }

        this.speed = speed;
    }

    public ItemStack getInputStack() {
        return itemHandler.getStackInSlot(0);
    }

    public ItemStack getOutputStack() {
        return itemHandler.getStackInSlot(1);
    }

    // Getters

    public int getWater() {
        return this.fluidHandler.getFluidAmount();
    }

    public int getGas() {
        return this.gasHandler.getFluidAmount();
    }

    public int getEnergy() {
        return this.energyHandler.getEnergyStored();
    }

    public int getProgress() {
        return this.progress;
    }

    // Setters

    private void setWater(int quantity) {
        this.water = quantity;
    }

    private void setGas(int quantity) {
        this.gas = quantity;
    }

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
            return this.getWater();
        case 2:
            return this.getProgress();
        case 3:
            return this.getGas();
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
            this.setWater(value);
            break;
        case 2:
            this.setProgress(value);
            break;
        case 3:
            this.setGas(value);
            break;
        }
    }

    // OpenComputers

    @Optional.Method(modid = "opencomputers")
    @Override
    public String getComponentName() {
        return "etech_scrubber";
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getWaterLevel(Context context, Arguments args) {
        int level = getWater();
        return new Object[] { level };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getEnergyLevel(Context context, Arguments args) {
        int level = getEnergy();
        return new Object[] { level };
    }

    @Callback
    @Optional.Method(modid = "opencomputers")
    public Object[] getProgress(Context context, Arguments args) {
        int value = getProgress();
        return new Object[] { value };
    }
}