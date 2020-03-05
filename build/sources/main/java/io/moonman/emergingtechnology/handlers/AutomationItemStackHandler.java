package io.moonman.emergingtechnology.handlers;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

/**
A customisable ItemStackHandler used in Emerging Technology
*/
public class AutomationItemStackHandler extends ItemStackHandler {

	private final ItemStackHandler mainHandler;
	private int validOutputSlot;
	private int validInputSlot;

	public AutomationItemStackHandler(ItemStackHandler hidden, int validInputSlot, int validOutputSlot) {
		super();
		mainHandler = hidden;

		this.validOutputSlot = validOutputSlot;
		this.validInputSlot = validInputSlot;
	}

	@Override
	public void setSize(int size) {
		NonNullList<ItemStack> list = NonNullList.create();
		stacks = list;
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		mainHandler.setStackInSlot(slot, stack);
	}

	@Override
	public int getSlots() {
		return mainHandler.getSlots();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return mainHandler.getStackInSlot(slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (slot != validInputSlot) {
			return stack;
		}

		return mainHandler.insertItem(slot, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (slot != validOutputSlot) {
			return ItemStack.EMPTY;
		}
		return mainHandler.extractItem(slot, amount, simulate);
	}


}