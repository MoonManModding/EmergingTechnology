package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.integration.ModLoader;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CollectorRecipeBuilder {

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
        
        if (EmergingTechnologyConfig.POLYMERS_MODULE.COLLECTOR.disabled || removedAll) return;

        for (ItemStack itemStack : getCollectorItemStacks()) {
            RecipeProvider.collectorRecipes.add(new SimpleRecipe(itemStack.getItem(), Items.AIR));
        }

        for (ItemStack itemStack : recipesToRemove) {
            RecipeProvider.removeRecipesByOutput(RecipeProvider.collectorRecipes, itemStack);
        }
    }

    public static List<ItemStack> getCollectorItemStacks() {

        List<ItemStack> itemStacks = new ArrayList<ItemStack>();

        itemStacks.add(new ItemStack(ModItems.paperwaste));
        itemStacks.add(new ItemStack(ModItems.plasticwaste));
        itemStacks.add(new ItemStack(ModItems.algae));

        if (ModLoader.isDumpsterDivingLoaded()) {
            String id = "dumpsterdiving:";
            itemStacks.add(new ItemStack(Item.getByNameOrId(id + "scrap_sil")));
            itemStacks.add(new ItemStack(Item.getByNameOrId(id + "scrap_glass")));
            itemStacks.add(new ItemStack(Item.getByNameOrId(id + "scrap_log")));
            itemStacks.add(new ItemStack(Item.getByNameOrId(id + "scrap_paper")));
            itemStacks.add(new ItemStack(Item.getByNameOrId(id + "scrap_plastic")));
            itemStacks.add(new ItemStack(Item.getByNameOrId(id + "scrap_tin")));
            itemStacks.add(new ItemStack(Item.getByNameOrId(id + "scrap_leather")));
            itemStacks.add(new ItemStack(Item.getByNameOrId(id + "scrap_aluminum")));
            itemStacks.add(new ItemStack(Item.getByNameOrId(id + "scrap_redstone")));
            itemStacks.add(new ItemStack(Item.getByNameOrId(id + "scrap_torch")));
        }

        return itemStacks;
    }

    public static ItemStack getRandomRecoveredItemStack() {
        return RecipeProvider.collectorRecipes.get(new Random().nextInt(RecipeProvider.collectorRecipes.size())).getOutput();
    }

    public static boolean isValidItemStack(ItemStack itemStack) {
        return RecipeProvider.getOutputForItemStackFromRecipes(itemStack, RecipeProvider.collectorRecipes) != null;
    }
}