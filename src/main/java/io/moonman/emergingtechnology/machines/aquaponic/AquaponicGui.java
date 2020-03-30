package io.moonman.emergingtechnology.machines.aquaponic;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.gui.GuiHelper;
import io.moonman.emergingtechnology.gui.GuiTooltipHelper;
import io.moonman.emergingtechnology.gui.classes.GuiIndicatorData;
import io.moonman.emergingtechnology.gui.classes.GuiPosition;
import io.moonman.emergingtechnology.gui.enums.IndicatorPositionEnum;
import io.moonman.emergingtechnology.gui.enums.ResourceTypeEnum;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class AquaponicGui extends GuiContainer {
	private static final ResourceLocation TEXTURES = new ResourceLocation(
			EmergingTechnology.MODID + ":textures/gui/aquaponicgui.png");
	private final InventoryPlayer player;
	private final AquaponicTileEntity tileEntity;

	private String NAME = ModBlocks.aquaponic.getLocalizedName();

	private static final int XSIZE = 175;
	private static final int YSIZE = 205;

	// Standard positions for labels
	private static final GuiPosition TOP_LEFT_POS = GuiHelper.getTopLeft();
	private static final GuiPosition TOP_RIGHT_POS = GuiHelper.getTopRight(XSIZE, 44);
	private static final GuiPosition MIDDLE_RIGHT_POS = GuiHelper.getMiddleRight(XSIZE, 44);
	private static final GuiPosition MIDDLE_BOTTOM_POS = GuiHelper.getMiddleBottom(XSIZE, 44);
	private static final GuiPosition INVENTORY_POS = GuiHelper.getInventory(YSIZE);

	// Draws textures on gui
	public AquaponicGui(InventoryPlayer player, AquaponicTileEntity tileEntity) {
		super(new AquaponicContainer(player, tileEntity));
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
		int nutrient = this.getNutrientScaled(37);

		this.drawTexturedModalRect(TOP_RIGHT_POS.x, TOP_RIGHT_POS.y, 176, 0, fluid, 7);
		this.drawTexturedModalRect(MIDDLE_RIGHT_POS.x, MIDDLE_RIGHT_POS.y, 176, 9, energy, 7);
		this.drawTexturedModalRect(MIDDLE_BOTTOM_POS.x, MIDDLE_BOTTOM_POS.y, 176, 0, nutrient, 7);

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
		return (int) (tileEntity.getField(EnumTileField.ENERGY) * scaled / Reference.AQUAPONIC_ENERGY_CAPACITY);
	}

	private int getFluidScaled(int scaled) {
		return (int) (tileEntity.getField(EnumTileField.FLUID) * scaled / Reference.AQUAPONIC_FLUID_CAPACITY);
	}

	private int getNutrientScaled(int scaled) {
		return (int) (tileEntity.getField(EnumTileField.NUTRIENT) * scaled / Reference.AQUAPONIC_FLUID_CAPACITY);
	}

	private void renderTooltips(int mouseX, int mouseY) {

		int energy = this.tileEntity.getField(EnumTileField.ENERGY);
		int maxEnergy = Reference.AQUAPONIC_ENERGY_CAPACITY;

		int fluid = this.tileEntity.getField(EnumTileField.FLUID);
		int maxFluid = Reference.AQUAPONIC_FLUID_CAPACITY;

		int nutrient = this.tileEntity.getField(EnumTileField.NUTRIENT);
		int maxNutrient = Reference.AQUAPONIC_FLUID_CAPACITY;

		GuiIndicatorData energyIndicator = GuiTooltipHelper.getIndicatorData(guiLeft, guiTop, ResourceTypeEnum.ENERGY,
				IndicatorPositionEnum.SECONDARY, mouseX, mouseY, energy, maxEnergy);

		GuiIndicatorData fluidIndicator = GuiTooltipHelper.getIndicatorData(guiLeft, guiTop, ResourceTypeEnum.FLUID,
				IndicatorPositionEnum.PRIMARY, mouseX, mouseY, fluid, maxFluid);

		GuiIndicatorData nutrientIndicator = GuiTooltipHelper.getIndicatorData(guiLeft, guiTop, ResourceTypeEnum.FLUID,
				IndicatorPositionEnum.BOTTOM, mouseX, mouseY, nutrient, maxNutrient);

		if (energyIndicator.isHovered) {
			this.drawHoveringText(energyIndicator.list, mouseX, mouseY, fontRenderer);
		}

		if (fluidIndicator.isHovered) {
			this.drawHoveringText(fluidIndicator.list, mouseX, mouseY, fontRenderer);
		}

		if (nutrientIndicator.isHovered) {
			this.drawHoveringText(nutrientIndicator.list, mouseX, mouseY, fontRenderer);
		}
	}
}