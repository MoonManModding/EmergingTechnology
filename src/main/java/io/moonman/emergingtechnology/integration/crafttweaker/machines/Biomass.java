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

@ZenClass("mods.emergingtechnology.Biomass")
public class Biomass
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
			RecipeProvider.biomassRecipes.add(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding Biomass Recipe for "+ recipe.getOutput().getDisplayName();
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
			removedRecipes = RecipeProvider.removeRecipesByOutput(RecipeProvider.biomassRecipes, output);
		}

		@Override
		public String describe()
		{
			return "Removing Biomass Recipe for "+ output.getDisplayName();
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
            removedRecipes = new ArrayList<>(RecipeProvider.biomassRecipes);
            RecipeProvider.biomassRecipes = new ArrayList<SimpleRecipe>();
		}

		@Override
		public String describe()
		{
			return "Removing all Biomass Recipes";
		}
	}
}