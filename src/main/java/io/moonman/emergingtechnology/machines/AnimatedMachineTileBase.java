package io.moonman.emergingtechnology.machines;

import com.google.common.collect.ImmutableMap;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.helpers.animation.AnimationHelper;
import io.moonman.emergingtechnology.machines.harvester.HarvesterTileEntity;
import io.moonman.emergingtechnology.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.animation.AnimationStateMachine;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;

/**
 * An Emerging Technology machine with animation
 */
public class AnimatedMachineTileBase extends MachineTileBase {

    private IAnimationStateMachine asm;

    public void initialiseAnimator(MachineTileBase target, String name) {

        subscribe(target);
        asm = isClientForAnimator() ? ModelLoaderRegistry.loadASM(getResourceLocation(name), ImmutableMap.of()) : null;
    }

    public void initialiseHarvesterAnimator(HarvesterTileEntity target) {

        subscribe(target);
        asm = isClientForAnimator() ? AnimationHelper.loadHarvesterASM(getResourceLocation("harvester"), ImmutableMap.of()) : null;
    }

    private void subscribe(Object target) {
        MinecraftForge.EVENT_BUS.register(target);
    }

    private ResourceLocation getResourceLocation(String name) {
        return new ResourceLocation(EmergingTechnology.MODID, "asms/block/" + name + ".json");
    }

    private boolean isClientForAnimator() {
        return FMLCommonHandler.instance().getSide() == Side.CLIENT;
    }

    public IAnimationStateMachine getAnimator() {
        return asm;
    }

    @SubscribeEvent
    public void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getEntityPlayer() instanceof EntityPlayerMP) {
            notifyPlayer((EntityPlayerMP) event.getEntityPlayer());
        }
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    /**
     * Called on tracking event
     */
    public void notifyPlayer(EntityPlayerMP player) {
    }

    public TargetPoint getTargetPoint() {
        return PacketHandler.getTargetPoint(this.getWorld(), this.getPos());
    }
}