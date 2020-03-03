package io.moonman.emergingtechnology.integration.crafttweaker.machines;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import io.moonman.emergingtechnology.integration.crafttweaker.CraftTweakerHelper;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.machines.CookerRecipes;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.emergingtechnology.Cooker")
public class Cooker
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
			CookerRecipes.add(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding Cooker Recipe for "+ recipe.getOutput().getDisplayName();
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
			CookerRecipes.removeByOutput(this.output);
		}

		@Override
		public String describe()
		{
			return "Removing Cooker Recipe for "+ output.getDisplayName();
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
            CookerRecipes.removeAll();
		}

		@Override
		public String describe()
		{
			return "Removing all Cooker Recipes";
		}
	}
}