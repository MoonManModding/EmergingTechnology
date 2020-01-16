package io.moonman.emergingtechnology.integration.jei;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.integration.jei.machines.MachineReference;
import io.moonman.emergingtechnology.integration.jei.machines.biomass.BiomassCategory;
import io.moonman.emergingtechnology.integration.jei.machines.biomass.BiomassRecipeWrapper;
import io.moonman.emergingtechnology.integration.jei.machines.bioreactor.BioreactorCategory;
import io.moonman.emergingtechnology.integration.jei.machines.bioreactor.BioreactorRecipeWrapper;
import io.moonman.emergingtechnology.integration.jei.machines.collector.CollectorCategory;
import io.moonman.emergingtechnology.integration.jei.machines.collector.CollectorRecipeWrapper;
import io.moonman.emergingtechnology.integration.jei.machines.cooker.CookerCategory;
import io.moonman.emergingtechnology.integration.jei.machines.cooker.CookerRecipeWrapper;
import io.moonman.emergingtechnology.integration.jei.machines.fabricator.FabricatorCategory;
import io.moonman.emergingtechnology.integration.jei.machines.fabricator.FabricatorRecipeWrapper;
import io.moonman.emergingtechnology.integration.jei.machines.processor.ProcessorCategory;
import io.moonman.emergingtechnology.integration.jei.machines.processor.ProcessorRecipeWrapper;
import io.moonman.emergingtechnology.integration.jei.machines.scaffolder.ScaffolderCategory;
import io.moonman.emergingtechnology.integration.jei.machines.scaffolder.ScaffolderRecipeWrapper;
import io.moonman.emergingtechnology.integration.jei.machines.shredder.ShredderCategory;
import io.moonman.emergingtechnology.integration.jei.machines.shredder.ShredderRecipeWrapper;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
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

        registry.addRecipeCategories(new ProcessorCategory(helper), new ShredderCategory(helper),
                new CookerCategory(helper), new FabricatorCategory(helper), new BioreactorCategory(helper),
                new ScaffolderCategory(helper), new CollectorCategory(helper), new BiomassCategory(helper));
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

        registry.handleRecipes(SimpleRecipe.class, CookerRecipeWrapper::new, MachineReference.COOKER_UID);
        registry.addRecipes(RecipeProvider.cookerRecipes, MachineReference.COOKER_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.cooker), MachineReference.COOKER_UID);

        registry.handleRecipes(FabricatorRecipe.class, FabricatorRecipeWrapper::new, MachineReference.FABRICATOR_UID);
        registry.addRecipes(RecipeProvider.fabricatorRecipes, MachineReference.FABRICATOR_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.fabricator), MachineReference.FABRICATOR_UID);

        registry.handleRecipes(SimpleRecipe.class, BioreactorRecipeWrapper::new, MachineReference.BIOREACTOR_UID);
        registry.addRecipes(RecipeProvider.bioreactorRecipes, MachineReference.BIOREACTOR_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.bioreactor), MachineReference.BIOREACTOR_UID);

        registry.handleRecipes(SimpleRecipe.class, ScaffolderRecipeWrapper::new, MachineReference.SCAFFOLDER_UID);
        registry.addRecipes(RecipeProvider.scaffolderRecipes, MachineReference.SCAFFOLDER_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.scaffolder), MachineReference.SCAFFOLDER_UID);

        registry.handleRecipes(SimpleRecipe.class, CollectorRecipeWrapper::new, MachineReference.COLLECTOR_UID);
        registry.addRecipes(RecipeProvider.collectorRecipes, MachineReference.COLLECTOR_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.collector), MachineReference.COLLECTOR_UID);

        registry.handleRecipes(SimpleRecipe.class, BiomassRecipeWrapper::new, MachineReference.BIOMASS_UID);
        registry.addRecipes(RecipeProvider.biomassRecipes, MachineReference.BIOMASS_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.biomassgenerator), MachineReference.BIOMASS_UID);

        EmergingTechnology.logger.info("Registered with JEI.");
    }

    public static boolean doesOreExist(String key) {
        return OreDictionary.doesOreNameExist(key)
                && OreDictionary.getOres(key).stream().anyMatch(s -> s.getItem() instanceof ItemBlock);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime iJeiRuntime) {
        runtime = iJeiRuntime;
    }
}