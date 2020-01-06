package io.moonman.emergingtechnology.recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.moonman.emergingtechnology.helpers.custom.wrappers.CustomRecipeWrapper;
import io.moonman.emergingtechnology.helpers.machines.CookerHelper;
import io.moonman.emergingtechnology.helpers.machines.FabricatorHelper;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Builds recipes for Emerging Technology. Used by machines and JEI if present
 * TODO: Refactor/rework
 */
public class RecipeBuilder {

    public static void buildMachineRecipes() {
        buildProcessorRecipes();
        buildShredderRecipes();
        buildCookerRecipes();
        buildFabricatorRecipeList();
        buildBioreactorRecipes();
        buildScaffolderRecipes();
        buildCollectorRecipes();

        registerFurnaceRecipes();
    }

    private static void buildProcessorRecipes() {

        registerProcessorRecipes(new ItemStack(ModBlocks.plasticblock), getProcessorBlockItems());
        registerProcessorRecipes(new ItemStack(ModBlocks.clearplasticblock), getProcessorClearBlockItems());

    }

    private static void buildShredderRecipes() {

        registerShredderRecipes(new ItemStack(ModItems.shreddedplant), getShredderPlantItems());
        registerShredderRecipes(new ItemStack(ModItems.shreddedplastic), getShredderPlasticItems());
        registerShredderRecipes(new ItemStack(ModItems.shreddedstarch), getShredderStarchItems());
    }

    private static void buildCookerRecipes() {
        List<ItemStack> validCookedFoodItems = CookerHelper.getValidCookedFoodItems();
        registerCookerRecipes(validCookedFoodItems);
    }

    private static void buildBioreactorRecipes() {
        //TODO: Implement new method
        // RecipeProvider.bioreactorRecipes.add(createSimpleRecipe(ModItems.chickensyringe, ModItems.chickensample));
        // RecipeProvider.bioreactorRecipes.add(createSimpleRecipe(ModItems.horsesyringe, ModItems.horsesample));
        // RecipeProvider.bioreactorRecipes.add(createSimpleRecipe(ModItems.pigsyringe, ModItems.pigsample));
        // RecipeProvider.bioreactorRecipes.add(createSimpleRecipe(ModItems.cowsyringe, ModItems.cowsample));

        // FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.syntheticbeefraw), new ItemStack(Items.COOKED_BEEF), 0.1f);
        // FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.syntheticchickenraw), new ItemStack(Items.COOKED_CHICKEN), 0.1f);
        // FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.syntheticporkchopraw), new ItemStack(Items.COOKED_PORKCHOP), 0.1f);
    }

    private static void buildScaffolderRecipes() {
        //TODO: Implement new method
        // RecipeProvider.scaffolderRecipes.add(createSimpleRecipe(ModItems.chickensample, ModItems.syntheticchickenraw));
        // RecipeProvider.scaffolderRecipes.add(createSimpleRecipe(ModItems.horsesample, Items.LEATHER));
        // RecipeProvider.scaffolderRecipes.add(createSimpleRecipe(ModItems.pigsample, ModItems.syntheticporkchopraw));
        // RecipeProvider.scaffolderRecipes.add(createSimpleRecipe(ModItems.cowsample, ModItems.syntheticbeefraw));
    }

    private static void buildCollectorRecipes() {
        RecipeProvider.collectorRecipes.add(createSimpleRecipe(Items.AIR, ModItems.plasticwaste));
        RecipeProvider.collectorRecipes.add(createSimpleRecipe(Items.AIR, Items.PAPER));
    }

