package io.moonman.emergingtechnology.gui.classes;

import io.moonman.emergingtechnology.gui.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiImageButton extends GuiButton {

    public ResourceLocation texture;
    public ResourceLocation overlay;

    public GuiImageButton(int id, int x, int y, int width, int height, ResourceLocation texture) {
        super(id, x, y, width, height, "");

        this.texture = texture;
        this.overlay = GuiHelper.OVERLAY_BUTTON_TEXTURE;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {

        if (!visible)
            return;
        
            GlStateManager.pushMatrix();
            mc.renderEngine.bindTexture(this.texture);
            GlStateManager.scale(1.0, 1.0, 1.0);
            drawModalRectWithCustomSizedTexture(this.x, this.y, 16, 16, this.width, this.height, 16, 16);

            GlStateManager.popMatrix();
    }

    
}