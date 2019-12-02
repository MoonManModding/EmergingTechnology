package io.moonman.emergingtechnology.gui.guis;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.container.containers.HydroponicContainer;
import io.moonman.emergingtechnology.helpers.HydroponicHelper;
import io.moonman.emergingtechnology.tile.tiles.HydroponicTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class HydroponicGui extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(EmergingTechnology.MODID + ":textures/gui/hydroponicgui.png");
	private final InventoryPlayer player;
	private final HydroponicTileEntity tileEntity;

	private String NAME = "Grow Bed";
    
    // Draws textures on gui
	public HydroponicGui(InventoryPlayer player, HydroponicTileEntity tileEntity) 
	{
		super(new HydroponicContainer(player, tileEntity));
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
		
		boolean growthMediumEmpty = HydroponicHelper.isItemStackEmpty(contents);

		String growthMediumName = "Empty";
		int colour = 8553090;
		int growthModifier = 0;

		if (!growthMediumEmpty) {
			boolean growthMediumValid = HydroponicHelper.isItemStackValidGrowthMedia(contents);
			growthMediumName = growthMediumValid ? contents.getDisplayName() : "Invalid";
			colour = growthMediumValid ? 4766261 : 14567989;
			growthModifier = HydroponicHelper.getGrowthProbabilityForMedium(contents);
		}


		this.fontRenderer.drawString(NAME, 6, 8, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 118, this.ySize - 95, 4210752);

		// Medium Name
		this.fontRenderer.drawString("Medium", 50, 35, 4210752);
		this.fontRenderer.drawString(growthMediumName, 50, 46, colour);

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