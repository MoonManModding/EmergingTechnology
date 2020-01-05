package io.moonman.emergingtechnology.machines.hydroponic;

import java.util.List;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.PlantHelper;
import io.moonman.emergingtechnology.helpers.machines.HydroponicHelper;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.MachineBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;

public class Hydroponic extends MachineBase implements ITileEntityProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyBool HAS_WATER = PropertyBool.create("haswater");

    public Hydroponic() {
        super(Material.IRON, "hydroponic");
        this.setSoundType(SoundType.METAL);

        setDefaultState(
                blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(HAS_WATER, false));
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        int fluidUsage = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growBedWaterUsePerCycle;

        // TextComponentTranslation translation = new TextComponentTranslation("info.emergingtechnology.hydroponic.test");

        // tooltip.add(translation.getFormattedText());
        tooltip.add("Plants grown on this block can receive a growth boost.");
        tooltip.add("Requires a growth medium to provide boost.");
        tooltip.add("Requires at least " + fluidUsage + "MB of fluid per cycle to function");
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction,
            IPlantable plantable) {
        return getActualState(state, world, pos).getValue(HAS_WATER);
    }

    @Override
    public boolean isFertile(World world, BlockPos pos) {
        return world.getBlockState(pos).getValue(HAS_WATER);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState north = worldIn.getBlockState(pos.north());
            IBlockState east = worldIn.getBlockState(pos.east());
            IBlockState south = worldIn.getBlockState(pos.south());
            IBlockState west = worldIn.getBlockState(pos.west());

            EnumFacing face = (EnumFacing) state.getValue(FACING);

            if (face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) {
                face = EnumFacing.SOUTH;
            } else if (face == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) {
                face = EnumFacing.NORTH;
            } else if (face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) {
                face = EnumFacing.WEST;
            } else if (face == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) {
                face = EnumFacing.EAST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, face));
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
            ItemStack stack) {
        worldIn.setBlockState(pos,
                this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote) {
            return true;
        }

        HydroponicTileEntity tileEntity = (HydroponicTileEntity) worldIn.getTileEntity(pos);
        ItemStack itemStackHeld = playerIn.getHeldItemMainhand();
        Item itemHeld = itemStackHeld.getItem();

        if (itemHeld == Items.WATER_BUCKET || itemHeld == Items.LAVA_BUCKET) {

            Fluid fluid = itemHeld == Items.WATER_BUCKET ? FluidRegistry.WATER : FluidRegistry.LAVA;

            int waterFilled = tileEntity.fluidHandler.fill(new FluidStack(fluid, 1000), true);
            
            if (waterFilled > 0) {
                playerIn.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.BUCKET));
            }

        } else if (itemHeld instanceof UniversalBucket) {

            UniversalBucket bucket = (UniversalBucket) itemHeld;
            FluidStack fluidStack = bucket.getFluid(itemStackHeld);

            if (tileEntity.fluidHandler.canFillFluidType(fluidStack)) {
                tileEntity.fluidHandler.fill(fluidStack, true);
                playerIn.setHeldItem(EnumHand.MAIN_HAND, bucket.getEmpty());
            }

        } else if (HydroponicHelper.isItemStackValid(itemStackHeld) && tileEntity.itemHandler.getStackInSlot(0).isEmpty()) {

            ItemStack remainder = tileEntity.itemHandler.insertItem(0, itemStackHeld, false);

            playerIn.setHeldItem(EnumHand.MAIN_HAND, remainder);

            return true;

        } else if (PlantHelper.isPlantItem(itemHeld) && facing == EnumFacing.UP) {

            return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);

            // Otherwise open the gui
        } else {
            playerIn.openGui(EmergingTechnology.instance, Reference.GUI_HYDROPONIC, worldIn, pos.getX(), pos.getY(),
                    pos.getZ());
            return true;
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new HydroponicTileEntity();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { FACING, HAS_WATER });
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (tileEntity instanceof HydroponicTileEntity) {
            HydroponicTileEntity hydroponicTileEntity = (HydroponicTileEntity) tileEntity;

            boolean hasWater = hydroponicTileEntity.getWater() > 0;

            return state.withProperty(HAS_WATER, hasWater);
        }

        return state;

    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7)).withProperty(HAS_WATER,
                (meta & 8) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(FACING).getIndex() + (state.getValue(HAS_WATER) ? 8 : 0);
        return meta;
    }
}