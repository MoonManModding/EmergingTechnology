package io.moonman.emergingtechnology.machines.hydroponic;

import io.moonman.emergingtechnology.helpers.machines.HydroponicHelper;
import io.moonman.emergingtechnology.init.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HydroponicTESR extends FastTESR<HydroponicTileEntity> {

    // Many thanks to TheUnlocked from minecraftforge.net for this code - saved my
    // bacon
    // https://www.minecraftforge.net/forum/topic/57009-1112-how-to-make-variable-water-level-in-a-block/

    private static final String CONTAINER_TEXTURE = "emergingtechnology:blocks/grey";

    @SideOnly(Side.CLIENT)
    @Override
    public void renderTileEntityFast(HydroponicTileEntity tileEntity, double x, double y, double z, float partialTicks,
            int destroyStage, float partial, BufferBuilder buffer) {

        this.renderGrowthMedium(tileEntity, x, y, z, partialTicks, destroyStage, partial, buffer);
        this.renderWaterLevel(tileEntity, x, y, z, partialTicks, destroyStage, partial, buffer);
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

                // I was hella lazy here, could be improved

                final float PX = 1f / 16f;
                final float YOFF = 12 * PX;
                final float BORDER = 6f * PX;
                final float MAXHEIGHT = 4 * PX;
                final float LOW = 12f * PX;
        
                float actualHeight = MAXHEIGHT + YOFF;

                TextureAtlasSprite containerTexture =  Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(CONTAINER_TEXTURE);
                TextureAtlasSprite texture = containerTexture;
                
                int mediumId = tileEntity.getGrowthMediumIdFromItemStack();

                if (mediumId > 0) {
                    IBlockState growthMediaBlockState = HydroponicHelper.getMediumBlockStateFromId(mediumId);
                    BlockModelShapes bm = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
                    texture = bm.getTexture(growthMediaBlockState);
                }

                // Lightmap calculations
                int upCombined = getWorld().getCombinedLight(tileEntity.getPos().up(), 0);
                int upLMa = upCombined >> 16 & 65535;
                int upLMb = upCombined & 65535;
        
                buffer.setTranslation(x,y,z);
        
                //UP face
                buffer.pos(BORDER, actualHeight, BORDER).color(1f,1f,1f,1f).tex(texture.getMinU(), texture.getMinV()).lightmap(upLMa,upLMb).endVertex();
                buffer.pos(1 - BORDER, actualHeight, BORDER).color(1f,1f,1f,1f).tex(texture.getMaxU(), texture.getMinV()).lightmap(upLMa,upLMb).endVertex();
                buffer.pos(1 - BORDER, actualHeight, 1 - BORDER).color(1f,1f,1f,1f).tex(texture.getMaxU(), texture.getMaxV()).lightmap(upLMa,upLMb).endVertex();
                buffer.pos(BORDER, actualHeight, 1 - BORDER).color(1f,1f,1f,1f).tex(texture.getMinU(), texture.getMaxV()).lightmap(upLMa,upLMb).endVertex();
        
                // //NORTH face
                // buffer.pos(BORDER, actualHeight, 1 - BORDER).color(1f,1f,1f,1f).tex(containerTexture.getMinU(), containerTexture.getMinV()).lightmap(upLMa,upLMb).endVertex();
                // buffer.pos(1 - BORDER, actualHeight, 1 - BORDER).color(1f,1f,1f,1f).tex(containerTexture.getMaxU(), containerTexture.getMinV()).lightmap(upLMa,upLMb).endVertex();
                // buffer.pos(1 - BORDER, LOW, 1 - BORDER).color(1f,1f,1f,1f).tex(containerTexture.getMaxU(), containerTexture.getMaxV()).lightmap(upLMa,upLMb).endVertex();
                // buffer.pos(BORDER, LOW, 1 - BORDER).color(1f,1f,1f,1f).tex(containerTexture.getMinU(), containerTexture.getMaxV()).lightmap(upLMa,upLMb).endVertex();
        
                // //SOUTH face
                // buffer.pos(BORDER, actualHeight, BORDER).color(1f,1f,1f,1f).tex(containerTexture.getMinU(), containerTexture.getMinV()).lightmap(upLMa,upLMb).endVertex();
                // buffer.pos(1 - BORDER, actualHeight, BORDER).color(1f,1f,1f,1f).tex(containerTexture.getMaxU(), containerTexture.getMinV()).lightmap(upLMa,upLMb).endVertex();
                // buffer.pos(1 - BORDER, LOW, BORDER).color(1f,1f,1f,1f).tex(containerTexture.getMaxU(), containerTexture.getMaxV()).lightmap(upLMa,upLMb).endVertex();
                // buffer.pos(BORDER, LOW, BORDER).color(1f,1f,1f,1f).tex(containerTexture.getMinU(), containerTexture.getMaxV()).lightmap(upLMa,upLMb).endVertex();
        
                // //WEST face
                // buffer.pos(BORDER, actualHeight, BORDER).color(1f,1f,1f,1f).tex(containerTexture.getMinU(), containerTexture.getMinV()).lightmap(upLMa,upLMb).endVertex();
                // buffer.pos(BORDER, actualHeight, 1 - BORDER).color(1f,1f,1f,1f).tex(containerTexture.getMaxU(), containerTexture.getMinV()).lightmap(upLMa,upLMb).endVertex();
                // buffer.pos(BORDER, LOW, 1 - BORDER).color(1f,1f,1f,1f).tex(containerTexture.getMaxU(), containerTexture.getMaxV()).lightmap(upLMa,upLMb).endVertex();
                // buffer.pos(BORDER, LOW, BORDER).color(1f,1f,1f,1f).tex(containerTexture.getMinU(), containerTexture.getMaxV()).lightmap(upLMa,upLMb).endVertex();
        
                // //EAST face
                // buffer.pos(1 - BORDER, actualHeight, BORDER).color(1f,1f,1f,1f).tex(containerTexture.getMinU(), containerTexture.getMinV()).lightmap(upLMa,upLMb).endVertex();
                // buffer.pos(1 - BORDER, actualHeight, 1 - BORDER).color(1f,1f,1f,1f).tex(containerTexture.getMaxU(), containerTexture.getMinV()).lightmap(upLMa,upLMb).endVertex();
                // buffer.pos(1 - BORDER, LOW, 1 - BORDER).color(1f,1f,1f,1f).tex(containerTexture.getMaxU(), containerTexture.getMaxV()).lightmap(upLMa,upLMb).endVertex();
                // buffer.pos(1 - BORDER, LOW, BORDER).color(1f,1f,1f,1f).tex(containerTexture.getMinU(), containerTexture.getMaxV()).lightmap(upLMa,upLMb).endVertex();
    }
}