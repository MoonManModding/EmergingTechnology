package io.moonman.emergingtechnology.recipes.classes;

import net.minecraft.item.ItemStack;

public class FabricatorRecipe implements IMachineRecipe {

	private final ItemStack output;
	private final ItemStack input;
	private final String oreDictName;
	
	public final int id;
	public final int cost;

	public FabricatorRecipe(int id, ItemStack output, ItemStack input) {
		this.output = output;
		this.input = input;
		this.id = id;
		this.cost = input.getCount();
		this.oreDictName = null;
	}

	public FabricatorRecipe(int id, ItemStack output, String input, int count) {
		this.output = output;
		this.input = ItemStack.EMPTY;
		this.id = id;
		this.cost = count;
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