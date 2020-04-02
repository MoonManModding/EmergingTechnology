package io.moonman.emergingtechnology.machines.battery;

import java.util.List;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.gui.enums.ResourceTypeEnum;
import io.moonman.emergingtechnology.helpers.classes.BatteryConfiguration;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.block.SimpleMachineBase;
import io.moonman.emergingtechnology.util.KeyBindings;
import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Battery extends SimpleMachineBase implements ITileEntityProvider {

    public static final PropertyBool INPUT_UP = PropertyBool.create("input_up");
    public static final PropertyBool INPUT_DOWN = PropertyBool.create("input_down");
    public static final PropertyBool INPUT_NORTH = PropertyBool.create("input_north");
    public static final PropertyBool INPUT_SOUTH = PropertyBool.create("input_south");
    public static final PropertyBool INPUT_EAST = PropertyBool.create("input_east");
    public static final PropertyBool INPUT_WEST = PropertyBool.create("input_west");

    public Battery() {
        super(Material.IRON, "battery");
        this.setSoundType(SoundType.METAL);

        setDefaultState(getDefaultSideProperties(blockState.getBaseState()));
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {

        int energy = Reference.BATTERY_ENERGY_CAPACITY;

        if (KeyBindings.showExtendedTooltips()) {
            tooltip.add(Lang.get(Lang.BATTERY_DESC));
            tooltip.add(Lang.getCapacity(energy, ResourceTypeEnum.ENERGY));
        } else {
            tooltip.add(Lang.get(Lang.INTERACT_SHIFT));
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote) {
            return true;
        }

        // TileEntity tileEntity = worldIn.getTileEntity(pos);

        // if (tileEntity != null) {
        //     if (tileEntity instanceof BatteryTileEntity == true) {
        //         BatteryTileEntity battery = (BatteryTileEntity) tileEntity;

        //         BatteryConfiguration config = battery.getConfiguration();

        //         config.setSideInput(facing, !config.getSideInput(facing));
        //     }
        // }

        playerIn.openGui(EmergingTechnology.instance, Reference.GUI_BATTERY, worldIn, pos.getX(), pos.getY(),
                pos.getZ());

        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new BatteryTileEntity();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this,
                new IProperty[] { INPUT_UP, INPUT_DOWN, INPUT_NORTH, INPUT_SOUTH, INPUT_EAST, INPUT_WEST });
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            worldIn.setBlockState(pos, getDefaultSideProperties(state));
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultSideProperties(this.getDefaultState());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
            ItemStack stack) {
        worldIn.setBlockState(pos, getDefaultSideProperties(this.getDefaultState()));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (tileEntity != null) {
            if (tileEntity instanceof BatteryTileEntity == true) {
                BatteryTileEntity battery = (BatteryTileEntity) tileEntity;

                BatteryConfiguration config = battery.getConfiguration();

                return state.withProperty(INPUT_UP, config.getSideInput(EnumFacing.UP))
                        .withProperty(INPUT_DOWN, config.getSideInput(EnumFacing.DOWN))
                        .withProperty(INPUT_NORTH, config.getSideInput(EnumFacing.NORTH))
                        .withProperty(INPUT_SOUTH, config.getSideInput(EnumFacing.SOUTH))
                        .withProperty(INPUT_EAST, config.getSideInput(EnumFacing.EAST))
                        .withProperty(INPUT_WEST, config.getSideInput(EnumFacing.WEST));
            }
        }

        return super.getActualState(state, worldIn, pos);

    }

    private IBlockState getDefaultSideProperties(IBlockState state) {
        return state.withProperty(INPUT_UP, false).withProperty(INPUT_DOWN, false).withProperty(INPUT_NORTH, false)
                .withProperty(INPUT_SOUTH, false).withProperty(INPUT_EAST, false).withProperty(INPUT_WEST, false);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }
}