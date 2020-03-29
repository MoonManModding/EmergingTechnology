package io.moonman.emergingtechnology.integration.jei.machines.diffuser;

import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class DiffuserRecipeWrapper implements IRecipeWrapper {

    private ItemStack input;
    private ItemStack output;

    public DiffuserRecipeWrapper(SimpleRecipe recipe)
    {
        input = recipe.getInput();
        output = recipe.getOutput();
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        ingredients.setInput(VanillaTypes.ITEM, input);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }
}