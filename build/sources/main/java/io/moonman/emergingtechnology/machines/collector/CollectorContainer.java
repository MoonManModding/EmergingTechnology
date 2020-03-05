package io.moonman.emergingtechnology.machines.collector;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

// Handles inventory and slots
public class CollectorContainer extends Container {
	private final CollectorTileEntity tileEntity;

	public CollectorContainer(InventoryPlayer player, CollectorTileEntity tileEntity) {
		this.tileEntity = tileEntity;
		IItemHandler handler = tileEntity.itemHandler;

		this.addSlotToContainer(new SlotItemHandler(handler, 0, 22, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 50, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 2, 78, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 3, 106, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 4, 134, 35));

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
		
		// for(int i = 0; i < this.listeners.size(); ++i) 
		// {
		// 	IContainerListener listener = (IContainerListener)this.listeners.get(i);
		// 	if(this.heat != this.tileEntity.getField(0)) listener.sendWindowProperty(this, 0, this.tileEntity.getField(0));
		// 	if(this.progress != this.tileEntity.getField(1)) listener.sendWindowProperty(this, 1, this.tileEntity.getField(1));
		// }
        
		// this.heat = this.tileEntity.getField(0);
		// this.progress = this.tileEntity.getField(1);
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

			// If it's from the collector, put in player's inventory
			if (index < 5) {
				if (!this.mergeItemStack(fromStack, 5, 38, false)) {
					return ItemStack.EMPTY;
				} else {
					fromSlot.onSlotChanged();
				}
			} else {
				if (!this.mergeItemStack(fromStack, 0, 5, false)) {
					return ItemStack.EMPTY;
				} else {
					fromSlot.onSlotChanged();
				}
			}

			fromSlot.onTake(playerIn, fromStack);
		}
		return stack;
	}
}