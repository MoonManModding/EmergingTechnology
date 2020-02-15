package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ShredderRecipeBuilder {

    private static boolean removedAll = false;

    public static void removeAll() {
        removedAll = true;
    }

    public static void build() {

        if (EmergingTechnologyConfig.POLYMERS_MODULE.SHREDDER.disabled || removedAll) return;

        registerShredderRecipes(new ItemStack(ModItems.shreddedplant), getShredderPlantItems());
        registerShredderRecipes(new ItemStack(ModItems.shreddedplastic), getShredderPlasticItems());
        registerShredderRecipes(new ItemStack(ModItems.shreddedstarch), getShredderStarchItems());
        registerShredderRecipes(new ItemStack(ModItems.shreddedpaper), getShredderPaperItems());
        registerShredderRecipes(new ItemStack(ModItems.shreddedpaper, 3), getShredderBookItems());
    }

    private static void registerShredderRecipes(ItemStack output, List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            if (RecipeProvider.getOutputForItemStackFromRecipes(input, RecipeProvider.shredderRecipes) == null) {
                SimpleRecipe recipe = new SimpleRecipe(output, input);
                RecipeProvider.shredderRecipes.add(recipe);
            }
        }

        RecipeProvider.shredderRecipes = RecipeProvider.shredderRecipes.stream().distinct()
                .collect(Collectors.toList());
    }

 

    private static List<ItemStack> getShredderPlasticItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();

        itemInputs.add(new ItemStack(ModItems.frame));
        itemInputs.add(new ItemStack(ModItems.plasticsheet));
        itemInputs.add(new ItemStack(ModItems.plasticblock));
        itemInputs.add(new ItemStack(ModItems.clearplasticblock));
        itemInputs.add(new ItemStack(ModItems.plasticrod));
        itemInputs.add(new ItemStack(ModItems.light));
        itemInputs.add(new ItemStack(ModItems.hydroponic));
        itemInputs.add(new ItemStack(ModItems.machinecase));
        itemInputs.add(new ItemStack(ModItems.shredder));
        itemInputs.add(new ItemStack(ModItems.processor));
        itemInputs.add(new ItemStack(ModItems.redbulb));
        itemInputs.add(new ItemStack(ModItems.greenbulb));
        itemInputs.add(new ItemStack(ModItems.bluebulb));
        itemInputs.add(new ItemStack(ModItems.purplebulb));
        itemInputs.add(new ItemStack(ModItems.plasticwaste));

        List<String> oreInputs = new ArrayList<String>();

        oreInputs.add("sheetPlastic");
        oreInputs.add("rodPlastic");
        oreInputs.add("stickPlastic");
        oreInputs.add("platePlastic");
        oreInputs.add("blockPlastic");
        oreInputs.add("itemPlastic");
        oreInputs.add("bioplastic");

        List<ItemStack> inputs = RecipeBuilder.buildRecipeList(itemInputs, oreInputs);

        return inputs;
    }

    private static List<ItemStack> getShredderPlantItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();

        itemInputs.add(new ItemStack(Items.REEDS));

        List<String> oreInputs = new ArrayList<String>();

        List<ItemStack> inputs = RecipeBuilder.buildRecipeList(itemInputs, oreInputs);

        return inputs;
    }

    private static List<ItemStack> getShredderStarchItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();

        itemInputs.add(new ItemStack(Items.BEETROOT));
        itemInputs.add(new ItemStack(Items.POTATO));
        itemInputs.add(new ItemStack(Items.POISONOUS_POTATO));

        List<String> oreInputs = new ArrayList<String>();
        oreInputs.add("cropCorn");
        oreInputs.add("corn");

        List<ItemStack> inputs = RecipeBuilder.buildRecipeList(itemInputs, oreInputs);

        return inputs;
    }

    private static List<ItemStack> getShredderPaperItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();

        itemInputs.add(new ItemStack(Items.PAPER));
        itemInputs.add(new ItemStack(ModItems.paperwaste));

        List<String> oreInputs = new ArrayList<String>();
        oreInputs.add("paper");

        List<ItemStack> inputs = RecipeBuilder.buildRecipeList(itemInputs, oreInputs);

        return inputs;
    }
    
    private static List<ItemStack> getShredderBookItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();

        itemInputs.add(new ItemStack(Items.BOOK));

        List<String> oreInputs = new ArrayList<String>();
        oreInputs.add("book");

        List<ItemStack> inputs = RecipeBuilder.buildRecipeList(itemInputs, oreInputs);

        return inputs;
    }


}