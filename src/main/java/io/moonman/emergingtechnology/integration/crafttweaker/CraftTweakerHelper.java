package io.moonman.emergingtechnology.integration.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.AlgaeBioreactor;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Biomass;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Bioreactor;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Collector;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Cooker;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Fabricator;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Injector;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Optimiser;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Processor;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Scaffolder;
import io.moonman.emergingtechnology.integration.crafttweaker.machines.Shredder;
import io.moonman.emergingtechnology.integration.crafttweaker.providers.Bulbs;
import io.moonman.emergingtechnology.integration.ModLoader;
import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.classes.OptimiserRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import io.moonman.emergingtechnology.recipes.machines.FabricatorRecipes;
import net.minecraft.item.ItemStack;

// Gratefully borrowed from BluSunrize's ImmersiveEngineering. Thanks folks!

public class CraftTweakerHelper {
	public static void preInit() {

		if (!ModLoader.isCraftTweakerLoaded()) {
			EmergingTechnology.logger.info("CraftTweaker not found. Skipping...");
			return;
		}

		EmergingTechnology.logger.info("CraftTweaker initialising...");
		CraftTweakerAPI.registerClass(Biomass.class);
		CraftTweakerAPI.registerClass(Bioreactor.class);
		CraftTweakerAPI.registerClass(AlgaeBioreactor.class);
		CraftTweakerAPI.registerClass(Cooker.class);
		CraftTweakerAPI.registerClass(Fabricator.class);
		CraftTweakerAPI.registerClass(Processor.class);
		CraftTweakerAPI.registerClass(Scaffolder.class);
		CraftTweakerAPI.registerClass(Shredder.class);
		CraftTweakerAPI.registerClass(Injector.class);
		CraftTweakerAPI.registerClass(Optimiser.class);
		CraftTweakerAPI.registerClass(Collector.class);

		CraftTweakerAPI.registerClass(Bulbs.class);
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
			return new FabricatorRecipe(FabricatorRecipes.getRecipes().size() + 1, CraftTweakerHelper.toStack(output), (String) input, count);
		} else {
			return new FabricatorRecipe(FabricatorRecipes.getRecipes().size() + 1, CraftTweakerHelper.toStack(output), CraftTweakerHelper.toStack((IItemStack)input));
		}
	}

	public static OptimiserRecipe getOptimiserRecipe(Object input, int cores) {
			return new OptimiserRecipe(CraftTweakerHelper.toStack((IItemStack)input), cores);
	}

	/**
	 * Helper Methods
	 */
	public static ItemStack toStack(IItemStack iStack) {
		if (iStack == null)
			return ItemStack.EMPTY;
		return (ItemStack) iStack.getInternal();
	}
}