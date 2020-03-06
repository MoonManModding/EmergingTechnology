package io.moonman.emergingtechnology.machines.solarglass;

import javax.annotation.Nullable;

import io.moonman.emergingtechnology.blocks.classes.ISidedTransparentBlock;
import io.moonman.emergingtechnology.machines.MachineBaseSimple;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public class SolarGlass extends MachineBaseSimple implements ISidedTransparentBlock {

    public static final String name = "solarglass";

    public SolarGlass() {
        super(name, Material.GLASS, SoundType.GLASS);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new SolarGlassTile();
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
      return adjacentBlockState.getBlock() instanceof ISidedTransparentBlock ? true : super.isSideInvisible(state, adjacentBlockState, side);
   }

}