package io.moonman.emergingtechnology.container.containers;

import io.moonman.emergingtechnology.tile.tiles.HydroponicTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

// Handles inventory and slots
public class HydroponicContainer extends Container {
	private final HydroponicTileEntity tileEntity;

	public HydroponicContainer(InventoryPlayer player, HydroponicTileEntity tileEntity) {
		this.tileEntity = tileEntity;
		IItemHandler handler = tileEntity.itemHandler;

		this.addSlotToContainer(new SlotItemHandler(handler, 0, 44, 31));

		// Inventory
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}

		// Hotbar
		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}
	
	@Override
	public void detectAndSendChanges() 
	{
		super.detectAndSendChanges();
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.tileEntity.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {

		ItemStack stack = ItemStack.EMPTY;
		Slot fromSlot = (Slot) this.inventorySlots.get(index);

		if (fromSlot != null && fromSlot.getHasStack()) {
			ItemStack fromStack = fromSlot.getStack();
			stack = fromStack.copy();

			if (index < 1) {
				if (!this.mergeItemStack(fromStack, 1, 37, false)) {
					return ItemStack.EMPTY;
				}
			} else {
				if (!this.mergeItemStack(fromStack, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			}

			fromSlot.onTake(playerIn, fromStack);
		}
		return stack;
	}
}