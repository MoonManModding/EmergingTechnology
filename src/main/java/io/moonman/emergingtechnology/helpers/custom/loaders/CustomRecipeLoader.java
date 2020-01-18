package io.moonman.emergingtechnology.helpers.custom.loaders;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.helpers.custom.system.JsonHelper;
import io.moonman.emergingtechnology.helpers.custom.wrappers.CustomRecipesWrapper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Loads and validates a custom recipe JSON file
 */
public class CustomRecipeLoader {

    public static void preInit(FMLPreInitializationEvent e) {

        String path = e.getModConfigurationDirectory().getAbsolutePath() + "\\" + EmergingTechnology.MODID
                + "\\custom-recipes.json";
        loadCustomRecipes(path);
    }

    public static void loadCustomRecipes(String filePath) {
        EmergingTechnology.logger.info("EmergingTechnology - Loading custom recipes...");
        try {
            RecipeProvider.customRecipes = readFromJson(filePath);
            EmergingTechnology.logger.info("EmergingTechnology - Loaded custom recipes.");

        } catch (FileNotFoundException ex) {
            EmergingTechnology.logger.warn("Recipe file not found.");
            RecipeProvider.customRecipes = new CustomRecipesWrapper();
        } catch (Exception ex) {
            EmergingTechnology.logger.warn("Warning! There was a problem loading custom recipes:");
            EmergingTechnology.logger.warn(ex);
            RecipeProvider.customRecipes = new CustomRecipesWrapper();
        }
    }

    private static CustomRecipesWrapper readFromJson(String filePath) throws IOException {

        CustomRecipesWrapper wrapper = JsonHelper.GSON_INSTANCE.fromJson(JsonHelper.readFromJsonObject(filePath),
                CustomRecipesWrapper.class);

        return wrapper;
    }
}