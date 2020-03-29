package io.moonman.emergingtechnology.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

/**
A customisable ItemStackHandler used in Emerging Technology
*/
public class AutomationItemStackHandler extends ItemStackHandler {

	private final ItemStackHandler mainHandler;
	private List<Integer> validOutputSlots;
	private List<Integer> validInputSlots;

	public AutomationItemStackHandler(ItemStackHandler hidden, List<Integer> validInputSlots, List<Integer> validOutputSlots) {
		super();
		mainHandler = hidden;

		this.validOutputSlots = validOutputSlots;
		this.validInputSlots = validInputSlots;
	}

	public AutomationItemStackHandler(ItemStackHandler hidden, int validInputSlot, int validOutputSlot) {
		super();
		mainHandler = hidden;

		this.validOutputSlots = new ArrayList<Integer>();
		this.validInputSlots = new ArrayList<Integer>();

		this.validInputSlots.add(validInputSlot);
		this.validOutputSlots.add(validOutputSlot);
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
		if (!validInputSlots.stream().anyMatch(x -> x == slot)) {
			return stack;
		}

		return mainHandler.insertItem(slot, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (!validOutputSlots.stream().anyMatch(x -> x == slot)) {
			return ItemStack.EMPTY;
		}
		return mainHandler.extractItem(slot, amount, simulate);
	}
}