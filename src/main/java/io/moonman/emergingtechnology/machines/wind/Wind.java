package io.moonman.emergingtechnology.machines.wind;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.gui.enums.ResourceTypeEnum;
import io.moonman.emergingtechnology.machines.classes.block.SimpleMachineBase;
import io.moonman.emergingtechnology.util.KeyBindings;
import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;

public class Wind extends SimpleMachineBase implements ITileEntityProvider {

    protected static final AxisAlignedBB AABB_COLUMN_UP = new AxisAlignedBB(0.25D, 0.2D, 0.25D, 0.75D, 1.0D, 0.75D);
    protected static final AxisAlignedBB AABB_SLAB_BOTTOM_UP = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.2D, 1.0D);
    protected static final AxisAlignedBB AABB_COLUMN_DOWN = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.8D, 0.75D);
    protected static final AxisAlignedBB AABB_SLAB_BOTTOM_DOWN = new AxisAlignedBB(0.0D, 0.8D, 0.0D, 1.0D, 1.0D, 1.0D);

    protected static final AxisAlignedBB AABB_COLUMN_WEST = new AxisAlignedBB(0.2D, 0.25D, 0.25D, 1.0D, 0.75D, 0.75D);
    protected static final AxisAlignedBB AABB_SLAB_BOTTOM_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.2D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_COLUMN_EAST = new AxisAlignedBB(0.0D, 0.25D, 0.25D, 0.8D, 0.75D, 0.75D);
    protected static final AxisAlignedBB AABB_SLAB_BOTTOM_EAST = new AxisAlignedBB(0.8D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    protected static final AxisAlignedBB AABB_COLUMN_SOUTH = new AxisAlignedBB(0.25D, 0.25D, 0.0D, 0.75D, 0.75D, 0.8D);
    protected static final AxisAlignedBB AABB_SLAB_BOTTOM_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.8D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_COLUMN_NORTH = new AxisAlignedBB(0.25D, 0.25D, 0.2D, 0.75D, 0.75D, 1.0D);
    protected static final AxisAlignedBB AABB_SLAB_BOTTOM_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.2D);

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public Wind() {
        super(Material.IRON, "wind");
        this.setSoundType(SoundType.METAL);

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {

        int energy = EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.energyGenerated;
        int min = EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.minOptimalHeight;
        int max = EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.maxOptimalHeight;
        int surround = EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.minimumAirBlocks;

        if (KeyBindings.showExtendedTooltips()) {
            tooltip.add(Lang.get(Lang.WIND_DESC));
            tooltip.add(Lang.getGenerated(energy, ResourceTypeEnum.ENERGY) + " " + Lang.getDepthBoost(min, max));
            tooltip.add(Lang.getAirBlocksRequired(surround));
        } else {
            tooltip.add(Lang.get(Lang.INTERACT_SHIFT));
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new WindTileEntity();
    }

    @Override
    public ExtendedBlockState createBlockState() {
        return new ExtendedBlockState(this, new IProperty[] { FACING, Properties.StaticProperty },
                new IUnlistedProperty[] { Properties.AnimationProperty });
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
            List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if (!isActualState) {
            state = this.getActualState(state, worldIn, pos);
        }

        for (AxisAlignedBB axisalignedbb : getCollisionBoxList(state)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
        }
    }

    private static List<AxisAlignedBB> getCollisionBoxList(IBlockState state) {
        List<AxisAlignedBB> list = new ArrayList<AxisAlignedBB>();

        // Need to switch between state.getValue(FACING) here
        
        switch (state.getValue(FACING)) {
            case DOWN:
                list.add(AABB_SLAB_BOTTOM_DOWN);
                list.add(AABB_COLUMN_DOWN);
               break;
            case EAST:
                list.add(AABB_SLAB_BOTTOM_EAST);
                list.add(AABB_COLUMN_EAST);
                break;
            case NORTH:
                list.add(AABB_SLAB_BOTTOM_NORTH);
                list.add(AABB_COLUMN_NORTH);
                break;
            case SOUTH:
                list.add(AABB_SLAB_BOTTOM_SOUTH);
                list.add(AABB_COLUMN_SOUTH);
                break;
            case UP:
                list.add(AABB_SLAB_BOTTOM_UP);
                list.add(AABB_COLUMN_UP);
                break;
            case WEST:
                list.add(AABB_SLAB_BOTTOM_WEST);
                list.add(AABB_COLUMN_WEST);
                break;
            default:
                list.add(AABB_SLAB_BOTTOM_UP);
                list.add(AABB_COLUMN_UP);
                break;

        }
        return list;
    }

    // Required for animation (?)

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(Properties.StaticProperty, true);
    }

    // End required for animation

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.VALUES[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }
}