package io.moonman.emergingtechnology.blocks;

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

public class ClearPlasticBlock extends SimpleBlock {

    public static final String name = "clearplasticblock";

    public ClearPlasticBlock() {
        super(name, Material.GLASS, SoundType.STONE);
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
     }

     public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
     }
  
     public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
     }
  
     public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
     }
  
     public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
        return false;
     }

    @OnlyIn(Dist.CLIENT)
   public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
      return adjacentBlockState.getBlock() == this ? true : super.isSideInvisible(state, adjacentBlockState, side);
   }
}