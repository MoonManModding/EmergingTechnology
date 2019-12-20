package io.moonman.emergingtechnology.recipes.classes;

import net.minecraft.item.ItemStack;

public class FabricatorRecipe {

	private final ItemStack output;
	private final ItemStack input;
	
	public final int id;
	public final int cost;

	public FabricatorRecipe(int id, ItemStack output, ItemStack input, int cost) {
		this.output = output;
		this.input = input;
		this.id = id;
		this.cost = cost;
	}

	public ItemStack getInput() {
		return input;
	}

	public ItemStack getOutput() {
		return output;
	}
}