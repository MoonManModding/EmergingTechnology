package io.moonman.emergingtechnology.block.blocks;

import javax.annotation.Nullable;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Torch extends Block {

  private final String _name = "torch";

  public static final PropertyDirection FACING = PropertyDirection.create("facing");

  protected static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.4000000059604645D, 0.0D, 0.4000000059604645D,
      0.6000000238418579D, 1.0D, 0.6000000238418579D);
  protected static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.4000000059604645D, 0.0D, 0.4000000059604645D,
      0.6000000238418579D, 1.0D, 0.6000000238418579D);
      protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.4000000059604645D, 0.4000000059604645D, 0.0D,
      0.6000000238418579D,  0.6000000238418579D, 1.0D);
      protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0, 0.4000000059604645D, 0.4000000059604645D,
      1.0, 0.6000000238418579D, 0.6000000238418579D);
      protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.4000000059604645D, 0.4000000059604645D, 0.0D,
      0.6000000238418579D,  0.6000000238418579D, 1.0D);
      protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0, 0.4000000059604645D, 0.4000000059604645D,
      1.0, 0.6000000238418579D, 0.6000000238418579D);

  public Torch() {
    super(Material.GLASS);
    this.setHardness(1.0f);
    this.setRegistryName(EmergingTechnology.MODID, _name);
    this.setUnlocalizedName(EmergingTechnology.MODID + "." + _name);
    this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
    this.setSoundType(SoundType.GLASS);
    this.setLightLevel(1.0f);
    this.setLightOpacity(0);

    setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
  }

  @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { FACING });
    }

    // @Override
    // public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    //     if (!worldIn.isRemote) {

    //         EnumFacing face = (EnumFacing) state.getValue(FACING);

    //         worldIn.setBlockState(pos, state.withProperty(FACING, face));
    //     }
    // }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, facing);
    }

    // @Override
    // public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
    //         ItemStack stack) {
    //     worldIn.setBlockState(pos,
    //             this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
    // }

  @Override
  public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
    return 15;
  }

  @Override
  public boolean isOpaqueCube(IBlockState iBlockState) {
    return false;
  }

  @Override
  public boolean isFullCube(IBlockState iBlockState) {
    return false;
  }

  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    switch(state.getValue(FACING)){
      case DOWN:
        return DOWN_AABB;
      case EAST:
        return EAST_AABB;
      case NORTH:
        return NORTH_AABB;
      case SOUTH:
        return SOUTH_AABB;
      case UP:
        return UP_AABB;
      case WEST:
        return WEST_AABB;
      default:
        return UP_AABB;

    }
  }

  @Nullable
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return NULL_AABB;
  }

  @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.VALUES[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

  // @SideOnly(Side.CLIENT)
  // public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos
  // pos, Random rand) {
  // double d0 = (double) pos.getX() + rand.nextDouble();
  // double d1 = (double) pos.getY() + rand.nextDouble();
  // double d2 = (double) pos.getZ() + rand.nextDouble();
  // worldIn.spawnParticle(EnumParticleTypes.END_ROD, d0, d1, d2, 0.0D, 0.0D,
  // 0.0D);
  // }
}