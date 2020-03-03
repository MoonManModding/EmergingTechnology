package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.providers.ModTissueProvider;
import io.moonman.emergingtechnology.providers.classes.ModTissue;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;

public class BioreactorRecipes {

    private static boolean removedAll = false;

    private static List<ItemStack> recipesToRemove = new ArrayList<ItemStack>();

    public static void removeAll() {
        removedAll = true;
    }

    public static ItemStack removeByOutput(ItemStack itemStack) {
        recipesToRemove.add(itemStack);
        return itemStack;
    }

    public static void build() {

        if (EmergingTechnologyConfig.SYNTHETICS_MODULE.BIOREACTOR.disabled || removedAll) return;

        for (ModTissue modTissue: ModTissueProvider.allTissues) {
            ItemStack syringe = ModTissueProvider.getSyringeItemStackByEntityId(modTissue.entityId);
            ItemStack sample = ModTissueProvider.getSampleItemStackByEntityId(modTissue.entityId);

            if (StackHelper.isItemStackEmpty(syringe) || StackHelper.isItemStackEmpty(sample)) {
                continue;
            }

            RecipeProvider.bioreactorRecipes.add(new SimpleRecipe(sample.getItem(), syringe.getItem()));
        }

        for (ItemStack itemStack : recipesToRemove) {
            RecipeProvider.removeRecipesByOutput(RecipeProvider.bioreactorRecipes, itemStack);
        }
    }

}