package io.moonman.emergingtechnology.blocks;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.blocks.classes.ISidedTransparentBlock;
import io.moonman.emergingtechnology.blocks.classes.SimpleBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ClearPlasticBlock extends SimpleBlock implements ISidedTransparentBlock {

   public static final String name = "clearplasticblock";
   public static final String registryName = EmergingTechnology.MODID_REG + name;

   public ClearPlasticBlock() {
      super(name, Material.GLASS, SoundType.STONE);
   }

   @Override
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   @Override
   public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
      return true;
   }

   @Override
   public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return false;
   }

   @Override
   public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return false;
   }

   @Override
   public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
      return false;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
      return adjacentBlockState.getBlock() instanceof ISidedTransparentBlock ? true
            : super.isSideInvisible(state, adjacentBlockState, side);
   }
}