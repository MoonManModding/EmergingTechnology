package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class ScrubberRecipes {

    private static boolean removedAll = false;

    private static List<ItemStack> recipesToRemove = new ArrayList<ItemStack>();

    private static List<String> gasNames = new ArrayList<String>();

    public static void removeAll() {
        removedAll = true;
    }

    public static ItemStack removeByOutput(ItemStack itemStack) {
        recipesToRemove.add(itemStack);
        return itemStack;
    }

    public static void build() {

        if (EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.disabled || removedAll) return;

        gasNames.add("carbondioxide");
        gasNames.add("carbon_dioxide");
        gasNames.add("co2");

        RecipeProvider.scrubberRecipes.add(new SimpleRecipe(ItemStack.EMPTY, new ItemStack(ModItems.biochar)));

        for (ItemStack itemStack : recipesToRemove) {
            RecipeProvider.removeRecipesByOutput(RecipeProvider.scrubberRecipes, itemStack);
        }
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