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