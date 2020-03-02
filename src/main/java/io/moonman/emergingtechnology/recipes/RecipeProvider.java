package io.moonman.emergingtechnology.recipes;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.custom.loaders.CustomRecipeLoader;
import io.moonman.emergingtechnology.helpers.custom.wrappers.CustomRecipesWrapper;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Provides useful methods and machine recipe lists
 */
public class RecipeProvider {

    public static List<IMachineRecipe> shredderRecipes = new ArrayList<IMachineRecipe>();
    public static List<IMachineRecipe> processorRecipes = new ArrayList<IMachineRecipe>();
    public static List<IMachineRecipe> cookerRecipes = new ArrayList<IMachineRecipe>();
    public static List<IMachineRecipe> bioreactorRecipes = new ArrayList<IMachineRecipe>();
    public static List<IMachineRecipe> scaffolderRecipes = new ArrayList<IMachineRecipe>();
    public static List<IMachineRecipe> collectorRecipes = new ArrayList<IMachineRecipe>();
    public static List<IMachineRecipe> biomassRecipes = new ArrayList<IMachineRecipe>();
    public static List<IMachineRecipe> fabricatorRecipes = new ArrayList<IMachineRecipe>();
    public static List<IMachineRecipe> scrubberRecipes = new ArrayList<IMachineRecipe>();
    public static List<IMachineRecipe> algaeBioreactorRecipes = new ArrayList<IMachineRecipe>();
    public static List<IMachineRecipe> injectorRecipes = new ArrayList<IMachineRecipe>();

    public static CustomRecipesWrapper customRecipes = new CustomRecipesWrapper();

    public static void preInit(FMLPreInitializationEvent e) {
        CustomRecipeLoader.preInit(e);
    }

    public static ItemStack getFabricatorOutputForItemStack(ItemStack itemStack) {
        return getOutputForItemStackFromRecipes(itemStack, fabricatorRecipes);
    }

    public static ItemStack getOutputForItemStackFromRecipes(ItemStack itemStack, List<IMachineRecipe> recipes) {

        IMachineRecipe recipe = getMatchingRecipe(itemStack, recipes);

        if (recipe != null) {
            return recipe.getOutput();
        }

        return null;
    }

    public static IMachineRecipe getMatchingRecipe(ItemStack itemStack, List<IMachineRecipe> recipes) {
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
        return fabricatorRecipes.get(index);
    }

    public static List<List<IMachineRecipe>> getSplitRecipes(int slots) {
        return splitList(fabricatorRecipes, slots);
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
}