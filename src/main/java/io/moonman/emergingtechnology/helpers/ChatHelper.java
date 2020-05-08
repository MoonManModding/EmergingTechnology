package io.moonman.emergingtechnology.helpers;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.machines.speaker.SpeakerTileEntity;
import io.moonman.emergingtechnology.network.PacketHandler;
import io.moonman.emergingtechnology.network.chat.SpeakerMessagePacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = EmergingTechnology.MODID)
public class ChatHelper {

    public static ChatHelper instance = new ChatHelper();

    public static List<SpeakerTileEntity> speakers = new ArrayList<SpeakerTileEntity>();

    @SubscribeEvent
    public static void onPlayerChatEvent(ServerChatEvent event) {
        EntityPlayer player = (EntityPlayer) event.getPlayer();
        if (event.getPlayer() != null) {
            sendChatEventToSpeakers(player.getPosition(), event.getMessage());
        }
    }

    public static void sendChatEventToSpeakers(BlockPos pos, String message) {
        PacketHandler.INSTANCE.sendToServer(new SpeakerMessagePacket(pos, message));
    }


}