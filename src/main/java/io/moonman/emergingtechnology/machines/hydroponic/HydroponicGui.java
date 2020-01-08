package io.moonman.emergingtechnology.machines.hydroponic;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.gui.GuiHelper;
import io.moonman.emergingtechnology.gui.GuiTooltipHelper;
import io.moonman.emergingtechnology.gui.classes.GuiIndicatorData;
import io.moonman.emergingtechnology.gui.classes.GuiPosition;
import io.moonman.emergingtechnology.gui.enums.IndicatorPositionEnum;

import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.enums.ResourceTypeEnum;
import io.moonman.emergingtechnology.helpers.machines.HydroponicHelper;
import io.moonman.emergingtechnology.init.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class HydroponicGui extends GuiContainer {
	private static final ResourceLocation TEXTURES = new ResourceLocation(
			EmergingTechnology.MODID + ":textures/gui/hydroponicgui.png");
	private final InventoryPlayer player;
	private final HydroponicTileEntity tileEntity;

	private String NAME = "Grow Bed";

	private static final int XSIZE = 175;
	private static final int YSIZE = 165;

	// Standard positions for labels
	private static final GuiPosition TOP_LEFT_POS = GuiHelper.getTopLeft();
	private static final GuiPosition TOP_RIGHT_POS = GuiHelper.getTopRight(XSIZE, 44);
	private static final GuiPosition FIRST_FIELD_POS = GuiHelper.getFirstField();
	private static final GuiPosition SECOND_FIELD_POS = GuiHelper.getSecondField();
	private static final GuiPosition INVENTORY_POS = GuiHelper.getInventory(YSIZE);

	// Draws textures on gui
	public HydroponicGui(InventoryPlayer player, HydroponicTileEntity tileEntity) {
		super(new HydroponicContainer(player, tileEntity));
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

		int fluid = this.getFluidScaled(37);

		this.drawTexturedModalRect(TOP_RIGHT_POS.x, TOP_RIGHT_POS.y, 176, 0, fluid, 7);

		ItemStack contents = this.tileEntity.getItemStack();

		boolean growthMediumEmpty = StackHelper.isItemStackEmpty(contents);

		String growthMediumName = "Empty";
		int colour = GuiHelper.EMPTY_COLOUR;
		int growthModifier = 0;
		int boostModifier = 0;

		if (!growthMediumEmpty) {
			boolean growthMediumValid = HydroponicHelper.isItemStackValid(contents);
			growthMediumName = growthMediumValid ? contents.getDisplayName() : "Invalid";
			colour = growthMediumValid ? GuiHelper.DARK_COLOUR : GuiHelper.INVALID_COLOUR;
			growthModifier = HydroponicHelper.getGrowthProbabilityForMedium(contents);

			boostModifier = this.tileEntity.getSpecificPlantGrowthBoostFromMedium();
			colour = boostModifier > 0 ? GuiHelper.VALID_COLOUR : colour;
		}

		growthModifier += this.tileEntity.getTotalGrowthFromAdjacentLight();

		this.fontRenderer.drawString(NAME, TOP_LEFT_POS.x, TOP_LEFT_POS.y, GuiHelper.LABEL_COLOUR);
		this.fontRenderer.drawString(GuiHelper.inventoryLabel(this.player), INVENTORY_POS.x, INVENTORY_POS.y,
				GuiHelper.LABEL_COLOUR);

		// Medium Name
		this.fontRenderer.drawString("Medium", FIRST_FIELD_POS.x, FIRST_FIELD_POS.y, GuiHelper.LABEL_COLOUR);

		// Do this for long names
		String[] nameSplit = growthMediumName.split(" ");
		int startYPos = 10;

		for (String word : nameSplit) {
			this.fontRenderer.drawString(word, FIRST_FIELD_POS.x, FIRST_FIELD_POS.y + startYPos, colour);
			startYPos += 10;

			// Only display 3 lines.
			if (startYPos == 40)
				break;
		}

		// Medium Stats
		this.fontRenderer.drawString("Growth", SECOND_FIELD_POS.x, SECOND_FIELD_POS.y, GuiHelper.LABEL_COLOUR);
		this.fontRenderer.drawString("+" + (growthModifier + boostModifier) + "%", SECOND_FIELD_POS.x,
				SECOND_FIELD_POS.y + 10, colour);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

	}

	private int getFluidScaled(int scaled) {
		return (int) (tileEntity.getField(0) * scaled / Reference.HYDROPONIC_FLUID_CAPACITY);
	}

	private void renderTooltips(int mouseX, int mouseY) {

		int fluidLevel = this.tileEntity.getField(0);
		int maxFluidLevel = Reference.HYDROPONIC_FLUID_CAPACITY;

		GuiIndicatorData fluidIndicator = GuiTooltipHelper.getIndicatorData(guiLeft, guiTop, ResourceTypeEnum.FLUID,
				IndicatorPositionEnum.PRIMARY, mouseX, mouseY, fluidLevel, maxFluidLevel);

		int growthFromMedium = HydroponicHelper.getGrowthProbabilityForMedium(this.tileEntity.getItemStack());
		int growthFromFluid = HydroponicHelper.getGrowthProbabilityForFluid(this.tileEntity.getFluidStack());
		int boostFromMedium = this.tileEntity.getSpecificPlantGrowthBoostFromMedium();
		int boostFromFluid = this.tileEntity.getSpecificPlantGrowthBoostFromFluid();
		int totalGrowthFromLight = this.tileEntity.getTotalGrowthFromAdjacentLight();

		GuiIndicatorData growthIndicator = GuiTooltipHelper.getHydroponicGrowthData(guiLeft, guiTop, mouseX, mouseY, growthFromMedium, growthFromFluid, boostFromMedium, boostFromFluid, totalGrowthFromLight);

		if (fluidIndicator.isHovered) {
			this.drawHoveringText(fluidIndicator.list, mouseX, mouseY, fontRenderer);
		}

		if (growthIndicator.isHovered) {
			this.drawHoveringText(growthIndicator.list, mouseX, mouseY, fontRenderer);
		}
	}
}