package io.moonman.emergingtechnology.integration.jei.machines.shredder;

import java.util.List;

import io.moonman.emergingtechnology.integration.jei.JEIIntegration;
import io.moonman.emergingtechnology.recipes.machines.ShredderRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class ShredderRecipeWrapper implements IRecipeWrapper {

    private List<List<ItemStack>> input;
    private ItemStack output;

    public ShredderRecipeWrapper(ShredderRecipe recipe)
    {
        input = JEIIntegration.helpers.getStackHelper().expandRecipeItemStackInputs(recipe.getInputs());
        output = recipe.getOutput();
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        ingredients.setInputLists(VanillaTypes.ITEM, input);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }
}