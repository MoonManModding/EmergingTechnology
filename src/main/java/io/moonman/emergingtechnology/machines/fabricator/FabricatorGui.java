package io.moonman.emergingtechnology.machines.fabricator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.gui.GuiHelper;
import io.moonman.emergingtechnology.gui.GuiTooltipHelper;
import io.moonman.emergingtechnology.gui.classes.GuiFabricatorButton;
import io.moonman.emergingtechnology.gui.classes.GuiIndicatorData;
import io.moonman.emergingtechnology.gui.classes.GuiPosition;
import io.moonman.emergingtechnology.gui.classes.GuiRegion;
import io.moonman.emergingtechnology.gui.enums.IndicatorPositionEnum;
import io.moonman.emergingtechnology.gui.enums.IndicatorTypeEnum;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class FabricatorGui extends GuiContainer {

	private static final ResourceLocation TEXTURES = new ResourceLocation(
			EmergingTechnology.MODID + ":textures/gui/fabricatorgui.png");

	private String NAME = ModBlocks.fabricator.getLocalizedName();

	private static final int XSIZE = 175;
	private static final int YSIZE = 185;

	// Standard positions for labels
	private static final GuiPosition TOP_LEFT_POS = GuiHelper.getTopLeft();
	private static final GuiPosition TOP_RIGHT_POS = GuiHelper.getTopRight(XSIZE, 44);
	private static final GuiPosition INVENTORY_POS = GuiHelper.getInventory(YSIZE);

	private final InventoryPlayer player;
	private final FabricatorTileEntity tileEntity;

	private static int page = 0;
	private static GuiRegion nextButton;
	private static GuiRegion previousButton;

	public FabricatorGui(InventoryPlayer player, FabricatorTileEntity tileEntity) {
		super(new FabricatorContainer(player, tileEntity));
		this.player = player;
		this.tileEntity = tileEntity;
		this.xSize = XSIZE;
		this.ySize = YSIZE;
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

		this.drawTexturedModalRect(TOP_RIGHT_POS.x, TOP_RIGHT_POS.y, 176, 9, energy, 7);

		int progress = this.getProgressScaled(34);
		this.drawTexturedModalRect(39, 38, 176, 18, progress, 10);

		this.fontRenderer.drawString(NAME, TOP_LEFT_POS.x, TOP_LEFT_POS.y, GuiHelper.LABEL_COLOUR);
		this.fontRenderer.drawString(GuiHelper.inventoryLabel(this.player), INVENTORY_POS.x, INVENTORY_POS.y,
				GuiHelper.LABEL_COLOUR);
				
		this.fontRenderer.drawString(page + 1 + "", 75, 19, GuiHelper.LABEL_COLOUR);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

	}

	private void createButtons() {
		page = 0;

		buttonList.clear();

		int topOffset = this.guiTop + 32;
		int leftOffset = this.guiLeft + 50;
		int buttonWidth = 15;
		int buttonHeight = 15;
		int margin = 5;

		int maxLeft = leftOffset + ((margin + buttonWidth) * 3);
		int maxTop = topOffset + ((margin + buttonHeight) * 3);

		ArrayList<ArrayList<FabricatorRecipe>> splitRecipes = RecipeProvider.getSplitRecipes(9);

		System.out.println(splitRecipes.size() + " buckets");

		ArrayList<GuiPosition> buttonGridPositions = new ArrayList<GuiPosition>();

		for (int x = leftOffset; x < maxLeft; x += (margin + buttonWidth)) {
			for (int y = topOffset; y < maxTop; y += (margin + buttonHeight)) {
				buttonGridPositions.add(new GuiPosition(x, y));
			}
		}

		int recipePage = 0;

		for (ArrayList<FabricatorRecipe> recipes : splitRecipes) {
			System.out.println(recipes.size() + " items in bucket");
			
			for (int i = 0; i < recipes.size(); i++) {
				GuiPosition pos = buttonGridPositions.get(i);
				buttonList.add(
						new GuiFabricatorButton(recipePage, pos.x, pos.y, buttonWidth, buttonHeight, recipes.get(i)));
			}
			
			recipePage++;
		}

		previousButton = new GuiRegion(this.guiLeft + 45, this.guiTop + 19, this.guiLeft + 55, this.guiTop + 29);
		nextButton = new GuiRegion(this.guiLeft + 101, this.guiTop + 19, this.guiLeft + 111, this.guiTop + 29);
	}

	@Override
	public void updateScreen() {
		for (GuiButton button : buttonList) {
			button.visible = shouldRenderButton(button);
		}
	}

	private boolean shouldRenderButton(GuiButton button) {
		GuiFabricatorButton fabButton = (GuiFabricatorButton) button;
		return fabButton.page == page;
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		GuiFabricatorButton fabButton = (GuiFabricatorButton) button;
		updateFabricatorSelection(fabButton.id);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		GuiPosition position = new GuiPosition(mouseX, mouseY);

		if (nextButton.isPositionInRegion(position)) {
			nextPage();
		}

		if (previousButton.isPositionInRegion(position)) {
			previousPage();
		}
	}

	private void nextPage() {
		if (page < (RecipeProvider.getRecipePagesCount(9) - 1)) {
			page++;
		}
	}

	private void previousPage() {
		if (page > 0) {
			page--;
		}
	}

	private void updateFabricatorSelection(int id) {
		System.out.println("Changing selection to " + id);
	}

	private int getEnergyScaled(int scaled) {
		return (int) (tileEntity.getField(0) * scaled / Reference.FABRICATOR_ENERGY_CAPACITY);
	}

	private int getProgressScaled(int scaled) {
		return (int) (tileEntity.getField(1) * scaled
				/ EmergingTechnologyConfig.POLYMERS_MODULE.FABRICATOR.fabricatorBaseTimeTaken);
	}

	private void renderTooltips(int mouseX, int mouseY) {

		int energy = this.tileEntity.getField(0);
		int maxEnergy = Reference.FABRICATOR_ENERGY_CAPACITY;

		GuiIndicatorData energyIndicator = GuiTooltipHelper.getIndicatorData(guiLeft, guiTop, IndicatorTypeEnum.ENERGY,
				IndicatorPositionEnum.PRIMARY, mouseX, mouseY, energy, maxEnergy);

		if (energyIndicator.isHovered) {
			this.drawHoveringText(energyIndicator.list, mouseX, mouseY, fontRenderer);
		}

		for (GuiButton button : buttonList) {
			GuiFabricatorButton fabButton = (GuiFabricatorButton) button;
			if (fabButton.hovered(mouseX, mouseY) && fabButton.visible) {
				this.drawHoveringText(fabButton.list, mouseX, mouseY);
			}
		}
	}
}