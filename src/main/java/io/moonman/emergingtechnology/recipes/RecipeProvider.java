package io.moonman.emergingtechnology.recipes;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.helpers.custom.loaders.CustomRecipeLoader;
import io.moonman.emergingtechnology.helpers.custom.wrappers.CustomRecipesWrapper;
import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
Provides useful methods and machine recipe lists
*/
public class RecipeProvider {

    public static List<SimpleRecipe> shredderRecipes = new ArrayList<>();
    public static List<SimpleRecipe> processorRecipes = new ArrayList<>();
    public static List<SimpleRecipe> cookerRecipes = new ArrayList<>();
    public static List<SimpleRecipe> bioreactorRecipes = new ArrayList<>();
    public static List<SimpleRecipe> scaffolderRecipes = new ArrayList<>();
    public static List<FabricatorRecipe> fabricatorRecipes = new ArrayList<>();
    
    public static CustomRecipesWrapper customRecipes;

    public static void preInit(FMLPreInitializationEvent e) {
        CustomRecipeLoader.preInit(e);
    }

    public static ItemStack getProcessorOutputForItemStack(ItemStack itemStack) {
        return getMatchingItemStackFromRecipes(itemStack, processorRecipes);
    }

    public static ItemStack getShredderOutputForItemStack(ItemStack itemStack) {
        return getMatchingItemStackFromRecipes(itemStack, shredderRecipes);
    }
    
    public static ItemStack getBioreactorOutputForItemStack(ItemStack itemStack) {
        return getMatchingItemStackFromRecipes(itemStack, bioreactorRecipes);
    }

    public static ItemStack getScaffolderOutputForItemStack(ItemStack itemStack) {
        return getMatchingItemStackFromRecipes(itemStack, scaffolderRecipes);
    }

    public static ItemStack getFabricatorOutputForItemStack(ItemStack itemStack) {
        for (FabricatorRecipe recipe : fabricatorRecipes) {
            if (recipe.getInput().isItemEqual(itemStack)) {
                return recipe.getOutput();
            }
        }
        return null;
    } 

    private static ItemStack getMatchingItemStackFromRecipes(ItemStack itemStack, List<SimpleRecipe> recipes) {
        for (SimpleRecipe recipe : recipes) {
            if (recipe.getInput().isItemEqual(itemStack)) {
                return recipe.getOutput();
            }
        }
        return null;
    }

    public static FabricatorRecipe getFabricatorRecipeByIndex(int index) {
        return fabricatorRecipes.get(index);
    }

    public static List<List<FabricatorRecipe>> getSplitRecipes(int slots) {
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

    private static <T> List<List<T>> splitList(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(
                list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }
}