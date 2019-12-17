package io.moonman.emergingtechnology.recipes.classes;

import net.minecraft.item.ItemStack;

public class SimpleRecipe {

	private final ItemStack output;
	private final ItemStack input;

	public SimpleRecipe(ItemStack output, ItemStack input) {
		this.output = output;
		this.input = input;
	}

	public ItemStack getInput() {
		return input;
	}

	public ItemStack getOutput() {
		return output;
	}
}