package io.moonman.emergingtechnology.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	private static int packetId = 0;
    public static SimpleNetworkWrapper INSTANCE = null;
	
	public static int nextID() {
        return packetId++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
        registerMessages();
    }

    public static void registerMessages() {
        INSTANCE.registerMessage(FabricatorSelectionPacket.Handler.class, FabricatorSelectionPacket.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(FabricatorStopStartPacket.Handler.class, FabricatorStopStartPacket.class, nextID(), Side.SERVER);
    }
}