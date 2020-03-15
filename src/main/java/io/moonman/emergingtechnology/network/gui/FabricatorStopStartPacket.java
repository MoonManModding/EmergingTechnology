package io.moonman.emergingtechnology.network.gui;

import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import io.moonman.emergingtechnology.machines.fabricator.FabricatorTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FabricatorStopStartPacket implements IMessage {
    boolean messageValid;

    private BlockPos pos;
    private int integer;

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        integer = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(integer);
    }

    public FabricatorStopStartPacket() {
    }

    public FabricatorStopStartPacket(BlockPos pos, int isPrinting) {
        this.integer = isPrinting;
        this.pos = pos;
        messageValid = true;
    }

    public static class Handler implements IMessageHandler<FabricatorStopStartPacket, IMessage> {
        @Override
        public IMessage onMessage(FabricatorStopStartPacket message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(FabricatorStopStartPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            World world = player.world;

            if (world.isBlockLoaded(message.pos)) {
                FabricatorTileEntity te = (FabricatorTileEntity) world.getTileEntity(message.pos);
                te.setField(EnumTileField.FABRICATORISPRINTING, message.integer);
            }
        }
    }
}