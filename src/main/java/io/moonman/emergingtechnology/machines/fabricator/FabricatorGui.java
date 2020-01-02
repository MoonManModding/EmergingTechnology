package io.moonman.emergingtechnology.machines.fabricator;

import java.util.List;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.gui.GuiHelper;
import io.moonman.emergingtechnology.gui.GuiTooltipHelper;
import io.moonman.emergingtechnology.gui.classes.GuiFabricatorButton;
import io.moonman.emergingtechnology.gui.classes.GuiImageButton;
import io.moonman.emergingtechnology.gui.classes.GuiIndicatorData;
import io.moonman.emergingtechnology.gui.classes.GuiPosition;
import io.moonman.emergingtechnology.gui.classes.GuiRegion;
import io.moonman.emergingtechnology.gui.enums.IndicatorPositionEnum;
import io.moonman.emergingtechnology.gui.enums.IndicatorTypeEnum;
import io.moonman.emergingtechnology.helpers.machines.enums.FabricatorStatusEnum;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.network.FabricatorSelectionPacket;
import io.moonman.emergingtechnology.network.FabricatorStopStartPacket;
import io.moonman.emergingtechnology.network.PacketHandler;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
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

	private static GuiRegion STATUS_REGION;

	private final InventoryPlayer player;
	private final FabricatorTileEntity tileEntity;

	private int selection;
	private boolean printing;
	private FabricatorStatusEnum status;

	private int nextButtonId;
	private int previousButtonId;
	private int playButtonId;
	private int stopButtonId;

	public FabricatorGui(InventoryPlayer player, FabricatorTileEntity tileEntity) {
		super(new FabricatorContainer(player, tileEntity));
		this.player = player;
		this.tileEntity = tileEntity;
		
		this.selection = this.tileEntity.getField(2);
		this.printing = this.tileEntity.getField(3) > 0;
		this.status = FabricatorStatusEnum.getById(this.tileEntity.getField(4));

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

		if (this.printing) {
			int progress = this.getProgressScaled(21);
			this.drawTexturedModalRect(68, 32, 176, 29, 22, 22 - progress);
			this.drawTexturedModalRect(68, 52, 176, 51, progress, 1);
		}

		this.status = FabricatorStatusEnum.getById(this.tileEntity.getField(4));

		if (statusWarning()) {
			this.drawTexturedModalRect(90, 32, 176, 53, 5, 7);
		}

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

	private void createButtons() {
		buttonList.clear();

		int topOffset = this.guiTop + 35;
		int leftOffset = this.guiLeft + 71;
		int buttonWidth = 15;
		int buttonHeight = 15;

		List<FabricatorRecipe> recipes = RecipeProvider.fabricatorRecipes;

		for (int i = 0; i < recipes.size(); i++) {
			FabricatorRecipe recipe = recipes.get(i);
			ItemStack itemStackToRender = recipe.getOutput().copy();
			String inputName = recipe.getInput().getDisplayName();

			GuiFabricatorButton button = new GuiFabricatorButton(i, leftOffset, topOffset, buttonWidth, buttonHeight, itemStackToRender, inputName, recipe.cost);

			button.visible = false;

			buttonList.add(button);
		}

		previousButtonId = recipes.size() + 1;
		nextButtonId = recipes.size() + 2;
		playButtonId = recipes.size() + 3;
		stopButtonId = recipes.size() + 4;

		GuiImageButton previousButton = new GuiImageButton(previousButtonId, this.guiLeft + 63, this.guiTop + 57, 16, 16, GuiHelper.LEFT_BUTTON_TEXTURE);
		GuiImageButton nextButton = new GuiImageButton(nextButtonId, this.guiLeft + 79, this.guiTop + 57, 16, 16, GuiHelper.RIGHT_BUTTON_TEXTURE);

		GuiImageButton playButton = new GuiImageButton(playButtonId, this.guiLeft + 63, this.guiTop + 73, 16, 16, GuiHelper.PLAY_BUTTON_TEXTURE);
		GuiImageButton stopButton = new GuiImageButton(stopButtonId, this.guiLeft + 79, this.guiTop + 73, 16, 16, GuiHelper.STOP_BUTTON_TEXTURE);

		previousButton.visible = false;
		nextButton.visible = false;
		playButton.visible = false;
		stopButton.visible = false;

		buttonList.add(previousButton);
		buttonList.add(nextButton);
		buttonList.add(playButton);
		buttonList.add(stopButton);
	}

	@Override
	public void updateScreen() {
		for (GuiButton button : buttonList) {
			button.visible = shouldRenderButton(button);
		}
	}

	private boolean shouldRenderButton(GuiButton button) {

		if (button instanceof GuiFabricatorButton) {
			GuiFabricatorButton fabButton = (GuiFabricatorButton) button;
			return fabButton.id == selection;
		}

		if (button.id == playButtonId) {
			return !this.printing;
		}

		if (button.id == stopButtonId) {
			return this.printing;
		}

		if (button.id == previousButtonId || button.id == nextButtonId) {
			return !this.printing;
		}

		return false;
	}

	@Override
	protected void actionPerformed(GuiButton button) {

		if (button.id == previousButtonId) {
			previousPage();
		}

		if (button.id == nextButtonId) {
			nextPage();
		}

		if (button.id == playButtonId) {
			this.printing = true;
			updateFabricatorPrinting();
		}

		if (button.id == stopButtonId) {
			this.printing = false;
			updateFabricatorPrinting();
		}
	}

	private void nextPage() {
		if (selection < RecipeProvider.fabricatorRecipes.size() - 1) {
			selection++;
		} else {
			selection = 0;
		}
		updateFabricatorSelection(selection);
	}

	private void previousPage() {
		if (selection > 0) {
			selection--;
		} else {
			selection = RecipeProvider.fabricatorRecipes.size() - 1;
		}
		updateFabricatorSelection(selection);
	}

	private void updateFabricatorSelection(int id) {
		PacketHandler.INSTANCE.sendToServer(new FabricatorSelectionPacket(this.tileEntity.getPos(), id));
	}

	private void updateFabricatorPrinting() {
		int isPrinting = this.printing ? 1 : 0;
		PacketHandler.INSTANCE.sendToServer(new FabricatorStopStartPacket(this.tileEntity.getPos(), isPrinting));
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
			if (button instanceof GuiFabricatorButton) {
				GuiFabricatorButton fabButton = (GuiFabricatorButton) button;
				if (fabButton.hovered(mouseX, mouseY) && fabButton.visible) {
					this.drawHoveringText(fabButton.getTooltip(this.status), mouseX, mouseY);
				}
			}
		}

		this.status = FabricatorStatusEnum.getById(this.tileEntity.getField(4));
	}

	private boolean statusWarning() {
		return !(this.status == FabricatorStatusEnum.IDLE || this.status == FabricatorStatusEnum.RUNNING);
	}
}