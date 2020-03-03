package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class AlgaeBioreactorRecipes {

    private static boolean removedAll = false;

    private static List<ItemStack> recipesToRemove = new ArrayList<ItemStack>();

    private static List<String> gasNames = new ArrayList<String>();
    private static List<String> fluidNames = new ArrayList<String>();

    public static void removeAll() {
        removedAll = true;
    }

    public static ItemStack removeByOutput(ItemStack itemStack) {
        recipesToRemove.add(itemStack);
        return itemStack;
    }

    public static void build() {
        
        if (EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.disabled || removedAll) return;

        gasNames.add("carbondioxide");
        gasNames.add("carbon_dioxide");
        gasNames.add("co2");

        fluidNames.add("water");
        fluidNames.add("nutrient");
        
        RecipeProvider.algaeBioreactorRecipes.add(new SimpleRecipe(new ItemStack(ModItems.algae, 2), new ItemStack(ModItems.algae, 1)));

        for (ItemStack itemStack : getSlimeItems()) {
            RecipeProvider.algaeBioreactorRecipes.add(new SimpleRecipe(new ItemStack(ModItems.algae, 4), itemStack));
        }

        for (ItemStack itemStack : recipesToRemove) {
            RecipeProvider.removeRecipesByOutput(RecipeProvider.algaeBioreactorRecipes, itemStack);
        }
    }

    private static List<ItemStack> getSlimeItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();

        List<String> oreInputs = new ArrayList<String>();
        oreInputs.add("slimeball");

        List<ItemStack> inputs = RecipeBuilder.buildRecipeList(itemInputs, oreInputs);

        for (ItemStack itemStack : inputs) {
            itemStack.setCount(1);
        }

        return inputs;
    }

    public static List<String> getValidGasNames() {
        return gasNames;
    }

    public static List<String> getValidFluidNames() {
        return fluidNames;
    }

    public static boolean isValidGas(Fluid fluid) {
        if (fluid == null) return false;

        for (String name : gasNames) {
            if (name.equalsIgnoreCase(fluid.getName())) {
                return true;
            }
        }

        return false;
    }
}