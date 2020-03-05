package io.moonman.emergingtechnology.integration.jei.machines.bioreactor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.integration.jei.machines.MachineReference;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class BioreactorCategory implements IRecipeCategory<BioreactorRecipeWrapper> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(
        EmergingTechnology.MODID + ":textures/jei/bioreactorgui.png");

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private final IDrawable background;
    private final String localizedName;
    private int xSize = 175;
    private int ySize = 80;

    public BioreactorCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(TEXTURE, 0, 0, xSize, ySize);
        localizedName = MachineReference.BIOREACTOR_NAME;
    }

    @Nonnull
    @Override
    public String getUid() {
        return MachineReference.BIOREACTOR_UID;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public String getModName() {
        return EmergingTechnology.NAME;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nullable
    @Override
    public IDrawable getIcon()
    {
        return null;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BioreactorRecipeWrapper recipeWrapper, IIngredients ingredients)
    {
        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();

        guiStacks.init(INPUT_SLOT, true, 16, 34);
        guiStacks.init(OUTPUT_SLOT, false, 79, 34);

        guiStacks.set(ingredients);
    }
}