package io.moonman.emergingtechnology.recipes.classes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SimpleRecipe implements IMachineRecipe {

	private final ItemStack output;
	private final ItemStack input;
	private final String oreDictName;

	public SimpleRecipe(ItemStack output, ItemStack input) {
		this.output = output;
		this.input = input;
		this.oreDictName = null;
	}

	public SimpleRecipe(Item output, Item input) {
		this.output = new ItemStack(output);
		this.input = new ItemStack(input);
		this.oreDictName = null;
	}

	public SimpleRecipe(ItemStack output, String input) {
		this.output = output;
		this.input = ItemStack.EMPTY;
		this.oreDictName = input;
	}

	public ItemStack getInput() {
		return input;
	}

	public ItemStack getOutput() {
		return output;
	}

	public String getInputOreName() {
		return this.oreDictName;
	}

	public boolean hasOreDictInput() {
		return oreDictName != null;
	}

	public int getInputCount() {
		int count = this.input.getCount();

		return count > 0 ? count : 1;
	}

	public int getOutputCount() {
		int count = this.output.getCount();

		return count > 0 ? count : 1;
	}
}