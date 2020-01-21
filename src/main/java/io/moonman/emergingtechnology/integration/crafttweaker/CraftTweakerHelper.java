package io.moonman.emergingtechnology.integration.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientStack;
import crafttweaker.api.oredict.IOreDictEntry;
import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Biomass;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Bioreactor;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Cooker;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Fabricator;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Processor;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Scaffolder;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Shredder;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

// Gratefully borrowed from BluSunrize's ImmersiveEngineering. Thanks folks!

public class CraftTweakerHelper {
	public static void preInit() {
		EmergingTechnology.logger.info("CraftTweaker initialising...");
		CraftTweakerAPI.registerClass(Biomass.class);
		CraftTweakerAPI.registerClass(Bioreactor.class);
		CraftTweakerAPI.registerClass(Cooker.class);
		CraftTweakerAPI.registerClass(Fabricator.class);
		CraftTweakerAPI.registerClass(Processor.class);
		CraftTweakerAPI.registerClass(Scaffolder.class);
		CraftTweakerAPI.registerClass(Shredder.class);
		EmergingTechnology.logger.info("CraftTweaker initialised.");
	}

	public void registerRecipes() {
	}

	public void init() {
	}

	public void postInit() {
	}

	public static IMachineRecipe getMachineRecipe(IItemStack output, Object input) {
		if (input instanceof String) {
			return new SimpleRecipe(CraftTweakerHelper.toStack(output), (String) input);
		} else {
			return new SimpleRecipe(CraftTweakerHelper.toStack(output), CraftTweakerHelper.toStack((IItemStack)input));
		}
	}

	public static FabricatorRecipe getFabricatorRecipe(IItemStack output, Object input, int count) {
		if (input instanceof String) {
			return new FabricatorRecipe(RecipeProvider.fabricatorRecipes.size() + 1, CraftTweakerHelper.toStack(output), (String) input, count);
		} else {
			return new FabricatorRecipe(RecipeProvider.fabricatorRecipes.size() + 1, CraftTweakerHelper.toStack(output), CraftTweakerHelper.toStack((IItemStack)input));
		}
	}

	/**
	 * Helper Methods
	 */
	public static ItemStack toStack(IItemStack iStack) {
		if (iStack == null)
			return ItemStack.EMPTY;
		return (ItemStack) iStack.getInternal();
	}

	public static Object toObject(IIngredient iStack) {
		if (iStack == null)
			return null;
		else {
			if (iStack instanceof IOreDictEntry)
				return ((IOreDictEntry) iStack).getName();
			else if (iStack instanceof IItemStack)
				return toStack((IItemStack) iStack);
			else if (iStack instanceof IngredientStack) {
				IIngredient ingredient = ReflectionHelper.getPrivateValue(IngredientStack.class,
						(IngredientStack) iStack, "ingredient");
				Object o = toObject(ingredient);

				return o;
			} else
				return null;
		}
	}
}