package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.integration.ModLoader;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ShredderRecipes {

    private static List<IMachineRecipe> shredderRecipes = new ArrayList<IMachineRecipe>();

    private static boolean removedAll = false;

    private static List<ItemStack> recipesToRemove = new ArrayList<ItemStack>();

    public static List<IMachineRecipe> getRecipes() {
        return shredderRecipes;
    }

    public static void add(IMachineRecipe recipe) {
        shredderRecipes.add(recipe);
    }

    public static ItemStack removeByOutput(ItemStack itemStack) {
        recipesToRemove.add(itemStack);
        return itemStack;
    }

    public static void removeAll() {
        removedAll = true;
    }

    public static ItemStack getOutputByItemStack(ItemStack itemStack) {
        ItemStack stack = RecipeBuilder.getOutputForItemStackFromRecipes(itemStack, getRecipes());

        if (stack == null) {
            return ItemStack.EMPTY;
        }

        return stack.copy();
    }

    public static boolean isValidInput(ItemStack itemStack) {
        return getOutputByItemStack(itemStack) != null;
    }

    public static IMachineRecipe getRecipeByInputItemStack(ItemStack itemStack) {
        return RecipeBuilder.getMatchingRecipe(itemStack, getRecipes());
    }

    public static void build() {

        if (EmergingTechnologyConfig.POLYMERS_MODULE.SHREDDER.disabled || removedAll)
            return;

        registerShredderRecipes(new ItemStack(ModItems.shreddedplant), getShredderPlantItems());
        registerShredderRecipes(new ItemStack(ModItems.shreddedplastic), getShredderPlasticItems());
        registerShredderRecipes(new ItemStack(ModItems.shreddedstarch), getShredderStarchItems());
        registerShredderRecipes(new ItemStack(ModItems.shreddedpaper), getShredderPaperItems());
        registerShredderRecipes(new ItemStack(ModItems.shreddedpaper, 3), getShredderBookItems());

        registerThirdPartyRecipes();

        for (ItemStack itemStack : recipesToRemove) {
            RecipeBuilder.removeRecipesByOutput(shredderRecipes, itemStack);
        }
    }

    private static void registerShredderRecipes(ItemStack output, List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            if (RecipeBuilder.getOutputForItemStackFromRecipes(input, shredderRecipes) == null) {
                SimpleRecipe recipe = new SimpleRecipe(output, input);
                add(recipe);
            }
        }

        shredderRecipes = shredderRecipes.stream().distinct()
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

        Item p = Item.getByNameOrId("rats:plastic_waste");

        if (p != null) {
            itemInputs.add(new ItemStack(p));
        }

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
        itemInputs.add(new ItemStack(ModItems.algae));

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

    private static void registerThirdPartyRecipes() {

        List<ItemStack> plasticInputs = new ArrayList<ItemStack>();
        List<ItemStack> plantInputs = new ArrayList<ItemStack>();
        List<ItemStack> paperInputs = new ArrayList<ItemStack>();

        if (ModLoader.isDumpsterDivingLoaded()) {
            plasticInputs.add(new ItemStack(Item.getByNameOrId("dumpsterdiving:scrap_plastic")));
            plasticInputs.add(new ItemStack(Item.getByNameOrId("dumpsterdiving:scrap_rubber")));
            paperInputs.add(new ItemStack(Item.getByNameOrId("dumpsterdiving:scrap_paper")));
        }

        registerShredderRecipes(new ItemStack(ModItems.shreddedplastic, 4), plasticInputs);
        registerShredderRecipes(new ItemStack(ModItems.shreddedpaper, 4), paperInputs);
        registerShredderRecipes(new ItemStack(ModItems.shreddedplant, 4), plantInputs);
    }

}