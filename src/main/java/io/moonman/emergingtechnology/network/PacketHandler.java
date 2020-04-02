package io.moonman.emergingtechnology.network;

import io.moonman.emergingtechnology.network.animation.HarvesterStartAnimationPacket;
import io.moonman.emergingtechnology.network.animation.HarvesterStopAnimationPacket;
import io.moonman.emergingtechnology.network.animation.ScrubberAnimationPacket;
import io.moonman.emergingtechnology.network.animation.TidalGeneratorAnimationPacket;
import io.moonman.emergingtechnology.network.animation.WindGeneratorAnimationPacket;
import io.moonman.emergingtechnology.network.gui.BatteryUpdatePacket;
import io.moonman.emergingtechnology.network.gui.FabricatorSelectionPacket;
import io.moonman.emergingtechnology.network.gui.FabricatorStopStartPacket;
import io.moonman.emergingtechnology.network.gui.OptimiserUpdatePacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
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
                registerServerMessages();
                registerClientMessages();
        }

        private static void registerServerMessages() {
                INSTANCE.registerMessage(FabricatorSelectionPacket.Handler.class, FabricatorSelectionPacket.class,
                                nextID(), Side.SERVER);
                INSTANCE.registerMessage(FabricatorStopStartPacket.Handler.class, FabricatorStopStartPacket.class,
                                nextID(), Side.SERVER);
                INSTANCE.registerMessage(HarvesterStopAnimationPacket.Handler.class, HarvesterStopAnimationPacket.class,
                                nextID(), Side.SERVER);
                INSTANCE.registerMessage(OptimiserUpdatePacket.Handler.class, OptimiserUpdatePacket.class, nextID(),
                                Side.SERVER);
                INSTANCE.registerMessage(BatteryUpdatePacket.Handler.class, BatteryUpdatePacket.class, nextID(),
                                Side.SERVER);
        }

        private static void registerClientMessages() {
                INSTANCE.registerMessage(TidalGeneratorAnimationPacket.Handler.class,
                                TidalGeneratorAnimationPacket.class, nextID(), Side.CLIENT);
                INSTANCE.registerMessage(WindGeneratorAnimationPacket.Handler.class, WindGeneratorAnimationPacket.class,
                                nextID(), Side.CLIENT);
                INSTANCE.registerMessage(ScrubberAnimationPacket.Handler.class, ScrubberAnimationPacket.class, nextID(),
                                Side.CLIENT);
                INSTANCE.registerMessage(HarvesterStartAnimationPacket.Handler.class,
                                HarvesterStartAnimationPacket.class, nextID(), Side.CLIENT);
        }

        public static TargetPoint getTargetPoint(World world, BlockPos blockPos) {
                if (world == null)
                        return null;

                WorldProvider provider = world.provider;

                int dimension = provider.getDimension();

                return new TargetPoint(dimension, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 5);
        }
}