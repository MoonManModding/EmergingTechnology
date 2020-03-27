package io.moonman.emergingtechnology.integration.crafttweaker.machines;

import java.util.ArrayList;
import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import io.moonman.emergingtechnology.integration.crafttweaker.CraftTweakerHelper;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.classes.OptimiserRecipe;
import io.moonman.emergingtechnology.recipes.machines.OptimiserRecipes;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.emergingtechnology.Optimiser")
public class Optimiser
{
    @ZenMethod
	public static void addRecipe(Object input, int cores)
	{
		OptimiserRecipe recipe = CraftTweakerHelper.getOptimiserRecipe(input, cores);

		CraftTweakerAPI.apply(new Add(recipe));
	}

	private static class Add implements IAction
	{
		private final OptimiserRecipe recipe;

		public Add(OptimiserRecipe recipe)
		{
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			OptimiserRecipes.add(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding Optimiser Recipe for "+ recipe.getOutput().getDisplayName();
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
			OptimiserRecipes.removeByOutput(this.output);
		}

		@Override
		public String describe()
		{
			return "Removing Optimiser Recipe for "+ output.getDisplayName();
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
            OptimiserRecipes.removeAll();
		}

		@Override
		public String describe()
		{
			return "Removing all Optimiser Recipes";
		}
	}
}