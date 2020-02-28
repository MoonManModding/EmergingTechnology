package io.moonman.emergingtechnology.block.blocks;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PollutedDirt extends Block {

private final String _name = "polluteddirt";

  public PollutedDirt() {
    super(Material.GROUND);
    this.setHardness(1.0f);
    this.setRegistryName(EmergingTechnology.MODID, _name);
    this.setUnlocalizedName(EmergingTechnology.MODID + "." + _name);
    this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
    this.setSoundType(SoundType.GROUND);
}

@Override
public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
        EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

    if (worldIn.isRemote) {
        return true;
    }

    if (facing == EnumFacing.UP) {
        worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState(), 3);
        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.offset(EnumFacing.UP).getY(), pos.getZ(),
                  new ItemStack(ModItems.plasticwaste));
          return true;
    }

    return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
}
}