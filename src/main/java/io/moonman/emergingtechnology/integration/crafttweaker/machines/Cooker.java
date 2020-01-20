package io.moonman.emergingtechnology.integration.crafttweaker.machines;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import io.moonman.emergingtechnology.integration.crafttweaker.CraftTweakerHelper;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.emergingtechnology.Cooker")
public class Cooker
{
    @ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input)
	{
		SimpleRecipe r = new SimpleRecipe(CraftTweakerHelper.toStack(output), CraftTweakerHelper.toStack(input));
		CraftTweakerAPI.apply(new Add(r));
	}

	private static class Add implements IAction
	{
		private final SimpleRecipe recipe;

		public Add(SimpleRecipe recipe)
		{
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			RecipeProvider.cookerRecipes.add(recipe);
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
		List<SimpleRecipe> removedRecipes;

		public Remove(ItemStack output)
		{
			this.output = output;
		}

		@Override
		public void apply()
		{
			removedRecipes = RecipeProvider.removeRecipesByOutput(RecipeProvider.cookerRecipes, output);
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
		List<SimpleRecipe> removedRecipes;

		public RemoveAll(){
		}

		@Override
		public void apply()
		{
            removedRecipes = new ArrayList<>(RecipeProvider.cookerRecipes);
            RecipeProvider.cookerRecipes = new ArrayList<SimpleRecipe>();
		}

		@Override
		public String describe()
		{
			return "Removing all Cooker Recipes";
		}
	}
}