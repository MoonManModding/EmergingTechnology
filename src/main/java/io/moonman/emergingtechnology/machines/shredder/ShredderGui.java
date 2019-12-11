package io.moonman.emergingtechnology.machines.shredder;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.gui.GuiHelper;
import io.moonman.emergingtechnology.gui.GuiHelper.GuiIndicator;
import io.moonman.emergingtechnology.gui.GuiHelper.GuiPosition;
import io.moonman.emergingtechnology.init.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class ShredderGui extends GuiContainer {
	private static final ResourceLocation TEXTURES = new ResourceLocation(
			EmergingTechnology.MODID + ":textures/gui/shreddergui.png");
	private final InventoryPlayer player;
	private final ShredderTileEntity tileEntity;

	private String NAME = "Shredder";

	private static final int XSIZE = 175;
	private static final int YSIZE = 165;

	// Standard positions for labels
	private static final GuiPosition TOP_LEFT_POS = GuiHelper.getTopLeft();
	private static final GuiPosition TOP_RIGHT_POS = GuiHelper.getTopRight(XSIZE, 44);
	private static final GuiPosition INVENTORY_POS = GuiHelper.getInventory(YSIZE);

	// Draws textures on gui
	public ShredderGui(InventoryPlayer player, ShredderTileEntity tileEntity) {
		super(new ShredderContainer(player, tileEntity));
		this.player = player;
		this.tileEntity = tileEntity;
		this.xSize = XSIZE;
		this.ySize = YSIZE;
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();

		this.drawHorizontalLine(0, this.width, 65, 0x111);
		
		int l = this.getProgressScaled(24);
		this.drawTexturedModalRect(guiLeft + 81, guiTop + 27, 176, 46, l + 1, 16);

		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	private int getProgressScaled(int pixels)
    {
		int i = this.tileEntity.getProgress();
		int j = this.tileEntity.getMaxProgress();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

		this.fontRenderer.drawString(NAME, TOP_LEFT_POS.x, TOP_LEFT_POS.y, GuiHelper.LABEL_COLOUR);
		this.fontRenderer.drawString(GuiHelper.inventoryLabel(this.player), INVENTORY_POS.x, INVENTORY_POS.y,
				GuiHelper.LABEL_COLOUR);

		// Water Stats
		int energy = this.tileEntity.getEnergy();

		GuiIndicator indicator = new GuiIndicator(energy, Reference.SHREDDER_ENERGY_CAPACITY);

		this.fontRenderer.drawString(indicator.getPercentageString(), TOP_RIGHT_POS.x, TOP_RIGHT_POS.y,
				indicator.getPercentageColour());
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

	}
}