package io.moonman.emergingtechnology.gui.classes;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;

public class GuiFabricatorButton extends GuiButton {

    public int id;
    public int page;
    public int cost;

    public String itemName;
    private FabricatorRecipe recipe;

    private GuiRegion buttonRegion;

    public List<String> list;

    public GuiFabricatorButton(int page, int x, int y, int width, int height, FabricatorRecipe recipe) {
        super(recipe.id, x, y, width, height, recipe.getOutput().getDisplayName());

        this.page = page;
        this.id = recipe.id;
        this.cost = recipe.cost;
        this.itemName = recipe.getOutput().getDisplayName();

        this.recipe = recipe;

        this.list = createList();

        buttonRegion = new GuiRegion(x,y,x + width, y + height);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {

        if (!visible)
            return;
        
        RenderHelper.enableGUIStandardItemLighting();
        RenderItem itemRender = mc.getRenderItem();

        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        itemRender.renderItemAndEffectIntoGUI(recipe.getOutput(), x, y);
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;

        RenderHelper.disableStandardItemLighting();
    }

    public boolean hovered(int mouseX, int mouseY) {
        return buttonRegion.isPositionInRegion(new GuiPosition(mouseX, mouseY));
    }

    public List<String> createList() {
        List<String> tooltips = new ArrayList<String>();

        tooltips.add(this.recipe.getOutput().getDisplayName());
        tooltips.add("Requires " + this.cost + " " + this.recipe.getInput().getDisplayName());

        return tooltips;
    }

    
}