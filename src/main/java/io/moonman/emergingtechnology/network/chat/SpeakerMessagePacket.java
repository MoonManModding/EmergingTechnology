package io.moonman.emergingtechnology.network.chat;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.machines.SpeakerHelper;
import io.moonman.emergingtechnology.machines.speaker.SpeakerTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SpeakerMessagePacket implements IMessage {
    boolean messageValid;

    private BlockPos pos;
    private int command;

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        command = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(command);
    }

    public SpeakerMessagePacket() {
    }

    public SpeakerMessagePacket(BlockPos pos, String message) {
        this.command = SpeakerHelper.getCommandIdFromMessage(message);
        this.pos = pos;
        messageValid = true;
    }

    public static class Handler implements IMessageHandler<SpeakerMessagePacket, IMessage> {
        @Override
        public IMessage onMessage(SpeakerMessagePacket message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(SpeakerMessagePacket message, MessageContext ctx) {

            World world = EmergingTechnology.proxy.getWorld(ctx);

            int radius = EmergingTechnologyConfig.ELECTRICS_MODULE.SPEAKER.listenRadius * 2;

            BlockPos startPos = message.pos.add(-radius / 2, -radius / 2, -radius / 2);
            
            System.out.println("Message received: " + message.command);

            for (int x = 0; x < radius; x++) {
                for (int y = 0; y < radius; y++) {
                    for (int z = 0; z < radius; z++) {

                        BlockPos positionToCheck = startPos.add(x, y, z);

                        SpeakerTileEntity tileEntity = getTileEntity(world, positionToCheck);

                        if (tileEntity == null)
                            continue;

                        tileEntity.handleCommand(message.command);
                    }
                }
            }
        }

        private SpeakerTileEntity getTileEntity(World world, BlockPos pos) {

            if (world == null)
                return null;
            if (!world.isBlockLoaded(pos))
                return null;
            if (world.getTileEntity(pos) == null)
                return null;
            if (world.getTileEntity(pos) instanceof SpeakerTileEntity == false)
                return null;

            return (SpeakerTileEntity) world.getTileEntity(pos);
        }
    }
}