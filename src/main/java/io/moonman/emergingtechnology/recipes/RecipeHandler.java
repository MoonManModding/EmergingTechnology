package io.moonman.emergingtechnology.recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeHandler {

    public static List<SimpleRecipe> shredderRecipes = new ArrayList<>();
    public static List<SimpleRecipe> processorRecipes = new ArrayList<>();
    public static List<SimpleRecipe> fabricatorRecipes = new ArrayList<>();

    public static ItemStack getProcessorOutputForItemStack(ItemStack itemStack) {
        for (SimpleRecipe recipe : processorRecipes) {
            if (recipe.getInput().isItemEqual(itemStack)) {
                return recipe.getOutput();
            }
        }

        return null;
    }

    public static ItemStack getShredderOutputForItemStack(ItemStack itemStack) {
        for (SimpleRecipe recipe : shredderRecipes) {
            if (recipe.getInput().isItemEqual(itemStack)) {
                return recipe.getOutput();
            }
        }

        return null;
    }

    public static void buildProcessorRecipes() {

        List<ItemStack> itemInputs = new ArrayList<ItemStack>();
        itemInputs.add(new ItemStack(ModItems.shreddedplant));
        itemInputs.add(new ItemStack(ModItems.shreddedplastic));

        List<String> oreInputs = new ArrayList<String>();
        oreInputs.add("dustPlastic");
        oreInputs.add("orePlastic");

        List<ItemStack> inputs = buildRecipeList(itemInputs, oreInputs);

        registerProcessorRecipes(new ItemStack(ModBlocks.plasticblock), inputs);
    }

    public static void buildShredderRecipes() {

        registerShredderRecipes(new ItemStack(ModItems.shreddedplant), getShredderPlantItems());

        registerShredderRecipes(new ItemStack(ModItems.shreddedplastic), getShredderPlasticItems());
    }

    private static void registerProcessorRecipes(ItemStack output, List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            if (getProcessorOutputForItemStack(input) == null) {
                SimpleRecipe recipe = new SimpleRecipe(output, input);
                processorRecipes.add(recipe);
            }
        }
    }

    private static void registerShredderRecipes(ItemStack output, List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            if (getShredderOutputForItemStack(input) == null) {
                SimpleRecipe recipe = new SimpleRecipe(output, input);
                shredderRecipes.add(recipe);
            }
        }

        shredderRecipes = shredderRecipes.stream().distinct().collect(Collectors.toList());
    }

    private static List<ItemStack> getShredderPlasticItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();

        itemInputs.add(new ItemStack(ModItems.frame));
        itemInputs.add(new ItemStack(ModItems.plasticsheet));
        itemInputs.add(new ItemStack(ModItems.plasticblock));
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
        itemInputs.add(new ItemStack(Items.POTATO));

        List<String> oreInputs = new ArrayList<String>();

        List<ItemStack> inputs = buildRecipeList(itemInputs, oreInputs);

        return inputs;
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