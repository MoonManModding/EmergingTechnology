package io.moonman.emergingtechnology.machines.optimiser;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.gui.GuiHelper;
import io.moonman.emergingtechnology.gui.GuiTooltipHelper;
import io.moonman.emergingtechnology.gui.classes.GuiImageButton;
import io.moonman.emergingtechnology.gui.classes.GuiIndicatorData;
import io.moonman.emergingtechnology.gui.classes.GuiOptimiserHandler;
import io.moonman.emergingtechnology.gui.classes.GuiPosition;
import io.moonman.emergingtechnology.gui.classes.GuiRegion;
import io.moonman.emergingtechnology.gui.enums.IndicatorPositionEnum;
import io.moonman.emergingtechnology.gui.enums.ResourceTypeEnum;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class OptimiserGui extends GuiContainer {

	private static final ResourceLocation TEXTURES = new ResourceLocation(
			EmergingTechnology.MODID + ":textures/gui/optimisergui.png");

	private final InventoryPlayer player;

	private final OptimiserTileEntity tileEntity;

	private final GuiOptimiserHandler optimiserHandler;
	private GuiRegion addButtonsIndicator;
	private GuiRegion removeButtonsIndicator;

	private GuiRegion energyBoostIndicator;
	private GuiRegion fluidBoostIndicator;
	private GuiRegion gasBoostIndicator;
	private GuiRegion progressBoostIndicator;

	private String NAME = ModBlocks.optimiser.getLocalizedName();

	private static final int XSIZE = 175;
	private static final int YSIZE = 185;

	// Standard positions for labels
	private static final GuiPosition TOP_LEFT_POS = GuiHelper.getTopLeft();
	private static final GuiPosition TOP_RIGHT_POS = GuiHelper.getTopRight(XSIZE, 44);
	private static final GuiPosition MIDDLE_RIGHT_POS = GuiHelper.getMiddleRight(XSIZE, 44);
	// Standard positions for labels
	private static final GuiPosition INVENTORY_POS = GuiHelper.getInventory(YSIZE);

	// Draws textures on gui
	public OptimiserGui(InventoryPlayer player, OptimiserTileEntity tileEntity) {
		super(new OptimiserContainer(player, tileEntity));
		this.player = player;
		this.tileEntity = tileEntity;
		this.xSize = XSIZE;
		this.ySize = YSIZE;

		this.optimiserHandler = new GuiOptimiserHandler(tileEntity);
	}

	@Override
	public void updateScreen() {
		for (GuiButton button : buttonList) {
			button.visible = this.optimiserHandler.shouldRenderButton(button.id);
		}
	}

	private void createButtons() {
		buttonList.clear();

		int topOffset = this.guiTop + 28;
		int leftColumnOffset = this.guiLeft + 50;
		int rightColumnOffset = this.guiLeft + 93;

		int buttonSize = 16;

		int id = 0;

		// Buttons
		for (int i = 0; i < 4; i++) {
			GuiImageButton down = new GuiImageButton(id, leftColumnOffset, topOffset + (i * buttonSize), buttonSize, buttonSize, GuiHelper.DOWN_BUTTON_TEXTURE);
			id ++;
			GuiImageButton up = new GuiImageButton(id, rightColumnOffset, topOffset + (i * buttonSize), buttonSize, buttonSize, GuiHelper.UP_BUTTON_TEXTURE);
			id ++;
			buttonList.add(down);
			buttonList.add(up);
		}

		for (GuiButton button : buttonList) {
			button.visible = false;
		}

		addButtonsIndicator = new GuiRegion(rightColumnOffset, topOffset, rightColumnOffset + buttonSize, topOffset + (4 * buttonSize));
		removeButtonsIndicator = new GuiRegion(leftColumnOffset, topOffset, leftColumnOffset + buttonSize, topOffset + (4 * buttonSize));

		fluidBoostIndicator = new GuiRegion(leftColumnOffset + 17, topOffset, leftColumnOffset + 42, topOffset + 16);
		energyBoostIndicator = new GuiRegion(leftColumnOffset + 17, topOffset + 16, leftColumnOffset + 42, topOffset + 32);
		gasBoostIndicator = new GuiRegion(leftColumnOffset + 17, topOffset + 32, leftColumnOffset + 42, topOffset + 48);
		progressBoostIndicator = new GuiRegion(leftColumnOffset + 17, topOffset + 48, leftColumnOffset + 42, topOffset + 64);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		this.optimiserHandler.updateValue(button.id);
	}

	@Override
	public void initGui() {
		super.initGui();
		this.createButtons();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
		this.renderTooltips(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(TEXTURES);

		int energy = this.getEnergyScaled(37);
		int water = this.getWaterScaled(37);

		this.optimiserHandler.setMaxAvailablePoints(this.tileEntity.getField(EnumTileField.OPTIMISERCORES));

		this.drawTexturedModalRect(MIDDLE_RIGHT_POS.x, MIDDLE_RIGHT_POS.y, 176, 9, energy, 7);
		this.drawTexturedModalRect(TOP_RIGHT_POS.x, TOP_RIGHT_POS.y, 176, 0, water, 7);

		final int x = TOP_LEFT_POS.x + 75;
		final int y = TOP_LEFT_POS.y + 24;

		this.fontRenderer.drawString(this.optimiserHandler.fluid + "", x, y, GuiHelper.LIGHT_COLOUR);
		this.fontRenderer.drawString(this.optimiserHandler.energy + "", x, y + 16, GuiHelper.LIGHT_COLOUR);
		this.fontRenderer.drawString(this.optimiserHandler.gas + "", x, y + 32, GuiHelper.LIGHT_COLOUR);
		this.fontRenderer.drawString(this.optimiserHandler.progress + "", x, y + 48, GuiHelper.LIGHT_COLOUR);

		this.fontRenderer.drawString(NAME, TOP_LEFT_POS.x, TOP_LEFT_POS.y, GuiHelper.LABEL_COLOUR);
		this.fontRenderer.drawString(GuiHelper.inventoryLabel(this.player), INVENTORY_POS.x, INVENTORY_POS.y,
				GuiHelper.LABEL_COLOUR);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	private int getEnergyScaled(int scaled) {
		return (int) (tileEntity.getField(EnumTileField.ENERGY) * scaled / Reference.OPTIMISER_ENERGY_CAPACITY);
	}

	private int getWaterScaled(int scaled) {
		return (int) (tileEntity.getField(EnumTileField.FLUID) * scaled / Reference.OPTIMISER_FLUID_CAPACITY);
	}

	private void renderTooltips(int mouseX, int mouseY) {

		int energy = this.tileEntity.getField(EnumTileField.ENERGY);
		int maxEnergy = Reference.OPTIMISER_ENERGY_CAPACITY;

		int water = this.tileEntity.getField(EnumTileField.FLUID);
		int maxWater = Reference.OPTIMISER_FLUID_CAPACITY;

		GuiIndicatorData energyIndicator = GuiTooltipHelper.getIndicatorData(guiLeft, guiTop, ResourceTypeEnum.ENERGY,
				IndicatorPositionEnum.SECONDARY, mouseX, mouseY, energy, maxEnergy);

		GuiIndicatorData waterIndicator = GuiTooltipHelper.getIndicatorData(guiLeft, guiTop, ResourceTypeEnum.FLUID,
				IndicatorPositionEnum.PRIMARY, mouseX, mouseY, water, maxWater);

		if (energyIndicator.isHovered) {
			this.drawHoveringText(energyIndicator.list, mouseX, mouseY, fontRenderer);
		}

		if (waterIndicator.isHovered) {
			this.drawHoveringText(waterIndicator.list, mouseX, mouseY, fontRenderer);
		}

		if (addButtonsIndicator.isPositionInRegion(mouseX, mouseY)) {
			this.drawHoveringText(this.optimiserHandler.getAddTooltips(), mouseX, mouseY, fontRenderer);
		}

		if (removeButtonsIndicator.isPositionInRegion(mouseX, mouseY)) {
			this.drawHoveringText(this.optimiserHandler.getRemoveTooltips(), mouseX, mouseY, fontRenderer);
		}

		if (energyBoostIndicator.isPositionInRegion(mouseX, mouseY)) {
			this.drawHoveringText(this.optimiserHandler.getBoostIndicator(EnumTileField.OPTIMISERENERGY), mouseX, mouseY, fontRenderer);
		}

		if (gasBoostIndicator.isPositionInRegion(mouseX, mouseY)) {
			this.drawHoveringText(this.optimiserHandler.getBoostIndicator(EnumTileField.OPTIMISERGAS), mouseX, mouseY, fontRenderer);
		}

		if (fluidBoostIndicator.isPositionInRegion(mouseX, mouseY)) {
			this.drawHoveringText(this.optimiserHandler.getBoostIndicator(EnumTileField.OPTIMISERFLUID), mouseX, mouseY, fontRenderer);
		}

		if (progressBoostIndicator.isPositionInRegion(mouseX, mouseY)) {
			this.drawHoveringText(this.optimiserHandler.getProgressIndicator(), mouseX, mouseY, fontRenderer);
		}
	}
}