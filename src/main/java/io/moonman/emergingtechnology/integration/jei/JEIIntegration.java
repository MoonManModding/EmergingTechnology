package io.moonman.emergingtechnology.integration.jei;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.integration.jei.machines.MachineReference;
import io.moonman.emergingtechnology.integration.jei.machines.processor.ProcessorCategory;
import io.moonman.emergingtechnology.integration.jei.machines.processor.ProcessorRecipeWrapper;
import io.moonman.emergingtechnology.integration.jei.machines.shredder.ShredderCategory;
import io.moonman.emergingtechnology.integration.jei.machines.shredder.ShredderRecipeWrapper;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

// Thanks thebrightspark: https://github.com/thebrightspark/SparksHammers/tree/1.12/src/main/java/brightspark/sparkshammers/integration/jei

@JEIPlugin
public class JEIIntegration implements IModPlugin {

    public static IJeiHelpers helpers = null;
    public static IJeiRuntime runtime = null;

    public JEIIntegration() {
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {

        IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();

        registry.addRecipeCategories(new ProcessorCategory(helper), new ShredderCategory(helper));
    }

    @Override
    public void register(IModRegistry registry) {
        EmergingTechnology.logger.info("Registering with JEI...");

        helpers = registry.getJeiHelpers();

        registry.handleRecipes(SimpleRecipe.class, ProcessorRecipeWrapper::new, MachineReference.PROCESSOR_UID);
        registry.addRecipes(RecipeProvider.processorRecipes, MachineReference.PROCESSOR_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.processor), MachineReference.PROCESSOR_UID);

        registry.handleRecipes(SimpleRecipe.class, ShredderRecipeWrapper::new, MachineReference.SHREDDER_UID);
        registry.addRecipes(RecipeProvider.shredderRecipes, MachineReference.SHREDDER_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.shredder), MachineReference.SHREDDER_UID);
        EmergingTechnology.logger.info("Registed " + (RecipeProvider.shredderRecipes.size() + RecipeProvider.processorRecipes.size()) + " recipes.");
    }

    public static boolean doesOreExist(String key) {
		return OreDictionary.doesOreNameExist(key)
				&& OreDictionary.getOres(key).stream()
				.anyMatch(s -> s.getItem() instanceof ItemBlock);
	}

    @Override
    public void onRuntimeAvailable(IJeiRuntime iJeiRuntime) {
        runtime = iJeiRuntime;
    }
}