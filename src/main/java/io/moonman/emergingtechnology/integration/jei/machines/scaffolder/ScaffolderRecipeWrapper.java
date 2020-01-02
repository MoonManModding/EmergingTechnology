package io.moonman.emergingtechnology.integration.jei.machines.scaffolder;

import java.util.ArrayList;

import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class ScaffolderRecipeWrapper implements IRecipeWrapper {

    private ItemStack input;
    private ItemStack output;
    private ItemStack scaffoldItem = new ItemStack(ModItems.plastictissuescaffold);

    public ScaffolderRecipeWrapper(SimpleRecipe recipe)
    {
        input = recipe.getInput();
        output = recipe.getOutput();
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();

        inputs.add(input);
        inputs.add(scaffoldItem);

        ingredients.setInputs(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }
}