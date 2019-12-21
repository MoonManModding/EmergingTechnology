package io.moonman.emergingtechnology.recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.moonman.emergingtechnology.helpers.machines.CookerHelper;
import io.moonman.emergingtechnology.helpers.machines.FabricatorHelper;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
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

        registerFilamentFurnaceRecipes();
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

    private static void registerCookerRecipes(List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            ItemStack output = FurnaceRecipes.instance().getSmeltingResult(input);
            SimpleRecipe recipe = new SimpleRecipe(output, input);
            RecipeProvider.cookerRecipes.add(recipe);

        }
    }

    private static void registerProcessorRecipes(ItemStack output, List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            if (RecipeProvider.getProcessorOutputForItemStack(input) == null) {
                SimpleRecipe recipe = new SimpleRecipe(output, input);
                RecipeProvider.processorRecipes.add(recipe);
            }
        }
    }

    private static void registerShredderRecipes(ItemStack output, List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            if (RecipeProvider.getShredderOutputForItemStack(input) == null) {
                SimpleRecipe recipe = new SimpleRecipe(output, input);
                RecipeProvider.shredderRecipes.add(recipe);
            }
        }

        RecipeProvider.shredderRecipes = RecipeProvider.shredderRecipes.stream().distinct()
                .collect(Collectors.toList());
    }

    private static List<ItemStack> getProcessorBlockItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();
        itemInputs.add(new ItemStack(ModItems.shreddedplastic));
        itemInputs.add(new ItemStack(ModItems.shreddedstarch));

        List<String> oreInputs = new ArrayList<String>();
        oreInputs.add("dustPlastic");
        oreInputs.add("orePlastic");
        oreInputs.add("starch");
        oreInputs.add("dustStarch");

        return buildRecipeList(itemInputs, oreInputs);
    }

    private static List<ItemStack> getProcessorClearBlockItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();
        itemInputs.add(new ItemStack(ModItems.shreddedplant));

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

    private static void registerFilamentFurnaceRecipes() {
        ItemStack result = new ItemStack(ModItems.filament);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.shreddedplant), result, 0.1f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.shreddedplastic), result, 0.1f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.shreddedstarch), result, 0.1f);
    }

    private static void buildFabricatorRecipeList() {

        FabricatorRecipe recipe = new FabricatorRecipe(1, new ItemStack(ModBlocks.plasticblock), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe2 = new FabricatorRecipe(2, new ItemStack(ModItems.plasticrod), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe3 = new FabricatorRecipe(3, new ItemStack(ModItems.plasticsheet), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe4 = new FabricatorRecipe(4, new ItemStack(ModBlocks.machinecase), FabricatorHelper.getFilamentWithAmount(4));
        FabricatorRecipe recipe5 = new FabricatorRecipe(5, new ItemStack(ModBlocks.frame), FabricatorHelper.getFilamentWithAmount(1));
        FabricatorRecipe recipe6 = new FabricatorRecipe(6, new ItemStack(ModBlocks.clearplasticblock), FabricatorHelper.getFilamentWithAmount(1));
        RecipeProvider.fabricatorRecipes.add(recipe);
        RecipeProvider.fabricatorRecipes.add(recipe2);
        RecipeProvider.fabricatorRecipes.add(recipe3);
        RecipeProvider.fabricatorRecipes.add(recipe4);
        RecipeProvider.fabricatorRecipes.add(recipe5);
        RecipeProvider.fabricatorRecipes.add(recipe6);
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