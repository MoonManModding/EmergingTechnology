package io.moonman.emergingtechnology.machines;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class MachineBase extends MachineBaseSimple {

    public MachineBase(String name, Material material) {
        super(name, material);
    }

    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);

        if (te == null) return;

        LazyOptional<IItemHandler> capability = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        capability.ifPresent(x -> {
            for (int i = 0; i < x.getSlots(); ++i) {
                ItemStack itemstack = x.getStackInSlot(i);
    
                if (!itemstack.isEmpty()) {
                    InventoryHelper.spawnItemStack((World) worldIn, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), itemstack);
                }
            }
        });
    }
}