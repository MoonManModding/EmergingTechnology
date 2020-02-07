package io.moonman.emergingtechnology.machines.diffuser;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.gui.GuiHelper;
import io.moonman.emergingtechnology.gui.GuiTooltipHelper;
import io.moonman.emergingtechnology.gui.classes.GuiIndicatorData;
import io.moonman.emergingtechnology.gui.classes.GuiPosition;
import io.moonman.emergingtechnology.gui.enums.IndicatorPositionEnum;
import io.moonman.emergingtechnology.helpers.enums.ResourceTypeEnum;
import io.moonman.emergingtechnology.helpers.machines.DiffuserHelper;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class DiffuserGui extends GuiContainer {
	private static final ResourceLocation TEXTURES = new ResourceLocation(
			EmergingTechnology.MODID + ":textures/gui/diffusergui.png");
	private final InventoryPlayer player;
	private final DiffuserTileEntity tileEntity;

	private String NAME = ModBlocks.diffuser.getLocalizedName();

	private static final int XSIZE = 175;
	private static final int YSIZE = 165;

	// Standard positions for labels
	private static final GuiPosition TOP_LEFT_POS = GuiHelper.getTopLeft();
	private static final GuiPosition TOP_RIGHT_POS = GuiHelper.getTopRight(XSIZE, 44);
	private static final GuiPosition MIDDLE_RIGHT_POS = GuiHelper.getMiddleRight(XSIZE, 44);
	private static final GuiPosition FIRST_FIELD_POS = GuiHelper.getFirstField();
	private static final GuiPosition SECOND_FIELD_POS = GuiHelper.getSecondField();
	private static final GuiPosition INVENTORY_POS = GuiHelper.getInventory(YSIZE);

	// Draws textures on gui
	public DiffuserGui(InventoryPlayer player, DiffuserTileEntity tileEntity) {
		super(new DiffuserContainer(player, tileEntity));
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
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
		this.renderTooltips(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(TEXTURES);

		int energy = this.getEnergyScaled(37);
		int gas = this.getGasScaled(37);
		int nozzleId = this.getNozzleId();
		String nozzleName = DiffuserHelper.getNozzleShortNameById(nozzleId);
		int boost = DiffuserHelper.getNozzleBoostModifierById(nozzleId) * DiffuserHelper.getBaseBoost();

		this.drawTexturedModalRect(MIDDLE_RIGHT_POS.x, MIDDLE_RIGHT_POS.y, 176, 9, energy, 7);
		this.drawTexturedModalRect(TOP_RIGHT_POS.x, TOP_RIGHT_POS.y, 176, 29, gas, 7);

		// Nozzle Name
		this.fontRenderer.drawString("Nozzle", FIRST_FIELD_POS.x, FIRST_FIELD_POS.y, GuiHelper.LABEL_COLOUR);
		this.fontRenderer.drawString(nozzleName, FIRST_FIELD_POS.x, FIRST_FIELD_POS.y + 10, GuiHelper.EMPTY_COLOUR);

		// Nozzle Stats
		this.fontRenderer.drawString("Growth", SECOND_FIELD_POS.x - 5, SECOND_FIELD_POS.y, GuiHelper.LABEL_COLOUR);
		this.fontRenderer.drawString(boost + "%", SECOND_FIELD_POS.x - 5, SECOND_FIELD_POS.y + 10,
				GuiHelper.EMPTY_COLOUR);

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
		return (int) (tileEntity.getField(0) * scaled / Reference.DIFFUSER_ENERGY_CAPACITY);
	}

	private int getGasScaled(int scaled) {
		return (int) (tileEntity.getField(1) * scaled / Reference.DIFFUSER_GAS_CAPACITY);
	}

	private int getPlants() {
		return tileEntity.getField(2);
	}

	private int getNozzleId() {
		return tileEntity.getField(3);
	}

	private void renderTooltips(int mouseX, int mouseY) {

		int energy = this.tileEntity.getField(0);
		int maxEnergy = Reference.DIFFUSER_ENERGY_CAPACITY;

		int gas = this.tileEntity.getField(1);
		int maxGas = Reference.DIFFUSER_GAS_CAPACITY;

		int growth = DiffuserHelper.getBaseBoost();
		int range = DiffuserHelper.getBaseRange();

		int boostGrowth = DiffuserHelper.getNozzleBoostModifierById(getNozzleId());
		int boostRange = DiffuserHelper.getNozzleRangeModifierById(getNozzleId());

		GuiIndicatorData energyIndicator = GuiTooltipHelper.getIndicatorData(guiLeft, guiTop, ResourceTypeEnum.ENERGY,
				IndicatorPositionEnum.SECONDARY, mouseX, mouseY, energy, maxEnergy);

		GuiIndicatorData gasIndicator = GuiTooltipHelper.getIndicatorData(guiLeft, guiTop, ResourceTypeEnum.GAS,
				IndicatorPositionEnum.PRIMARY, mouseX, mouseY, gas, maxGas);

		GuiIndicatorData growthIndicator = GuiTooltipHelper.getDiffuserGrowthData(guiLeft, guiTop, mouseX, mouseY,
				growth, boostGrowth, range, boostRange, getPlants());

		if (growthIndicator.isHovered) {
			this.drawHoveringText(growthIndicator.list, mouseX, mouseY, fontRenderer);
		}

		if (energyIndicator.isHovered) {
			this.drawHoveringText(energyIndicator.list, mouseX, mouseY, fontRenderer);
		}

		if (gasIndicator.isHovered) {
			this.drawHoveringText(gasIndicator.list, mouseX, mouseY, fontRenderer);
		}
	}
}