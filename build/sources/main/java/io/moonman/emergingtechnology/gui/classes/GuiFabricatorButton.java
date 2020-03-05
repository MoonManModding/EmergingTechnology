package io.moonman.emergingtechnology.gui.classes;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiFabricatorButton extends GuiButton {

    private ItemStack itemStackToRender;
    private int cost;
    private String inputName;

    private GuiRegion buttonRegion;

    public List<String> list;

    public GuiFabricatorButton(int id, int x, int y, int width, int height, ItemStack itemStackToRender, String inputName, int cost) {
        super(id, x, y, width, height, "");

        this.itemStackToRender = itemStackToRender;
        this.cost = cost;
        this.inputName = inputName;

        this.list = createList();

        buttonRegion = new GuiRegion(x,y,x + width, y + height);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {

        if (!visible)
            return;
        
        RenderHelper.enableGUIStandardItemLighting();
        RenderItem itemRender = mc.getRenderItem();

        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        itemRender.renderItemAndEffectIntoGUI(itemStackToRender, x, y);
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;

        RenderHelper.disableStandardItemLighting();
    }

    public boolean hovered(int mouseX, int mouseY) {
        return buttonRegion.isPositionInRegion(new GuiPosition(mouseX, mouseY));
    }

    public List<String> createList() {
        List<String> tooltips = new ArrayList<String>();

        tooltips.add(this.itemStackToRender.getDisplayName());
        tooltips.add(Lang.get(Lang.GUI_FABRICATOR_BUTTON_PROGRAM) + this.id);
        tooltips.add(Lang.get(Lang.GUI_FABRICATOR_BUTTON_REQUIRES) + this.cost + " " + this.inputName);

        return tooltips;
    }
}