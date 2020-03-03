package io.moonman.emergingtechnology.integration.crafttweaker.machines;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import io.moonman.emergingtechnology.integration.crafttweaker.CraftTweakerHelper;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.machines.AlgaeBioreactorRecipes;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.emergingtechnology.AlgaeBioreactor")
public class AlgaeBioreactor
{
    @ZenMethod
	public static void addRecipe(IItemStack output, Object input)
	{
		IMachineRecipe recipe = CraftTweakerHelper.getMachineRecipe(output, input);

		CraftTweakerAPI.apply(new Add(recipe));
	}

	private static class Add implements IAction
	{
		private final IMachineRecipe recipe;

		public Add(IMachineRecipe recipe)
		{
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			RecipeProvider.algaeBioreactorRecipes.add(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding Algae Bioreactor Recipe for "+ recipe.getOutput().getDisplayName();
		}
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		CraftTweakerAPI.apply(new Remove(CraftTweakerHelper.toStack(output)));
	}

	private static class Remove implements IAction
	{
		private final ItemStack output;
		List<IMachineRecipe> removedRecipes = new ArrayList<IMachineRecipe>();

		public Remove(ItemStack output)
		{
			this.output = output;
		}

		@Override
		public void apply()
		{
			AlgaeBioreactorRecipes.removeByOutput(this.output);
		}

		@Override
		public String describe()
		{
			return "Removing Algae Bioreactor Recipe for "+ output.getDisplayName();
		}
	}

	@ZenMethod
	public static void removeAll()
	{
		CraftTweakerAPI.apply(new RemoveAll());
	}

	private static class RemoveAll implements IAction
	{
		List<IMachineRecipe> removedRecipes = new ArrayList<IMachineRecipe>();

		public RemoveAll(){
		}

		@Override
		public void apply()
		{
            AlgaeBioreactorRecipes.removeAll();
		}

		@Override
		public String describe()
		{
			return "Removing all Algae Bioreactor Recipes";
		}
	}
}