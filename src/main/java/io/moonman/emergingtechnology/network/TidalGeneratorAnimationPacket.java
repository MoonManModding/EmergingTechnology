package io.moonman.emergingtechnology.network;

import io.moonman.emergingtechnology.helpers.machines.enums.TurbineSpeedEnum;
import io.moonman.emergingtechnology.machines.tidal.TidalGeneratorTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TidalGeneratorAnimationPacket implements IMessage {
    boolean messageValid;

    private BlockPos pos;
    private int speed;

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        speed = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(speed);
    }

    public TidalGeneratorAnimationPacket() {
    }

    public TidalGeneratorAnimationPacket(BlockPos pos, TurbineSpeedEnum speed) {
        this.speed = TurbineSpeedEnum.getId(speed);
        this.pos = pos;
        messageValid = true;
    }

    public static class Handler implements IMessageHandler<TidalGeneratorAnimationPacket, IMessage> {
        @Override
        public IMessage onMessage(TidalGeneratorAnimationPacket message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(TidalGeneratorAnimationPacket message, MessageContext ctx) {

            World world = Minecraft.getMinecraft().world;

            if (world != null && world.isBlockLoaded(message.pos)) {
                TidalGeneratorTileEntity tileEntity = (TidalGeneratorTileEntity) world.getTileEntity(message.pos);
                tileEntity.setTurbineStateClient(TurbineSpeedEnum.getById(message.speed));
            }
        }
    }
}