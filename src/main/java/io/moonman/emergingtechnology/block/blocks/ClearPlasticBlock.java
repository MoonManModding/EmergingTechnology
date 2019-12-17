package io.moonman.emergingtechnology.block.blocks;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClearPlasticBlock extends BlockBreakable {

  private final String _name = "clearplasticblock";

  public ClearPlasticBlock() {
    super(Material.GLASS, false);
    this.setHardness(1.0f);
    this.setRegistryName(EmergingTechnology.MODID, _name);
    this.setUnlocalizedName(EmergingTechnology.MODID + "." + _name);
    this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
    this.setSoundType(SoundType.GLASS);
    this.setLightOpacity(0);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public BlockRenderLayer getBlockLayer() {
    return BlockRenderLayer.CUTOUT;
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return false;
  }

  // @Override
  // public boolean isOpaqueCube(IBlockState iBlockState) {
  //     return false;
  // }

  @SideOnly(Side.CLIENT)
  @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();

        if (this == ModBlocks.clearplasticblock)
        {
            if (blockState != iblockstate)
            {
                return true;
            }

            if (block == this)
            {
                return false;
            }
        }

        return block == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
}