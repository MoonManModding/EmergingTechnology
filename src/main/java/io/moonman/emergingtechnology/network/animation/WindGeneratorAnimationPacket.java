package io.moonman.emergingtechnology.network.animation;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.helpers.machines.enums.TurbineSpeedEnum;
import io.moonman.emergingtechnology.machines.wind.WindTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class WindGeneratorAnimationPacket implements IMessage {
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

    public WindGeneratorAnimationPacket() {
    }

    public WindGeneratorAnimationPacket(BlockPos pos, TurbineSpeedEnum speed) {
        this.speed = TurbineSpeedEnum.getId(speed);
        this.pos = pos;
        messageValid = true;
    }

    public static class Handler implements IMessageHandler<WindGeneratorAnimationPacket, IMessage> {
        @Override
        public IMessage onMessage(WindGeneratorAnimationPacket message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(WindGeneratorAnimationPacket message, MessageContext ctx) {

            World world = EmergingTechnology.proxy.getWorld(ctx);

            if (world != null && world.isBlockLoaded(message.pos)) {
                WindTileEntity tileEntity = (WindTileEntity) world.getTileEntity(message.pos);
                if (tileEntity != null) {
                    tileEntity.setTurbineStateClient(TurbineSpeedEnum.getById(message.speed));
                }
            }
        }
    }
}