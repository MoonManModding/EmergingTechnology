package io.moonman.emergingtechnology.block.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Torch extends Block {

  private final String _name = "torch";

  protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.4000000059604645D, 0.0D, 0.4000000059604645D,
      0.6000000238418579D, 1.0D, 0.6000000238418579D);

  public Torch() {
    super(Material.GLASS);
    this.setHardness(1.0f);
    this.setRegistryName(EmergingTechnology.MODID, _name);
    this.setUnlocalizedName(EmergingTechnology.MODID + "." + _name);
    this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
    this.setSoundType(SoundType.GLASS);
    this.setLightLevel(1.0f);
    this.setLightOpacity(0);
  }

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
    return STANDING_AABB;
  }

  @Nullable
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return NULL_AABB;
  }

  // @SideOnly(Side.CLIENT)
  // public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
  //   double d0 = (double) pos.getX() + rand.nextDouble();
  //   double d1 = (double) pos.getY() + rand.nextDouble();
  //   double d2 = (double) pos.getZ() + rand.nextDouble();
  //   worldIn.spawnParticle(EnumParticleTypes.END_ROD, d0, d1, d2, 0.0D, 0.0D, 0.0D);
  // }
}