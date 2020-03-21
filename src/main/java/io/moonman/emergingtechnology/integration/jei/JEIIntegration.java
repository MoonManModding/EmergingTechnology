package io.moonman.emergingtechnology.integration.jei;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.integration.jei.machines.MachineReference;
import io.moonman.emergingtechnology.integration.jei.machines.algaebioreactor.AlgaeBioreactorCategory;
import io.moonman.emergingtechnology.integration.jei.machines.algaebioreactor.AlgaeBioreactorRecipeWrapper;
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
import io.moonman.emergingtechnology.integration.jei.machines.hydroponic.HydroponicCategory;
import io.moonman.emergingtechnology.integration.jei.machines.hydroponic.HydroponicRecipeWrapper;
import io.moonman.emergingtechnology.integration.jei.machines.injector.InjectorCategory;
import io.moonman.emergingtechnology.integration.jei.machines.injector.InjectorRecipeWrapper;
import io.moonman.emergingtechnology.integration.jei.machines.light.LightCategory;
import io.moonman.emergingtechnology.integration.jei.machines.light.LightRecipeWrapper;
import io.moonman.emergingtechnology.integration.jei.machines.processor.ProcessorCategory;
import io.moonman.emergingtechnology.integration.jei.machines.processor.ProcessorRecipeWrapper;
import io.moonman.emergingtechnology.integration.jei.machines.scaffolder.ScaffolderCategory;
import io.moonman.emergingtechnology.integration.jei.machines.scaffolder.ScaffolderRecipeWrapper;
import io.moonman.emergingtechnology.integration.jei.machines.scrubber.ScrubberCategory;
import io.moonman.emergingtechnology.integration.jei.machines.scrubber.ScrubberRecipeWrapper;
import io.moonman.emergingtechnology.integration.jei.machines.shredder.ShredderCategory;
import io.moonman.emergingtechnology.integration.jei.machines.shredder.ShredderRecipeWrapper;
import io.moonman.emergingtechnology.machines.algaebioreactor.AlgaeBioreactorGui;
import io.moonman.emergingtechnology.machines.biomass.BiomassGeneratorGui;
import io.moonman.emergingtechnology.machines.bioreactor.BioreactorGui;
import io.moonman.emergingtechnology.machines.collector.CollectorGui;
import io.moonman.emergingtechnology.machines.cooker.CookerGui;
import io.moonman.emergingtechnology.machines.fabricator.FabricatorGui;
import io.moonman.emergingtechnology.machines.hydroponic.HydroponicGui;
import io.moonman.emergingtechnology.machines.injector.InjectorGui;
import io.moonman.emergingtechnology.machines.light.LightGui;
import io.moonman.emergingtechnology.machines.processor.ProcessorGui;
import io.moonman.emergingtechnology.machines.scaffolder.ScaffolderGui;
import io.moonman.emergingtechnology.machines.scrubber.ScrubberGui;
import io.moonman.emergingtechnology.machines.shredder.ShredderGui;
import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import io.moonman.emergingtechnology.recipes.machines.AlgaeBioreactorRecipes;
import io.moonman.emergingtechnology.recipes.machines.BiomassRecipes;
import io.moonman.emergingtechnology.recipes.machines.BioreactorRecipes;
import io.moonman.emergingtechnology.recipes.machines.CollectorRecipes;
import io.moonman.emergingtechnology.recipes.machines.CookerRecipes;
import io.moonman.emergingtechnology.recipes.machines.FabricatorRecipes;
import io.moonman.emergingtechnology.recipes.machines.HydroponicRecipes;
import io.moonman.emergingtechnology.recipes.machines.InjectorRecipes;
import io.moonman.emergingtechnology.recipes.machines.LightRecipes;
import io.moonman.emergingtechnology.recipes.machines.ProcessorRecipes;
import io.moonman.emergingtechnology.recipes.machines.ScaffolderRecipes;
import io.moonman.emergingtechnology.recipes.machines.ScrubberRecipes;
import io.moonman.emergingtechnology.recipes.machines.ShredderRecipes;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
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
                new ScaffolderCategory(helper), new CollectorCategory(helper), new BiomassCategory(helper),
                new ScrubberCategory(helper), new AlgaeBioreactorCategory(helper), new InjectorCategory(helper),
                new LightCategory(helper), new HydroponicCategory(helper));
    }

    @Override
    public void register(IModRegistry registry) {
        EmergingTechnology.logger.info("Registering with JEI...");

        helpers = registry.getJeiHelpers();

        registry.handleRecipes(SimpleRecipe.class, ProcessorRecipeWrapper::new, MachineReference.PROCESSOR_UID);
        registry.addRecipes(ProcessorRecipes.getRecipes(), MachineReference.PROCESSOR_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.processor), MachineReference.PROCESSOR_UID);
        registry.addRecipeClickArea(ProcessorGui.class, 39, 38, 34, 10, MachineReference.PROCESSOR_UID);

        registry.handleRecipes(SimpleRecipe.class, ShredderRecipeWrapper::new, MachineReference.SHREDDER_UID);
        registry.addRecipes(ShredderRecipes.getRecipes(), MachineReference.SHREDDER_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.shredder), MachineReference.SHREDDER_UID);
        registry.addRecipeClickArea(ShredderGui.class, 39, 38, 34, 10, MachineReference.SHREDDER_UID);

        registry.handleRecipes(SimpleRecipe.class, CookerRecipeWrapper::new, MachineReference.COOKER_UID);
        registry.addRecipes(CookerRecipes.getRecipes(), MachineReference.COOKER_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.cooker), MachineReference.COOKER_UID);
        registry.addRecipeClickArea(CookerGui.class, 39, 38, 34, 10, MachineReference.COOKER_UID);

        registry.handleRecipes(FabricatorRecipe.class, FabricatorRecipeWrapper::new, MachineReference.FABRICATOR_UID);
        registry.addRecipes(FabricatorRecipes.getRecipes(), MachineReference.FABRICATOR_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.fabricator), MachineReference.FABRICATOR_UID);
        registry.addRecipeClickArea(FabricatorGui.class, 39, 38, 34, 10, MachineReference.FABRICATOR_UID);

        registry.handleRecipes(SimpleRecipe.class, BioreactorRecipeWrapper::new, MachineReference.BIOREACTOR_UID);
        registry.addRecipes(BioreactorRecipes.getRecipes(), MachineReference.BIOREACTOR_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.bioreactor), MachineReference.BIOREACTOR_UID);
        registry.addRecipeClickArea(BioreactorGui.class, 39, 38, 34, 10, MachineReference.BIOREACTOR_UID);

        registry.handleRecipes(SimpleRecipe.class, ScaffolderRecipeWrapper::new, MachineReference.SCAFFOLDER_UID);
        registry.addRecipes(ScaffolderRecipes.getRecipes(), MachineReference.SCAFFOLDER_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.scaffolder), MachineReference.SCAFFOLDER_UID);
        registry.addRecipeClickArea(ScaffolderGui.class, 39, 38, 34, 10, MachineReference.SCAFFOLDER_UID);

        registry.handleRecipes(SimpleRecipe.class, CollectorRecipeWrapper::new, MachineReference.COLLECTOR_UID);
        registry.addRecipes(CollectorRecipes.getRecipes(), MachineReference.COLLECTOR_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.collector), MachineReference.COLLECTOR_UID);
        registry.addRecipeClickArea(CollectorGui.class, 39, 38, 34, 10, MachineReference.COLLECTOR_UID);

        registry.handleRecipes(SimpleRecipe.class, BiomassRecipeWrapper::new, MachineReference.BIOMASS_UID);
        registry.addRecipes(BiomassRecipes.getRecipes(), MachineReference.BIOMASS_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.biomassgenerator), MachineReference.BIOMASS_UID);
        registry.addRecipeClickArea(BiomassGeneratorGui.class, 39, 38, 34, 10, MachineReference.BIOMASS_UID);

        registry.handleRecipes(SimpleRecipe.class, ScrubberRecipeWrapper::new, MachineReference.SCRUBBER_UID);
        registry.addRecipes(ScrubberRecipes.getRecipes(), MachineReference.SCRUBBER_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.scrubber), MachineReference.SCRUBBER_UID);
        registry.addRecipeClickArea(ScrubberGui.class, 39, 38, 34, 10, MachineReference.SCRUBBER_UID);

        registry.handleRecipes(SimpleRecipe.class, AlgaeBioreactorRecipeWrapper::new,
                MachineReference.ALGAEBIOREACTOR_UID);
        registry.addRecipes(AlgaeBioreactorRecipes.getRecipes(), MachineReference.ALGAEBIOREACTOR_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.algaebioreactor), MachineReference.ALGAEBIOREACTOR_UID);
        registry.addRecipeClickArea(AlgaeBioreactorGui.class, 39, 38, 34, 10, MachineReference.ALGAEBIOREACTOR_UID);

        registry.handleRecipes(SimpleRecipe.class, InjectorRecipeWrapper::new, MachineReference.INJECTOR_UID);
        registry.addRecipes(InjectorRecipes.getRecipes(), MachineReference.INJECTOR_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.injector), MachineReference.INJECTOR_UID);
        registry.addRecipeClickArea(InjectorGui.class, 39, 38, 34, 10, MachineReference.INJECTOR_UID);

        registry.handleRecipes(SimpleRecipe.class, HydroponicRecipeWrapper::new, MachineReference.HYDROPONIC_UID);
        registry.addRecipes(HydroponicRecipes.getRecipes(), MachineReference.HYDROPONIC_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.hydroponic), MachineReference.HYDROPONIC_UID);
        registry.addRecipeClickArea(HydroponicGui.class, 39, 38, 34, 10, MachineReference.HYDROPONIC_UID);

        registry.handleRecipes(SimpleRecipe.class, LightRecipeWrapper::new, MachineReference.LIGHT_UID);
        registry.addRecipes(LightRecipes.getRecipes(), MachineReference.LIGHT_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.light), MachineReference.LIGHT_UID);
        registry.addRecipeClickArea(LightGui.class, 39, 38, 34, 10, MachineReference.LIGHT_UID);

        EmergingTechnology.logger.info("Successfully registered with JEI.");
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