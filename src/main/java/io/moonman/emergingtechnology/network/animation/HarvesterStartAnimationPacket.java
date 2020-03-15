package io.moonman.emergingtechnology.network.animation;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.machines.harvester.HarvesterTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HarvesterStartAnimationPacket implements IMessage {
    boolean messageValid;

    private BlockPos pos;

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    public HarvesterStartAnimationPacket() {
    }

    public HarvesterStartAnimationPacket(BlockPos pos) {
        this.pos = pos;
        messageValid = true;
    }

    public static class Handler implements IMessageHandler<HarvesterStartAnimationPacket, IMessage> {
        @Override
        public IMessage onMessage(HarvesterStartAnimationPacket message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(HarvesterStartAnimationPacket message, MessageContext ctx) {

            HarvesterTileEntity tileEntity = getTileEntity(EmergingTechnology.proxy.getWorld(ctx), message.pos);

            if (tileEntity == null)
                return;

            tileEntity.animateHarvestClient();
        }

        private HarvesterTileEntity getTileEntity(World world, BlockPos pos) {

            if (world == null)
                return null;
            if (!world.isBlockLoaded(pos))
                return null;
            if (world.getTileEntity(pos) == null)
                return null;
            if (world.getTileEntity(pos) instanceof HarvesterTileEntity == false)
                return null;

            return (HarvesterTileEntity) world.getTileEntity(pos);
        }
    }
}