package io.moonman.emergingtechnology.recipes;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import io.moonman.emergingtechnology.helpers.custom.loaders.OreDictionaryLoader;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.machines.ProcessorRecipe;
import io.moonman.emergingtechnology.recipes.machines.ShredderRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public final class RecipeHandler {

    public static final List<ShredderRecipe> shredderRecipes = new ArrayList<>();
    public static final List<ProcessorRecipe> processorRecipes = new ArrayList<>();
    public static final List<ProcessorRecipe> fabricatorRecipes = new ArrayList<>();

	private static ProcessorRecipe registerProcessorRecipe(ItemStack output, Object... inputs) {
		Preconditions.checkArgument(inputs.length <= 16);
		ProcessorRecipe recipe = new ProcessorRecipe(output, inputs);
        processorRecipes.add(recipe);
		return recipe;
    }

    private static ShredderRecipe registerShredderRecipe(ItemStack output, Object... inputs) {
		Preconditions.checkArgument(inputs.length <= 16);
		ShredderRecipe recipe = new ShredderRecipe(output, inputs);
        shredderRecipes.add(recipe);
		return recipe;
    }
    
    public static void registerProcessorRecipes() {
        registerProcessorRecipe(new ItemStack(ModBlocks.plasticblock), new ItemStack(ModItems.shreddedplant));
        registerProcessorRecipe(new ItemStack(ModBlocks.plasticblock), new ItemStack(ModItems.shreddedplastic));
    }

    public static void registerShredderRecipes() {
        registerShredderRecipe(new ItemStack(ModItems.shreddedplant), new ItemStack(Items.REEDS));
        registerShredderRecipe(new ItemStack(ModItems.shreddedplastic), (Object[]) OreDictionaryLoader.getValidPlasticOreDictNames());
    }

}