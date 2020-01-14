package io.moonman.emergingtechnology.recipes.classes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SimpleRecipe {

	private final ItemStack output;
	private final ItemStack input;

	public SimpleRecipe(ItemStack output, ItemStack input) {
		this.output = output;
		this.input = input;
	}

	public SimpleRecipe(Item output, Item input) {
		this.output = new ItemStack(output);
		this.input = new ItemStack(input);
	}

	public ItemStack getInput() {
		return input;
	}

	public ItemStack getOutput() {
		return output;
	}
}