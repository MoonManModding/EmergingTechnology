package io.moonman.emergingtechnology.network.gui;

import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import io.moonman.emergingtechnology.machines.optimiser.OptimiserTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class OptimiserUpdatePacket implements IMessage {
    boolean messageValid;

    private BlockPos pos;
    private int packetField;
    private int value;

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        packetField = buf.readInt();
        value = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(packetField);
        buf.writeInt(value);
    }

    public OptimiserUpdatePacket() {
    }

    public OptimiserUpdatePacket(BlockPos pos, int packetField, int value) {
        this.packetField = packetField;
        this.value = value;
        this.pos = pos;
        messageValid = true;
    }

    public static class Handler implements IMessageHandler<OptimiserUpdatePacket, IMessage> {
        @Override
        public IMessage onMessage(OptimiserUpdatePacket message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(OptimiserUpdatePacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

            if (player == null)
                return;

            if (message.packetField == 0)
                return;

            OptimiserTileEntity tileEntity = getTileEntity(player.world, message.pos);

            if (tileEntity == null)
                return;

            tileEntity.setField(EnumTileField.getById(message.packetField), message.value);
        }

        private OptimiserTileEntity getTileEntity(World world, BlockPos pos) {

            if (world == null)
                return null;
            if (!world.isBlockLoaded(pos))
                return null;
            if (world.getTileEntity(pos) == null)
                return null;
            if (world.getTileEntity(pos) instanceof OptimiserTileEntity == false)
                return null;

            return (OptimiserTileEntity) world.getTileEntity(pos);
        }
    }
}