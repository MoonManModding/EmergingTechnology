package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.providers.ModTissueProvider;
import io.moonman.emergingtechnology.providers.classes.ModTissue;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;

public class ScaffolderRecipes {

    private static List<IMachineRecipe> scaffolderRecipes = new ArrayList<IMachineRecipe>();

    private static boolean removedAll = false;

    private static List<ItemStack> recipesToRemove = new ArrayList<ItemStack>();

    public static List<IMachineRecipe> getRecipes() {
        return scaffolderRecipes;
    }

    public static void add(IMachineRecipe recipe) {
        scaffolderRecipes.add(recipe);
    }

    public static void removeAll() {
        removedAll = true;
    }

    public static ItemStack removeByOutput(ItemStack itemStack) {
        recipesToRemove.add(itemStack);
        return itemStack;
    }

    public static ItemStack getOutputByItemStack(ItemStack itemStack) {
        ItemStack stack = RecipeBuilder.getOutputForItemStackFromRecipes(itemStack, getRecipes());

        if (stack == null) {
            return ItemStack.EMPTY;
        }

        return stack.copy();
    }

    public static boolean isValidInput(ItemStack itemStack) {
        return getOutputByItemStack(itemStack) != null;
    }

    public static IMachineRecipe getRecipeByInputItemStack(ItemStack itemStack) {
        return RecipeBuilder.getMatchingRecipe(itemStack, getRecipes());
    }

    public static boolean isItemStackValidScaffold(ItemStack itemStack) {
        return StackHelper.compareItemStacks(itemStack, new ItemStack(ModItems.plastictissuescaffold));
    }

    public static void build() {

        if (EmergingTechnologyConfig.SYNTHETICS_MODULE.SCAFFOLDER.disabled || removedAll) return;

        for (ModTissue modTissue: ModTissueProvider.allTissues) {
            ItemStack sample = ModTissueProvider.getSampleItemStackByEntityId(modTissue.entityId);
            ItemStack result = ModTissueProvider.getResultItemStackByEntityId(modTissue.entityId);

            if (StackHelper.isItemStackEmpty(sample) || StackHelper.isItemStackEmpty(result)) {
                continue;
            }

            add(new SimpleRecipe(result.copy(), sample.copy()));
        }

        for (ItemStack itemStack : recipesToRemove) {
            RecipeBuilder.removeRecipesByOutput(scaffolderRecipes, itemStack);
        }
    }

}