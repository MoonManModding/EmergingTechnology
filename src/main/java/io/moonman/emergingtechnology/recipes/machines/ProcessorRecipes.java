package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.integration.ModLoader;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ProcessorRecipes {

    private static List<IMachineRecipe> processorRecipes = new ArrayList<IMachineRecipe>();

    private static boolean removedAll = false;

    private static List<ItemStack> recipesToRemove = new ArrayList<ItemStack>();

    public static List<IMachineRecipe> getRecipes() {
        return processorRecipes;
    }

    public static void add(IMachineRecipe recipe) {
        processorRecipes.add(recipe);
    }

    public static void removeAll() {
        removedAll = true;
    }

    public static ItemStack removeByOutput(ItemStack itemStack) {
        recipesToRemove.add(itemStack);
        return itemStack;
    }

    public static ItemStack getOutputByItemStack(ItemStack itemStack) {
        return RecipeBuilder.getOutputForItemStackFromRecipes(itemStack, getRecipes());
    }

    public static boolean isValidInput(ItemStack itemStack) {
        return getOutputByItemStack(itemStack) != null;
    }

    public static IMachineRecipe getRecipeByInputItemStack(ItemStack itemStack) {
        return RecipeBuilder.getMatchingRecipe(itemStack, getRecipes());
    }

    public static void build() {

        if (EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.disabled || removedAll) return;

        registerProcessorRecipes(new ItemStack(ModBlocks.plasticblock), getProcessorBlockItems());
        registerProcessorRecipes(new ItemStack(ModBlocks.clearplasticblock), getProcessorClearBlockItems());
        registerProcessorRecipes(new ItemStack(ModItems.paperpulp), getProcessorPaperItems());
        registerProcessorRecipes(new ItemStack(ModItems.fertilizer), getProcessorFertilizerItems());

        registerThirdPartyRecipes();

        for (ItemStack itemStack : recipesToRemove) {
            RecipeBuilder.removeRecipesByOutput(processorRecipes, itemStack);
        }
    }

    private static List<ItemStack> getProcessorBlockItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();
        itemInputs.add(new ItemStack(ModItems.shreddedplastic, 4));
        itemInputs.add(new ItemStack(ModItems.shreddedstarch, 4));
        itemInputs.add(new ItemStack(ModBlocks.shreddedplasticblock, 1));
        itemInputs.add(new ItemStack(ModBlocks.shreddedstarchblock, 1));

        List<String> oreInputs = new ArrayList<String>();

        return RecipeBuilder.buildRecipeList(itemInputs, oreInputs);
    }

    private static List<ItemStack> getProcessorClearBlockItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();
        itemInputs.add(new ItemStack(ModItems.shreddedplant, 4));
        itemInputs.add(new ItemStack(ModBlocks.shreddedplantblock, 1));

        List<String> oreInputs = new ArrayList<String>();

        return RecipeBuilder.buildRecipeList(itemInputs, oreInputs);
    }
    
    private static List<ItemStack> getProcessorPaperItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();
        itemInputs.add(new ItemStack(ModItems.shreddedpaper, 1));

        List<String> oreInputs = new ArrayList<String>();

        return RecipeBuilder.buildRecipeList(itemInputs, oreInputs);
    }

    private static List<ItemStack> getProcessorFertilizerItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();

        itemInputs.add(new ItemStack(ModItems.algae, 1));
        itemInputs.add(new ItemStack(Items.EGG, 1));
        itemInputs.add(new ItemStack(Items.BONE, 1));
        itemInputs.add(new ItemStack(Items.LEATHER, 1));
        itemInputs.add(new ItemStack(Items.FISH, 1));
        itemInputs.add(new ItemStack(Items.DYE, 1, 15));

        List<String> oreInputs = new ArrayList<String>();

        oreInputs.add("blockWool");

        return RecipeBuilder.buildRecipeList(itemInputs, oreInputs);
    }

    private static void registerProcessorRecipes(ItemStack output, List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            if (RecipeBuilder.getOutputForItemStackFromRecipes(input, processorRecipes) == null) {
                SimpleRecipe recipe = new SimpleRecipe(output, input);
                add(recipe);
            }
        }
    }

    private static void registerThirdPartyRecipes() {

        List<ItemStack> plasticInputs = new ArrayList<ItemStack>();
        List<ItemStack> clearPlasticInputs = new ArrayList<ItemStack>();
        List<ItemStack> paperInputs = new ArrayList<ItemStack>();
        List<ItemStack> fertilizerInputs = new ArrayList<ItemStack>();

        if (ModLoader.isDumpsterDivingLoaded()) {
            fertilizerInputs.add(new ItemStack(Item.getByNameOrId("dumpsterdiving:mold_apple")));
            fertilizerInputs.add(new ItemStack(Item.getByNameOrId("dumpsterdiving:mold_pumpkin")));
            fertilizerInputs.add(new ItemStack(Item.getByNameOrId("dumpsterdiving:slop_rand")));
            fertilizerInputs.add(new ItemStack(Item.getByNameOrId("dumpsterdiving:mold_bread")));
            fertilizerInputs.add(new ItemStack(Item.getByNameOrId("dumpsterdiving:mold_stew")));
            paperInputs.add(new ItemStack(Item.getByNameOrId("dumpsterdiving:scrap_wooddust")));
        }

        registerProcessorRecipes(new ItemStack(ModItems.plasticblock), plasticInputs);
        registerProcessorRecipes(new ItemStack(ModItems.clearplasticblock), clearPlasticInputs);
        registerProcessorRecipes(new ItemStack(ModItems.paperpulp), paperInputs);
        registerProcessorRecipes(new ItemStack(ModItems.fertilizer), fertilizerInputs);
    }

}