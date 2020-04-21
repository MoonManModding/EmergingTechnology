package io.moonman.emergingtechnology.helpers;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = EmergingTechnology.MODID)
public class ChatHelper {

    public static ChatHelper instance = new ChatHelper();

    @SubscribeEvent
    public static void onPlayerChatEvent(ServerChatEvent event) {
        EntityPlayer player = (EntityPlayer) event.getPlayer();
        if (event.getPlayer() != null) {

            System.out.println("!" + event.getMessage() + " " + player.getPosition());
        }
    }
}