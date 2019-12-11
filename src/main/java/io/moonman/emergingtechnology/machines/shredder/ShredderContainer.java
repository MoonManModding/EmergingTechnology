package io.moonman.emergingtechnology.machines.shredder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

// Handles inventory and slots
public class ShredderContainer extends Container {
	private final ShredderTileEntity tileEntity;

	private int energy;

	public ShredderContainer(InventoryPlayer player, ShredderTileEntity tileEntity) {
		this.tileEntity = tileEntity;
		IItemHandler handler = tileEntity.itemHandler;

		this.addSlotToContainer(new SlotItemHandler(handler, 0, 17, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 80, 35));

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
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) 
	{
		this.tileEntity.setField(id, data);
	}
	
	@Override
	public void detectAndSendChanges() 
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); ++i) 
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			if(this.energy != this.tileEntity.getField(0)) listener.sendWindowProperty(this, 0, this.tileEntity.getField(0));
		}
        
		this.energy = this.tileEntity.getField(0);
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

			// If it's from the shredder, put in player's inventory
			if (index < 2) {
				if (!this.mergeItemStack(fromStack, 2, 38, false)) {
					return ItemStack.EMPTY;
				} else {
					fromSlot.onSlotChanged();
				}
			} else {// Otherwise try to put it in input slot
				if (!this.mergeItemStack(fromStack, 0, 1, false)) {
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