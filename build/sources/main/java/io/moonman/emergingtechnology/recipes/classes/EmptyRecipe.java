package io.moonman.emergingtechnology.recipes.classes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * An Empty recipe to replace disabled machines/items from Emerging Technology
 */
public class EmptyRecipe implements IRecipe {
    private final IRecipe originalRecipe;

    public EmptyRecipe(IRecipe originalRecipe) {
        this.originalRecipe = originalRecipe;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public IRecipe setRegistryName(ResourceLocation name) {
        return this;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.originalRecipe.getRegistryName();
    }

    @Override
    public Class<IRecipe> getRegistryType() {
        return this.originalRecipe.getRegistryType();
    }
}