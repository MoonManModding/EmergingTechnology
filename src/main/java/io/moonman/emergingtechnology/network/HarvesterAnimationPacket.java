package io.moonman.emergingtechnology.network;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.helpers.machines.enums.RotationEnum;
import io.moonman.emergingtechnology.helpers.machines.enums.TurbineSpeedEnum;
import io.moonman.emergingtechnology.machines.harvester.HarvesterTileEntity;
import io.moonman.emergingtechnology.machines.wind.WindTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HarvesterAnimationPacket implements IMessage {
    boolean messageValid;

    private BlockPos pos;
    private int rotation;

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        rotation = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(rotation);
    }

    public HarvesterAnimationPacket() {
    }

    public HarvesterAnimationPacket(BlockPos pos, RotationEnum rotation) {
        this.rotation = RotationEnum.getId(rotation);
        this.pos = pos;
        messageValid = true;
    }

    public static class Handler implements IMessageHandler<HarvesterAnimationPacket, IMessage> {
        @Override
        public IMessage onMessage(HarvesterAnimationPacket message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(HarvesterAnimationPacket message, MessageContext ctx) {

            World world = EmergingTechnology.proxy.getWorld(ctx);

            if (world != null && world.isBlockLoaded(message.pos)) {
                HarvesterTileEntity tileEntity = (HarvesterTileEntity) world.getTileEntity(message.pos);
                if (tileEntity != null) {
                    tileEntity.setRotationClient(RotationEnum.getById(message.rotation));
                }
            }
        }
    }
}