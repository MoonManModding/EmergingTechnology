package io.moonman.emergingtechnology.recipes.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.providers.ModTissueProvider;
import io.moonman.emergingtechnology.providers.classes.ModTissue;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;

public class BioreactorRecipeBuilder {

    public static void build() {

        if (EmergingTechnologyConfig.SYNTHETICS_MODULE.BIOREACTOR.disabled) return;

        for (ModTissue modTissue: ModTissueProvider.allTissues) {
            ItemStack syringe = ModTissueProvider.getSyringeItemStackByEntityId(modTissue.entityId);
            ItemStack sample = ModTissueProvider.getSampleItemStackByEntityId(modTissue.entityId);

            if (StackHelper.isItemStackEmpty(syringe) || StackHelper.isItemStackEmpty(sample)) {
                continue;
            }

            RecipeProvider.bioreactorRecipes.add(new SimpleRecipe(syringe.getItem(), sample.getItem()));
        }
    }

}