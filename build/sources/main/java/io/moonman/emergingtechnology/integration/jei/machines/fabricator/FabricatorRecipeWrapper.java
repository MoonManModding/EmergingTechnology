package io.moonman.emergingtechnology.integration.jei.machines.fabricator;

import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class FabricatorRecipeWrapper implements IRecipeWrapper {

    private ItemStack input;
    private ItemStack output;
    private int cost;

    public FabricatorRecipeWrapper(FabricatorRecipe recipe)
    {
        this.input = recipe.getInput();
        this.output = recipe.getOutput();
        this.cost = recipe.cost;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        ingredients.setInput(VanillaTypes.ITEM, input);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }

    public int getCost() {
        return this.cost;
    }
}