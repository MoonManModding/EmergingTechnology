package io.moonman.emergingtechnology.machines.algaebioreactor;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.gui.GuiHelper;
import io.moonman.emergingtechnology.gui.GuiTooltipHelper;
import io.moonman.emergingtechnology.gui.classes.GuiIndicatorData;
import io.moonman.emergingtechnology.gui.classes.GuiPosition;
import io.moonman.emergingtechnology.gui.enums.IndicatorPositionEnum;
import io.moonman.emergingtechnology.gui.enums.ResourceTypeEnum;
import io.moonman.emergingtechnology.helpers.machines.AlgaeBioreactorHelper;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class AlgaeBioreactorGui extends GuiContainer {
	private static final ResourceLocation TEXTURES = new ResourceLocation(
			EmergingTechnology.MODID + ":textures/gui/algaebioreactorgui.png");
	private final InventoryPlayer player;
	private final AlgaeBioreactorTileEntity tileEntity;

	private String NAME = ModBlocks.algaebioreactor.getLocalizedName();

	private static final int XSIZE = 175;
	private static final int YSIZE = 165;

	// Standard positions for labels
	private static final GuiPosition TOP_LEFT_POS = GuiHelper.getTopLeft();
	private static final GuiPosition TOP_RIGHT_POS = GuiHelper.getTopRight(XSIZE, 44);
	private static final GuiPosition MIDDLE_RIGHT_POS = GuiHelper.getMiddleRight(XSIZE, 44);
	private static final GuiPosition MIDDLE_LOWER_POS = GuiHelper.getMiddleLower(XSIZE, 44);
	private static final GuiPosition INVENTORY_POS = GuiHelper.getInventory(YSIZE);

	// Draws textures on gui
	public AlgaeBioreactorGui(InventoryPlayer player, AlgaeBioreactorTileEntity tileEntity) {
		super(new AlgaeBioreactorContainer(player, tileEntity));
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
		int fluid = this.getFluidScaled(37);
		int gas = this.getGasScaled(37);
		int progress = this.getProgressScaled(34);

		this.drawTexturedModalRect(TOP_RIGHT_POS.x, TOP_RIGHT_POS.y, 176, 0, fluid, 7);
		this.drawTexturedModalRect(MIDDLE_RIGHT_POS.x, MIDDLE_RIGHT_POS.y, 176, 9, energy, 7);
		this.drawTexturedModalRect(MIDDLE_LOWER_POS.x, MIDDLE_LOWER_POS.y, 176, 29, gas, 7);
		this.drawTexturedModalRect(39, 38, 176, 18, progress, 10);

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
		return (int) (tileEntity.getField(EnumTileField.ENERGY) * scaled / Reference.ALGAEBIOREACTOR_ENERGY_CAPACITY);
	}

	private int getFluidScaled(int scaled) {
		return (int) (tileEntity.getField(EnumTileField.FLUID) * scaled / Reference.ALGAEBIOREACTOR_FLUID_CAPACITY);
	}

	private int getGasScaled(int scaled) {
		return (int) (tileEntity.getField(EnumTileField.GAS) * scaled / Reference.ALGAEBIOREACTOR_GAS_CAPACITY);
	}

	private int getProgressScaled(int scaled) {
		return (int) (tileEntity.getField(EnumTileField.PROGRESS) * scaled
				/ AlgaeBioreactorHelper.getTimeTaken(getBoost()));
	}

	private int getBoost() {
		return tileEntity.getField(EnumTileField.LIGHTBOOST);
	}

	private void renderTooltips(int mouseX, int mouseY) {

		int energy = this.tileEntity.getField(EnumTileField.ENERGY);
		int maxEnergy = Reference.ALGAEBIOREACTOR_ENERGY_CAPACITY;

		int fluid = this.tileEntity.getField(EnumTileField.FLUID);
		int maxFluid = Reference.ALGAEBIOREACTOR_FLUID_CAPACITY;

		int gas = this.tileEntity.getField(EnumTileField.GAS);
		int maxGas = Reference.ALGAEBIOREACTOR_GAS_CAPACITY;

		GuiIndicatorData energyIndicator = GuiTooltipHelper.getIndicatorData(guiLeft, guiTop, ResourceTypeEnum.ENERGY,
				IndicatorPositionEnum.SECONDARY, mouseX, mouseY, energy, maxEnergy);

		GuiIndicatorData fluidIndicator = GuiTooltipHelper.getIndicatorData(guiLeft, guiTop, ResourceTypeEnum.FLUID,
				IndicatorPositionEnum.PRIMARY, mouseX, mouseY, fluid, maxFluid);

		GuiIndicatorData gasIndicator = GuiTooltipHelper.getIndicatorData(guiLeft, guiTop, ResourceTypeEnum.GAS,
				IndicatorPositionEnum.LOWER, mouseX, mouseY, gas, maxGas);

		GuiIndicatorData boostIndicator = GuiTooltipHelper.getAlgaeBioreactorGrowthData(guiLeft, guiTop, mouseX, mouseY,
				getBoost());

		GuiIndicatorData progressIndicator = GuiTooltipHelper.getProgressIndicator(guiLeft, guiTop,
				getProgressScaled(100), mouseX, mouseY);

		if (progressIndicator.isHovered) {
			this.drawHoveringText(progressIndicator.list, mouseX, mouseY + 20, fontRenderer);
		}

		if (energyIndicator.isHovered) {
			this.drawHoveringText(energyIndicator.list, mouseX, mouseY, fontRenderer);
		}

		if (fluidIndicator.isHovered) {
			this.drawHoveringText(fluidIndicator.list, mouseX, mouseY, fontRenderer);
		}

		if (gasIndicator.isHovered) {
			this.drawHoveringText(gasIndicator.list, mouseX, mouseY, fontRenderer);
		}

		if (boostIndicator.isHovered) {
			this.drawHoveringText(boostIndicator.list, mouseX, mouseY + 40, fontRenderer);
		}
	}
}