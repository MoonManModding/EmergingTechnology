package io.moonman.emergingtechnology.machines.hydroponic;

import java.util.List;

import javax.annotation.Nullable;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.hydroponics.bed.GrowBedConfiguration;
import io.moonman.emergingtechnology.machines.MachineBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class Hydroponic extends MachineBase {

    public static final String name = "hydroponic";
    public static final String registryName = EmergingTechnology.MODID_REG + name;

    public Hydroponic() {
        super(name, Material.IRON);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new HydroponicTile();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip,
            ITooltipFlag flagIn) {

        int fluidUsage = GrowBedConfiguration.WATER_USAGE.get();

        tooltip.add(new StringTextComponent("Uses " + fluidUsage + "MB per cycle"));

        // if (KeyBindings.showExtendedTooltips()) {
        // tooltip.add(Lang.get(Lang.HYDROPONIC_DESC));
        // tooltip.add(Lang.getRequired(fluidUsage, ResourceTypeEnum.FLUID));
        // } else {
        // tooltip.add(Lang.get(Lang.INTERACT_SHIFT));
        // }
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction direction,
            IPlantable plantable) {
        return true;
    }

    @Override
    public boolean isFertile(IBlockReader world, BlockPos pos) {
        return true;
    }

    @Deprecated
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
            BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return true;
        }

        HydroponicTile tileEntity = (HydroponicTile) worldIn.getTileEntity(pos);
        ItemStack itemStackHeld = player.getHeldItemMainhand();
        Item itemHeld = itemStackHeld.getItem();

        if (itemHeld == Items.WATER_BUCKET || itemHeld == Items.LAVA_BUCKET) {

            Fluid fluid = itemHeld == Items.WATER_BUCKET ? Fluids.WATER : Fluids.LAVA;

            tileEntity.fluidHandler.ifPresent(f -> {
                int waterFilled = f.fill(new FluidStack(fluid, 1000), FluidAction.EXECUTE);

                if (waterFilled > 0) {
                    player.setHeldItem(Hand.MAIN_HAND, new ItemStack(Items.BUCKET));
                }
            });

        }
        
        // } else if (itemHeld instanceof UniversalBucket) {

        // UniversalBucket bucket = (UniversalBucket) itemHeld;
        // FluidStack fluidStack = bucket.getFluid(itemStackHeld);

        // if (tileEntity.fluidHandler.canFillFluidType(fluidStack)) {
        // tileEntity.fluidHandler.fill(fluidStack, true);
        // player.setHeldItem(Hand.MAIN_HAND, bucket.getEmpty());
        // }

        // } else if (HydroponicHelper.isItemStackValid(itemStackHeld) &&
        // tileEntity.itemHandler.getStackInSlot(0).isEmpty()) {

        // ItemStack remainder = tileEntity.itemHandler.insertItem(0, itemStackHeld,
        // false);

        // player.setHeldItem(EnumHand.MAIN_HAND, remainder);

        // return true;

        // } else if (PlantHelper.isPlantItem(itemHeld) && facing == EnumFacing.UP) {

        // return super.onBlockActivated(worldIn, pos, state, player, hand, facing,
        // hitX, hitY, hitZ);

        // // Otherwise open the gui
        // } else {
        // player.openGui(EmergingTechnology.instance, Reference.GUI_HYDROPONIC,
        // worldIn, pos.getX(), pos.getY(),
        // pos.getZ());
        // return true;
        // }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}