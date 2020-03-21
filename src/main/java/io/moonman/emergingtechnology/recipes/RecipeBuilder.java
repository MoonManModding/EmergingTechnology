package io.moonman.emergingtechnology.recipes;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.classes.EmptyRecipe;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
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
import net.minecraft.block.Block;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryModifiable;

/**
 * Builds recipes for Emerging Technology. Used by machines and JEI if present
 */
public class RecipeBuilder {

    public static void buildMachineRecipes() {

        BioreactorRecipes.build();
        CollectorRecipes.build();
        CookerRecipes.build();
        ShredderRecipes.build();
        ScaffolderRecipes.build();
        ProcessorRecipes.build();
        FabricatorRecipes.build();
        BiomassRecipes.build();
        ScrubberRecipes.build();
        AlgaeBioreactorRecipes.build();
        InjectorRecipes.build();
        HydroponicRecipes.build();
        LightRecipes.build();

        registerFurnaceRecipes();
    }

    private static void registerFurnaceRecipes() {
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModBlocks.plasticblock),
                new ItemStack(ModItems.filament), 0.1f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.shreddedplant),
                new ItemStack(ModItems.biomass), 0.1f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.shreddedstarch),
                new ItemStack(ModItems.biomass), 0.1f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.algae), new ItemStack(ModItems.biomass),
                0.1f);

        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.syntheticchickenraw),
                new ItemStack(ModItems.syntheticchickencooked), 0.2f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.syntheticcowraw),
                new ItemStack(ModItems.syntheticcowcooked), 0.2f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.syntheticpigraw),
                new ItemStack(ModItems.syntheticpigcooked), 0.2f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.algaebarraw),
                new ItemStack(ModItems.algaebarcooked), 0.2f);
    }

    public static List<ItemStack> buildRecipeList(List<ItemStack> itemStacks, List<String> oreNames) {
        List<ItemStack> result = new ArrayList<ItemStack>();

        for (ItemStack itemStack : itemStacks) {
            result.add(itemStack);
        }

        for (String oreName : oreNames) {
            List<ItemStack> oreItemStacks = OreDictionary.getOres(oreName);
            for (ItemStack itemStack : oreItemStacks) {
                result.add(itemStack);
            }
        }

        return result;
    }

    public static ItemStack getFabricatorOutputForItemStack(ItemStack itemStack) {
        return getOutputForItemStackFromRecipes(itemStack, FabricatorRecipes.getRecipes());
    }

    public static ItemStack getOutputForItemStackFromRecipes(ItemStack itemStack, List<IMachineRecipe> recipes) {

        IMachineRecipe recipe = getMatchingRecipe(itemStack, recipes);

        if (recipe != null) {
            return recipe.getOutput();
        }

        return null;
    }

    public static IMachineRecipe getMatchingRecipe(ItemStack itemStack, List<IMachineRecipe> recipes) {

        if (itemStack == null || itemStack.isEmpty())
            return null;

        int[] oreIds = OreDictionary.getOreIDs(itemStack);

        for (IMachineRecipe recipe : recipes) {

            if (!recipe.hasOreDictInput()) {
                if (recipe.getInput().isItemEqual(itemStack)) {
                    return recipe;
                }
            } else {

                int oreId = OreDictionary.getOreID(recipe.getInputOreName());

                for (int id : oreIds) {
                    if (id == oreId) {
                        return recipe;
                    }
                }
            }
        }
        return null;
    }

    public static IMachineRecipe getFabricatorRecipeByIndex(int index) {
        return FabricatorRecipes.getRecipes().get(index);
    }

    public static List<List<IMachineRecipe>> getSplitRecipes(int slots) {
        return splitList(FabricatorRecipes.getRecipes(), slots);
    }

    public static int getRecipePagesCount(int slots) {
        return getSplitRecipes(slots).size();
    }

    public static ItemStack getCookerOutputForItemStack(ItemStack itemStack) {
        ItemStack resultStack = FurnaceRecipes.instance().getSmeltingResult(itemStack);

        if (resultStack == null) {
            return null;
        }

        if (resultStack.getItem() instanceof ItemFood) {
            return resultStack;
        }

        return null;
    }

    public static List<IMachineRecipe> removeRecipesByOutput(List<IMachineRecipe> recipeList, ItemStack outputStack) {

        List<IMachineRecipe> removedRecipes = new ArrayList<IMachineRecipe>();

        recipeList.removeIf(x -> {
            boolean match = StackHelper.compareItemStacks(x.getOutput(), outputStack);
            removedRecipes.add(x);
            return match;
        });

        return removedRecipes;
    }

    private static <T> List<List<T>> splitList(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(list.subList(i, Math.min(N, i + L))));
        }
        return parts;
    }

    /**
     * Removes recipes for Emerging Technology based on configuration file
     */
    public static void removeRecipes(IForgeRegistryModifiable<IRecipe> registry) {

        ArrayList<Block> disabledBlocks = new ArrayList<Block>();

        if (EmergingTechnologyConfig.ELECTRICS_MODULE.SOLAR.disabled)
            disabledBlocks.add(ModBlocks.solar);
        if (EmergingTechnologyConfig.ELECTRICS_MODULE.SOLARGLASS.disabled)
            disabledBlocks.add(ModBlocks.solarglass);
        if (EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.disabled)
            disabledBlocks.add(ModBlocks.wind);
        if (EmergingTechnologyConfig.ELECTRICS_MODULE.BATTERY.disabled)
            disabledBlocks.add(ModBlocks.battery);
        if (EmergingTechnologyConfig.ELECTRICS_MODULE.TIDALGENERATOR.disabled)
            disabledBlocks.add(ModBlocks.tidalgenerator);
        if (EmergingTechnologyConfig.ELECTRICS_MODULE.PIEZOELECTRIC.disabled)
            disabledBlocks.add(ModBlocks.piezoelectric);
        if (EmergingTechnologyConfig.ELECTRICS_MODULE.BIOMASSGENERATOR.disabled)
            disabledBlocks.add(ModBlocks.biomassgenerator);
        if (EmergingTechnologyConfig.ELECTRICS_MODULE.OPTIMISER.disabled)
            disabledBlocks.add(ModBlocks.optimiser);

        if (EmergingTechnologyConfig.POLYMERS_MODULE.SHREDDER.disabled)
            disabledBlocks.add(ModBlocks.shredder);
        if (EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.disabled)
            disabledBlocks.add(ModBlocks.processor);
        if (EmergingTechnologyConfig.POLYMERS_MODULE.COLLECTOR.disabled)
            disabledBlocks.add(ModBlocks.collector);
        if (EmergingTechnologyConfig.POLYMERS_MODULE.FABRICATOR.disabled)
            disabledBlocks.add(ModBlocks.fabricator);

        if (EmergingTechnologyConfig.SYNTHETICS_MODULE.COOKER.disabled)
            disabledBlocks.add(ModBlocks.cooker);
        if (EmergingTechnologyConfig.SYNTHETICS_MODULE.SCAFFOLDER.disabled)
            disabledBlocks.add(ModBlocks.scaffolder);
        if (EmergingTechnologyConfig.SYNTHETICS_MODULE.BIOREACTOR.disabled)
            disabledBlocks.add(ModBlocks.bioreactor);
        if (EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.disabled)
            disabledBlocks.add(ModBlocks.algaebioreactor);

        if (EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.disabled)
            disabledBlocks.add(ModBlocks.cooker);
        if (EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.disabled)
            disabledBlocks.add(ModBlocks.light);
        if (EmergingTechnologyConfig.HYDROPONICS_MODULE.HARVESTER.disabled)
            disabledBlocks.add(ModBlocks.harvester);
        if (EmergingTechnologyConfig.HYDROPONICS_MODULE.FILLER.disabled)
            disabledBlocks.add(ModBlocks.filler);
        if (EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.disabled)
            disabledBlocks.add(ModBlocks.scrubber);
        if (EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.disabled)
            disabledBlocks.add(ModBlocks.diffuser);
        if (EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.disabled)
            disabledBlocks.add(ModBlocks.injector);

        for (Block block : disabledBlocks) {
            removeBlockRecipe(registry, block);
        }
    }

    private static void removeBlockRecipe(IForgeRegistryModifiable<IRecipe> registry, Block block) {

        block.setCreativeTab(null);

        IRecipe p = (IRecipe) registry.getValue(block.getRegistryName());
        registry.remove(block.getRegistryName());
        registry.register(new EmptyRecipe(p));
    }
}