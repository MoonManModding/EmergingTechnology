package io.moonman.emergingtechnology.machines.light;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.gui.GuiHelper;
import io.moonman.emergingtechnology.gui.GuiTooltipHelper;
import io.moonman.emergingtechnology.gui.classes.GuiIndicatorData;
import io.moonman.emergingtechnology.gui.classes.GuiPosition;
import io.moonman.emergingtechnology.gui.enums.IndicatorPositionEnum;

import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.gui.enums.ResourceTypeEnum;
import io.moonman.emergingtechnology.helpers.machines.LightHelper;
import io.moonman.emergingtechnology.init.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LightGui extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(EmergingTechnology.MODID + ":textures/gui/lightgui.png");
	private final InventoryPlayer player;
	private final LightTileEntity tileEntity;

	private String NAME = "Grow Light";

	private static final int XSIZE = 175;
	private static final int YSIZE = 165;

	// Standard positions for labels
	private static final GuiPosition TOP_LEFT_POS = GuiHelper.getTopLeft();
	private static final GuiPosition TOP_RIGHT_POS = GuiHelper.getTopRight(XSIZE, 44);
	private static final GuiPosition FIRST_FIELD_POS = GuiHelper.getFirstField();
	private static final GuiPosition SECOND_FIELD_POS = GuiHelper.getSecondField();
	private static final GuiPosition INVENTORY_POS = GuiHelper.getInventory(YSIZE);
    
    // Draws textures on gui
	public LightGui(InventoryPlayer player, LightTileEntity tileEntity) 
	{
		super(new LightContainer(player, tileEntity));
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
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		this.mc.getTextureManager().bindTexture(TEXTURES);

		int energy = this.getEnergyScaled(37);

		this.drawTexturedModalRect(TOP_RIGHT_POS.x, TOP_RIGHT_POS.y, 176, 9, energy, 7);

		ItemStack contents = this.tileEntity.getItemStack();
		
		boolean bulbSlotEmpty = StackHelper.isItemStackEmpty(contents);

		String bulbName = "Empty";
		int colour = GuiHelper.EMPTY_COLOUR;
		int growthModifier = 0;
		int energyUsage = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightEnergyBaseUsage;

		if (!bulbSlotEmpty) {
			// Get bulb type Id
			int bulbTypeId = LightHelper.getBulbTypeIdFromStack(contents);

			// Check is valid
			boolean bulbValid = bulbTypeId != 0 || tileEntity.isGlowstonePowered();

			// Get name and colour
			bulbName = bulbValid ? contents.getDisplayName().split(" ")[0] : "Invalid";
			colour = bulbValid ? GuiHelper.DARK_COLOUR : GuiHelper.INVALID_COLOUR;

			// Get bulb growth modifier
			growthModifier = this.tileEntity.getGrowthProbabilityForBulb();

			int boost = this.tileEntity.getSpecificPlantGrowthBoostForBulb();

			growthModifier += boost;

			if (boost > 0) {
				colour = GuiHelper.VALID_COLOUR;
			}

			// Calculate bulb energy usage from modifier
			energyUsage = energyUsage * LightHelper.getEnergyUsageModifierForBulbById(bulbTypeId);
		}


		// Machine & Inventory labels

		this.fontRenderer.drawString(NAME, TOP_LEFT_POS.x, TOP_LEFT_POS.y, GuiHelper.LABEL_COLOUR);
		this.fontRenderer.drawString(GuiHelper.inventoryLabel(this.player), INVENTORY_POS.x, INVENTORY_POS.y, GuiHelper.LABEL_COLOUR);

		// Bulb Name
		this.fontRenderer.drawString("Bulb", FIRST_FIELD_POS.x, FIRST_FIELD_POS.y, GuiHelper.LABEL_COLOUR);
		this.fontRenderer.drawString(bulbName, FIRST_FIELD_POS.x, FIRST_FIELD_POS.y + 10, colour);

		// Bulb Stats
		this.fontRenderer.drawString("Growth", SECOND_FIELD_POS.x, SECOND_FIELD_POS.y, GuiHelper.LABEL_COLOUR);
		this.fontRenderer.drawString("+" + growthModifier + "%", SECOND_FIELD_POS.x, SECOND_FIELD_POS.y + 10, colour);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
	}

	private int getEnergyScaled(int scaled)
    {
		return (int) (tileEntity.getField(0) * scaled / Reference.LIGHT_ENERGY_CAPACITY);
	}

	private void renderTooltips(int mouseX, int mouseY) {

		int energy = this.tileEntity.getField(0);
		int maxEnergy = Reference.LIGHT_ENERGY_CAPACITY;

		GuiIndicatorData energyIndicator = GuiTooltipHelper.getIndicatorData(guiLeft, guiTop, ResourceTypeEnum.ENERGY,
				IndicatorPositionEnum.PRIMARY, mouseX, mouseY, energy, maxEnergy);

		int growthFromBulb = this.tileEntity.getGrowthProbabilityForBulb();
		int boostFromBulb = this.tileEntity.getSpecificPlantGrowthBoostForBulb();

		GuiIndicatorData growthIndicator = GuiTooltipHelper.getLightGrowData(guiLeft, guiTop, mouseX, mouseY, growthFromBulb, boostFromBulb);

		if (growthIndicator.isHovered) {
			this.drawHoveringText(growthIndicator.list, mouseX, mouseY, fontRenderer);
		}

		if (energyIndicator.isHovered) {
			this.drawHoveringText(energyIndicator.list, mouseX, mouseY, fontRenderer);
		}
	}
}