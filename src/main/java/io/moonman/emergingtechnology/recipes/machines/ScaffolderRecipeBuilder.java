package io.moonman.emergingtechnology.recipes.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.providers.ModTissueProvider;
import io.moonman.emergingtechnology.providers.classes.ModTissue;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;

public class ScaffolderRecipeBuilder {

    private static boolean removedAll = false;

    public static void removeAll() {
        removedAll = true;
    }

    public static void build() {

        if (EmergingTechnologyConfig.SYNTHETICS_MODULE.SCAFFOLDER.disabled || removedAll) return;

        for (ModTissue modTissue: ModTissueProvider.allTissues) {
            ItemStack sample = ModTissueProvider.getSampleItemStackByEntityId(modTissue.entityId);
            ItemStack result = ModTissueProvider.getResultItemStackByEntityId(modTissue.entityId);

            if (StackHelper.isItemStackEmpty(sample) || StackHelper.isItemStackEmpty(result)) {
                continue;
            }

            RecipeProvider.scaffolderRecipes.add(new SimpleRecipe(result.copy(), sample.copy()));
        }
    }

}