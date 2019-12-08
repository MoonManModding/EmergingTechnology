package io.moonman.emergingtechnology.machines.hydroponic;

import org.lwjgl.opengl.GL11;

import io.moonman.emergingtechnology.init.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HydroponicTESR extends FastTESR<HydroponicTileEntity> {

    private static final ItemStack DEFAULT_STACK = new ItemStack(Blocks.STONE);

    // Many thanks to TheUnlocked from minecraftforge.net for this code - saved my
    // bacon
    // https://www.minecraftforge.net/forum/topic/57009-1112-how-to-make-variable-water-level-in-a-block/

    @SideOnly(Side.CLIENT)
    @Override
    public void renderTileEntityFast(HydroponicTileEntity tileEntity, double x, double y, double z, float partialTicks,
            int destroyStage, float partial, BufferBuilder buffer) {

        this.renderWaterLevel(tileEntity, x, y, z, partialTicks, destroyStage, partial, buffer);
        this.renderGrowthMedium(tileEntity, x, y, z, partialTicks, destroyStage, partial, buffer);
    }

    public void renderWaterLevel(HydroponicTileEntity tileEntity, double x, double y, double z, float partialTicks,
            int destroyStage, float partial, BufferBuilder buffer) {
        final float PX = 1f / 16f;
        final float YOFF = 12 * PX;
        final float BORDER = 1f * PX;
        final float MAXHEIGHT = 3 * PX;

        float waterLevel = (float) tileEntity.fluidHandler.getFluidAmount() / Reference.HYDROPONIC_FLUID_CAPACITY;

        if (waterLevel <= 0.01) {
            return;
        }

        if (waterLevel >= 0.9) {
            waterLevel = 1;
        }

        float actualHeight = (MAXHEIGHT * waterLevel) + YOFF;
        BlockModelShapes bm = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
        TextureAtlasSprite texture = bm.getTexture(Blocks.WATER.getDefaultState());

        // Lightmap calculations
        int upCombined = getWorld().getCombinedLight(tileEntity.getPos().up(), 0);
        int upLMa = upCombined >> 16 & 65535;
        int upLMb = upCombined & 65535;

        buffer.setTranslation(x, y, z);

        // UP face
        buffer.pos(BORDER, actualHeight, BORDER).color(1f, 1f, 1f, 0.8f).tex(texture.getMinU(), texture.getMinV())
                .lightmap(upLMa, upLMb).endVertex();
        buffer.pos(1 - BORDER, actualHeight, BORDER).color(1f, 1f, 1f, 0.8f).tex(texture.getMaxU(), texture.getMinV())
                .lightmap(upLMa, upLMb).endVertex();
        buffer.pos(1 - BORDER, actualHeight, 1 - BORDER).color(1f, 1f, 1f, 0.8f)
                .tex(texture.getMaxU(), texture.getMaxV()).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(BORDER, actualHeight, 1 - BORDER).color(1f, 1f, 1f, 0.8f).tex(texture.getMinU(), texture.getMaxV())
                .lightmap(upLMa, upLMb).endVertex();
    }

    public void renderGrowthMedium(HydroponicTileEntity tileEntity, double x, double y, double z, float partialTicks,
            int destroyStage, float partial, BufferBuilder buffer) {

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        // Translate to the location of our tile entity
        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();

       

        GlStateManager.pushMatrix();
        // Translate to the center of the block and .9 points higher
        GlStateManager.translate(.5, .85, .5);
        GlStateManager.scale(.3f, .3f, .3f);

        ItemStack stack = tileEntity.getItemStack();

        if (stack.isEmpty()) {
            stack = DEFAULT_STACK;
        }

        GL11.glDisable(GL11.GL_LIGHTING);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);

        Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);

        GL11.glEnable(GL11.GL_LIGHTING);

        GlStateManager.popMatrix();

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}