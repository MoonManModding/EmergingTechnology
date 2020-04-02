package io.moonman.emergingtechnology.machines.battery;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.gui.GuiHelper;
import io.moonman.emergingtechnology.gui.GuiTooltipHelper;
import io.moonman.emergingtechnology.gui.classes.GuiBatteryHandler;
import io.moonman.emergingtechnology.gui.classes.GuiImageButton;
import io.moonman.emergingtechnology.gui.classes.GuiIndicatorData;
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

public class BatteryGui extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(EmergingTechnology.MODID + ":textures/gui/batterygui.png");
	private final InventoryPlayer player;
	private final BatteryTileEntity tileEntity;

	private String NAME = ModBlocks.battery.getLocalizedName();

	private static final int XSIZE = 175;
	private static final int YSIZE = 185;

	private final GuiBatteryHandler batteryHandler;

	// Standard positions for labels
	private static final GuiPosition TOP_LEFT_POS = GuiHelper.getTopLeft();
	private static final GuiPosition TOP_RIGHT_POS = GuiHelper.getTopRight(XSIZE, 44);
	private static final GuiPosition FIRST_FIELD_POS = GuiHelper.getFirstField();
	private static final GuiPosition SECOND_FIELD_POS = GuiHelper.getSecondField();
	private static final GuiPosition INVENTORY_POS = GuiHelper.getInventory(YSIZE);

	// Regions
    
    // Draws textures on gui
	public BatteryGui(InventoryPlayer player, BatteryTileEntity tileEntity) 
	{
		super(new BatteryContainer(player, tileEntity));
		this.player = player;
		this.tileEntity = tileEntity;
		this.xSize = XSIZE;
		this.ySize = YSIZE;

		batteryHandler = new GuiBatteryHandler(tileEntity);
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
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		this.mc.getTextureManager().bindTexture(TEXTURES);

		int energy = this.getEnergyScaled(37);

		this.drawTexturedModalRect(TOP_RIGHT_POS.x, TOP_RIGHT_POS.y, 176, 9, energy, 7);

		// Machine & Inventory labels

		this.fontRenderer.drawString(NAME, TOP_LEFT_POS.x, TOP_LEFT_POS.y, GuiHelper.LABEL_COLOUR);

		int totalInput = this.tileEntity.getField(EnumTileField.BATTERYINPUT);
		int totalOutput = this.tileEntity.getField(EnumTileField.BATTERYOUTPUT);

		this.fontRenderer.drawString("Input", FIRST_FIELD_POS.x - 12, FIRST_FIELD_POS.y + 100, GuiHelper.LABEL_COLOUR);
		this.fontRenderer.drawString("+" + totalInput + "RF", FIRST_FIELD_POS.x - 12, FIRST_FIELD_POS.y + 110, GuiHelper.VALID_COLOUR);

		this.fontRenderer.drawString("Output", SECOND_FIELD_POS.x - 12, SECOND_FIELD_POS.y + 100, GuiHelper.LABEL_COLOUR);
		this.fontRenderer.drawString("-" + totalOutput + "RF", SECOND_FIELD_POS.x - 12, SECOND_FIELD_POS.y + 110, GuiHelper.INVALID_COLOUR);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
	}

	private void createButtons() {
		buttonList.clear();

		int topOffset = this.guiTop + 28;
		int leftColumnOffset = this.guiLeft + 50;
		int rightColumnOffset = this.guiLeft + 93;

		int buttonSize = 16;

		int id = 0;

		// Buttons
		for (int i = 0; i < 6; i++) {
			GuiImageButton input = new GuiImageButton(id, leftColumnOffset, topOffset + (i * buttonSize), buttonSize, buttonSize, GuiHelper.ON_BUTTON_TEXTURE);
			id ++;
			GuiImageButton output = new GuiImageButton(id, rightColumnOffset, topOffset + (i * buttonSize), buttonSize, buttonSize, GuiHelper.OFF_BUTTON_TEXTURE);
			id ++;
			buttonList.add(input);
			buttonList.add(output);
		}

		for (GuiButton button : buttonList) {
			button.visible = false;
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		this.batteryHandler.updateValue(button.id);
	}

	@Override
	public void updateScreen() {
		for (GuiButton button : buttonList) {
			button.visible = this.batteryHandler.shouldRenderButton(button.id);
		}
	}

	private int getEnergyScaled(int scaled)
    {
		return (int) (tileEntity.getField(EnumTileField.ENERGY) * scaled / Reference.BATTERY_ENERGY_CAPACITY);
	}

	private void renderTooltips(int mouseX, int mouseY) {

		int energy = this.tileEntity.getField(EnumTileField.ENERGY);
		int maxEnergy = Reference.BATTERY_ENERGY_CAPACITY;

		GuiIndicatorData energyIndicator = GuiTooltipHelper.getIndicatorData(guiLeft, guiTop, ResourceTypeEnum.ENERGY,
				IndicatorPositionEnum.PRIMARY, mouseX, mouseY, energy, maxEnergy);

		if (energyIndicator.isHovered) {
			this.drawHoveringText(energyIndicator.list, mouseX, mouseY, fontRenderer);
		}

		for (GuiButton button : buttonList) {
			if (mouseX > button.x && mouseX < button.x + button.width && mouseY > button.y && mouseY < button.y + button.width && button.visible) {
				this.drawHoveringText(this.batteryHandler.getTooltip(button.id), mouseX, mouseY, fontRenderer);
			}
		}
	}
}