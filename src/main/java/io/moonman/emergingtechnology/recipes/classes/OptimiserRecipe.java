package io.moonman.emergingtechnology.recipes.classes;

import net.minecraft.item.ItemStack;

public class OptimiserRecipe extends SimpleRecipe {

    private final int cores;
    
	public OptimiserRecipe(ItemStack input, int cores) {
        super(ItemStack.EMPTY, input);
        this.cores = cores;
	}

	public int getCores() {
        return this.cores;
	}
}