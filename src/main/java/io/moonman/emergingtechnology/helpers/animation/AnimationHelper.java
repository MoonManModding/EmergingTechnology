package io.moonman.emergingtechnology.helpers.animation;

import com.google.common.collect.ImmutableMap;

import io.moonman.emergingtechnology.helpers.FacingHelper;
import io.moonman.emergingtechnology.network.PacketHandler;
import io.moonman.emergingtechnology.network.animation.HarvesterActionAnimationPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AnimationHelper {

    @SideOnly(Side.CLIENT)
    public static HarvesterAnimationStateMachine loadHarvesterASM(ResourceLocation location, ImmutableMap<String, ITimeValue> customParameters) {
        IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
        return HarvesterAnimationStateMachine.load(manager, location, customParameters);
    }

    @SideOnly(Side.CLIENT)
    public static void onHarvesterAction(BlockPos pos, EnumFacing baseFacing, EnumFacing cropFacing, String action, String state) {
        
        PacketHandler.INSTANCE.sendToServer(new HarvesterActionAnimationPacket(pos, FacingHelper.facingToInt(cropFacing)));
    }
}