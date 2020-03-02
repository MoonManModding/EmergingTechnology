package io.moonman.emergingtechnology.integration.crafttweaker.providers;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import io.moonman.emergingtechnology.integration.crafttweaker.CraftTweakerHelper;
import io.moonman.emergingtechnology.providers.ModBulbProvider;
import io.moonman.emergingtechnology.providers.classes.ModBulb;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.emergingtechnology.Bulbs")
public class Bulbs
{
    @ZenMethod
	public static void addBulb(Object input, int color, int energyUsage, int growthModifier, String[] plants, int boostModifier)
	{
		if (input == null) return;

		String itemName = input.toString();

		if (plants == null) plants = new String[0];

		CraftTweakerAPI.apply(new Add(itemName, color, energyUsage, growthModifier, plants, boostModifier));
	}

	private static class Add implements IAction
	{
		private final ModBulb bulb;

		public Add(String itemName, int color, int energyUsage, int growthModifier, String[] plants, int boostModifier)
		{
			this.bulb = new ModBulb(ModBulbProvider.getBulbs().length + 1, itemName, color, energyUsage, growthModifier, plants, boostModifier);
		}

		@Override
		public void apply()
		{
			ModBulbProvider.addBulb(this.bulb);
		}

		@Override
		public String describe()
		{
			return "Adding Bulb "+ bulb.name;
		}
	}

	@ZenMethod
	public static void remove(String name)
	{
		CraftTweakerAPI.apply(new Remove(name));
	}

	private static class Remove implements IAction
	{
		private final String name;

		public Remove(String name)
		{
			this.name = name;
		}

		@Override
		public void apply()
		{
			ModBulbProvider.removeBulbByItemName(name);
		}

		@Override
		public String describe()
		{
			return "Removing Bulb "+ name;
		}
	}

	// @ZenMethod
	// public static void removeAll()
	// {
	// 	CraftTweakerAPI.apply(new RemoveAll());
	// }

	// private static class RemoveAll implements IAction
	// {
	// 	//List<IMachineRecipe> removedRecipes = new ArrayList<IMachineRecipe>();

	// 	public RemoveAll(){
	// 	}

	// 	@Override
	// 	public void apply()
	// 	{
    //         //BulbRecipeBuilder.removeAll();
	// 	}

	// 	@Override
	// 	public String describe()
	// 	{
	// 		return "Removing all Bulbs";
	// 	}
	// }
}