package io.moonman.emergingtechnology.block.blocks;

import java.util.List;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Frame extends Block {
    private final String _name = "frame";

    private static final float yStop = 1.0f;
    private static final float yStart = 0.0f;

    protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0f, yStart, 0.0f, 1.0f, yStop, 0.0625f);
    protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(1.0f, yStart, 0.9375f, 0.0f, yStop, 1.0f);
    protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0625f, yStart, 1.0f, 0.0f, yStop, 0.0f);
    protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.9375f, yStart, 0.0f, 1.0f, yStop, 1.0f);

    public Frame() {
        super(Material.ANVIL);
        this.setHardness(1.0f);
        this.setLightOpacity(0);
        this.setRegistryName(EmergingTechnology.MODID, _name);
        this.setUnlocalizedName(EmergingTechnology.MODID + "." + _name);
        this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
        this.setSoundType(SoundType.METAL);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        tooltip.add(Lang.get(Lang.FRAME_DESC));
    }


    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }


    @Override
    public boolean isOpaqueCube(IBlockState iBlockState) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState iBlockState) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState iBlockState) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return this.getBox(blockState);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return this.getBox(state);
    }

    private AxisAlignedBB getBox(IBlockState state) {
        switch (state.getValue(PROPERTYFACING)) {
        case EAST:
            return EAST_AABB;
        case WEST:
            return WEST_AABB;
        case SOUTH:
            return SOUTH_AABB;
        case NORTH:
            return NORTH_AABB;
        default:
            return FULL_BLOCK_AABB;
        }
    }

    public static final PropertyDirection PROPERTYFACING = PropertyDirection.create("facing",
            EnumFacing.Plane.HORIZONTAL);

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getHorizontal(meta);
        return this.getDefaultState().withProperty(PROPERTYFACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumFacing facing = (EnumFacing) state.getValue(PROPERTYFACING);

        int facingbits = facing.getHorizontalIndex();
        return facingbits;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { PROPERTYFACING });
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing blockFaceClickedOn, float hitX,
            float hitY, float hitZ, int meta, EntityLivingBase placer) {

        EnumFacing enumfacing = (placer == null) ? EnumFacing.NORTH : EnumFacing.fromAngle(placer.rotationYaw);

        return this.getDefaultState().withProperty(PROPERTYFACING, enumfacing);
    }

}