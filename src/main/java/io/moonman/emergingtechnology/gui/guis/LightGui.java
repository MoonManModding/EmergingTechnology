package io.moonman.emergingtechnology.gui.guis;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.container.containers.LightContainer;
import io.moonman.emergingtechnology.helpers.LightHelper;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.tile.tiles.LightTileEntity;
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
    
    // Draws textures on gui
	public LightGui(InventoryPlayer player, LightTileEntity tileEntity) 
	{
		super(new LightContainer(player, tileEntity));
		this.player = player;
		this.tileEntity = tileEntity;
		this.xSize = 175;
		this.ySize = 165;
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
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		ItemStack contents = this.tileEntity.getItemStack();
		
		boolean bulbSlotEmpty = StackHelper.isItemStackEmpty(contents);

		String bulbName = "Empty";
		int colour = 8553090;
		int growthModifier = 0;

		if (!bulbSlotEmpty) {
			boolean bulbValid = LightHelper.isItemStackValidBulb(contents);
			bulbName = bulbValid ? contents.getDisplayName().split(" ")[0] : "Invalid";
			colour = bulbValid ? 4766261 : 14567989;
			growthModifier = LightHelper.getGrowthProbabilityForBulb(contents);
		}


		this.fontRenderer.drawString(NAME, 6, 8, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 118, this.ySize - 95, 4210752);

		// Medium Name
		this.fontRenderer.drawString("Bulb", 50, 35, 4210752);
		this.fontRenderer.drawString(bulbName, 50, 46, colour);

		// Medium Stats
		this.fontRenderer.drawString("Multiplier", 100, 35, 4210752);
		this.fontRenderer.drawString(growthModifier + "%", 100, 46, colour);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
	}
}