    private static void registerCookerRecipes(List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            ItemStack output = FurnaceRecipes.instance().getSmeltingResult(input);
            SimpleRecipe recipe = new SimpleRecipe(output, input);
            RecipeProvider.cookerRecipes.add(recipe);

        }
    }

    private static void registerProcessorRecipes(ItemStack output, List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            if (RecipeProvider.getOutputForItemStackFromRecipes(input, RecipeProvider.processorRecipes) == null) {
                SimpleRecipe recipe = new SimpleRecipe(output, input);
                RecipeProvider.processorRecipes.add(recipe);
            }
        }
    }

    private static void registerShredderRecipes(ItemStack output, List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            if (RecipeProvider.getOutputForItemStackFromRecipes(input, RecipeProvider.shredderRecipes) == null) {
                SimpleRecipe recipe = new SimpleRecipe(output, input);
                RecipeProvider.shredderRecipes.add(recipe);
            }
        }

        RecipeProvider.shredderRecipes = RecipeProvider.shredderRecipes.stream().distinct()
                .collect(Collectors.toList());
    }

    private static List<ItemStack> getProcessorBlockItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();
        itemInputs.add(new ItemStack(ModItems.shreddedplastic, 4));
        itemInputs.add(new ItemStack(ModItems.shreddedstarch, 9));
        itemInputs.add(new ItemStack(ModBlocks.shreddedplasticblock, 1));
        itemInputs.add(new ItemStack(ModBlocks.shreddedstarchblock, 1));

        List<String> oreInputs = new ArrayList<String>();
        // oreInputs.add("dustPlastic");
        // oreInputs.add("orePlastic");
        // oreInputs.add("starch");
        // oreInputs.add("dustStarch");

        return buildRecipeList(itemInputs, oreInputs);
    }

    private static List<ItemStack> getProcessorClearBlockItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();
        itemInputs.add(new ItemStack(ModItems.shreddedplant, 9));
        itemInputs.add(new ItemStack(ModBlocks.shreddedplantblock, 1));

        List<String> oreInputs = new ArrayList<String>();

        return buildRecipeList(itemInputs, oreInputs);
    }

    private static List<ItemStack> getShredderPlasticItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();

        itemInputs.add(new ItemStack(ModItems.frame));
        itemInputs.add(new ItemStack(ModItems.plasticsheet));
        itemInputs.add(new ItemStack(ModItems.plasticblock));
        itemInputs.add(new ItemStack(ModItems.clearplasticblock));
        itemInputs.add(new ItemStack(ModItems.plasticrod));
        itemInputs.add(new ItemStack(ModItems.light));
        itemInputs.add(new ItemStack(ModItems.hydroponic));
        itemInputs.add(new ItemStack(ModItems.machinecase));
        itemInputs.add(new ItemStack(ModItems.shredder));
        itemInputs.add(new ItemStack(ModItems.processor));
        itemInputs.add(new ItemStack(ModItems.redbulb));
        itemInputs.add(new ItemStack(ModItems.greenbulb));
        itemInputs.add(new ItemStack(ModItems.bluebulb));
        itemInputs.add(new ItemStack(ModItems.purplebulb));
        itemInputs.add(new ItemStack(ModItems.plasticwaste));

        List<String> oreInputs = new ArrayList<String>();

        oreInputs.add("sheetPlastic");
        oreInputs.add("rodPlastic");
        oreInputs.add("stickPlastic");
        oreInputs.add("platePlastic");
        oreInputs.add("blockPlastic");
        oreInputs.add("itemPlastic");
        oreInputs.add("bioplastic");

        List<ItemStack> inputs = buildRecipeList(itemInputs, oreInputs);

        return inputs;
    }

    private static List<ItemStack> getShredderPlantItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();

        itemInputs.add(new ItemStack(Items.REEDS));

        List<String> oreInputs = new ArrayList<String>();

        List<ItemStack> inputs = buildRecipeList(itemInputs, oreInputs);

        return inputs;
    }

    private static List<ItemStack> getShredderStarchItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();

        itemInputs.add(new ItemStack(Items.BEETROOT));
        itemInputs.add(new ItemStack(Items.POTATO));

        List<String> oreInputs = new ArrayList<String>();
        oreInputs.add("cropCorn");
        oreInputs.add("corn");

        List<ItemStack> inputs = buildRecipeList(itemInputs, oreInputs);

        return inputs;
    }

    private static void registerFurnaceRecipes() {
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModBlocks.plasticblock), new ItemStack(ModItems.filament), 0.1f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.shreddedplant), new ItemStack(ModItems.biomass), 0.1f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.shreddedstarch), new ItemStack(ModItems.biomass), 0.1f);
    }

    private static void buildFabricatorRecipeList() {

        FabricatorRecipe recipe = new FabricatorRecipe(1, new ItemStack(ModBlocks.plasticblock), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe2 = new FabricatorRecipe(2, new ItemStack(ModItems.plasticrod, 4), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe3 = new FabricatorRecipe(3, new ItemStack(ModItems.plasticsheet, 2), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe4 = new FabricatorRecipe(4, new ItemStack(ModBlocks.machinecase), FabricatorHelper.getFilamentWithAmount(2));
        FabricatorRecipe recipe5 = new FabricatorRecipe(5, new ItemStack(ModBlocks.frame), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe6 = new FabricatorRecipe(6, new ItemStack(ModBlocks.clearplasticblock), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe7 = new FabricatorRecipe(7, new ItemStack(ModBlocks.ladder, 2), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe8 = new FabricatorRecipe(8, new ItemStack(ModItems.plastictissuescaffold, 2), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe9 = new FabricatorRecipe(9, new ItemStack(ModItems.emptysyringe, 3), FabricatorHelper.getFilamentWithAmount(2));
        RecipeProvider.fabricatorRecipes.add(recipe);
        RecipeProvider.fabricatorRecipes.add(recipe2);
        RecipeProvider.fabricatorRecipes.add(recipe3);
        RecipeProvider.fabricatorRecipes.add(recipe4);
        RecipeProvider.fabricatorRecipes.add(recipe5);
        RecipeProvider.fabricatorRecipes.add(recipe6);
        RecipeProvider.fabricatorRecipes.add(recipe7);
        RecipeProvider.fabricatorRecipes.add(recipe8);
        RecipeProvider.fabricatorRecipes.add(recipe9);
        addCustomFabricatorRecipes(10);
    }

    private static void addCustomFabricatorRecipes(int id) {
        for (CustomRecipeWrapper wrapper: RecipeProvider.customRecipes.fabricator) {
            ItemStack inputStack = GameRegistry.makeItemStack(wrapper.input, wrapper.inputMeta, wrapper.inputCount, wrapper.inputNBTData);
            ItemStack outputStack = GameRegistry.makeItemStack(wrapper.output, wrapper.outputMeta, wrapper.outputCount, wrapper.outputNBTData);
            FabricatorRecipe recipe = new FabricatorRecipe(id, outputStack, inputStack);
            RecipeProvider.fabricatorRecipes.add(recipe);
            id++;
        }
    }

    private static SimpleRecipe createSimpleRecipe(Item input, Item output) {
        ItemStack inputStack = new ItemStack(input);
        ItemStack outputStack = new ItemStack(output);

        return new SimpleRecipe(outputStack, inputStack);
    }

    private static List<ItemStack> buildRecipeList(List<ItemStack> itemStacks, List<String> oreNames) {
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
}