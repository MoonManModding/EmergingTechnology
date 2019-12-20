package io.moonman.emergingtechnology.gui.classes;

import net.minecraft.client.gui.GuiButton;

public class GuiPageButton extends GuiButton {


    public GuiPageButton(int id, int x, int y, int width, int height, boolean next) {
        super(id, x, y, width, height, next ? ">" : "<");
    }

    // @Override
    // public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {

    //     if (!visible)
    //         return;
        
    //     RenderHelper.enableGUIStandardItemLighting();
    //     RenderItem itemRender = mc.getRenderItem();

    //     GlStateManager.translate(0.0F, 0.0F, 32.0F);
    //     this.zLevel = 200.0F;
    //     itemRender.zLevel = 200.0F;
    //     itemRender.renderItemAndEffectIntoGUI(recipe.getOutput(), x, y);
    //     this.zLevel = 0.0F;
    //     itemRender.zLevel = 0.0F;

    //     RenderHelper.disableStandardItemLighting();
    // }
}