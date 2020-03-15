package io.moonman.emergingtechnology.machines.classes.container;

import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public class MachineContainer extends Container {

	protected final MachineTileBase tileEntity;
	protected final int MACHINE_SLOT_COUNT;
	protected final int MAX_SLOT_COUNT;

	public MachineContainer(MachineTileBase tileEntity, InventoryPlayer player, IItemHandler handler, int slotCount, int maxInputSlot) {
		addMachineSlots(handler);
		addPlayerSlots(player);

		this.tileEntity = tileEntity;

		this.MACHINE_SLOT_COUNT = slotCount;
		this.MAX_SLOT_COUNT = slotCount;
	}

	public void addMachineSlots(IItemHandler handler) {

	}

	public void addPlayerSlots(InventoryPlayer player) {
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
	public void updateProgressBar(int id, int data) {
		this.tileEntity.setField(EnumTileField.getById(id), data);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileEntity.isUsableByPlayer(player);
	}

	public void checkField(IContainerListener listener, EnumTileField field, int value) {
		if (value != this.tileEntity.getField(field)) {
			listener.sendWindowProperty(this, EnumTileField.getId(field),
					this.tileEntity.getField(field));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {

		ItemStack stack = ItemStack.EMPTY;
		Slot fromSlot = (Slot) this.inventorySlots.get(index);

		if (fromSlot != null && fromSlot.getHasStack()) {
			ItemStack fromStack = fromSlot.getStack();
			stack = fromStack.copy();

			// If it's from the machine, put in player's inventory
			if (index < MACHINE_SLOT_COUNT) {
				if (!this.mergeItemStack(fromStack, MACHINE_SLOT_COUNT, MACHINE_SLOT_COUNT + 36, false)) {
					return ItemStack.EMPTY;
				} else {
					fromSlot.onSlotChanged();
				}
			} else {// Otherwise try to put it in input slot(s)
				if (!mergeItemStack(fromStack, 0, MAX_SLOT_COUNT, false)) {
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