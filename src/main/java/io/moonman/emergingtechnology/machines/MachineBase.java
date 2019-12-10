package io.moonman.emergingtechnology.machines;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class MachineBase extends Block {

    public MachineBase(Material material, String name) {
        super(material);
        this.setHardness(1.0f);
        this.setRegistryName(EmergingTechnology.MODID, name);
        this.setUnlocalizedName(EmergingTechnology.MODID + "." + name);
        this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
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
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity te = world.getTileEntity(pos);

        IItemHandler cap = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        for (int i = 0; i < cap.getSlots(); ++i) {
            ItemStack itemstack = cap.getStackInSlot(i);

            if (!itemstack.isEmpty()) {
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
            }
        }
    }

}