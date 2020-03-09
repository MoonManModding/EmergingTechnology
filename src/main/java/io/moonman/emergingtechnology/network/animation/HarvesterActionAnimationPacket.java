package io.moonman.emergingtechnology.network.animation;

import io.moonman.emergingtechnology.helpers.FacingHelper;
import io.moonman.emergingtechnology.machines.harvester.HarvesterTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HarvesterActionAnimationPacket implements IMessage {
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

    public HarvesterActionAnimationPacket() {
    }

    public HarvesterActionAnimationPacket(BlockPos pos, int integer) {
        this.integer = integer;
        this.pos = pos;
        messageValid = true;
    }

    public static class Handler implements IMessageHandler<HarvesterActionAnimationPacket, IMessage> {
        @Override
        public IMessage onMessage(HarvesterActionAnimationPacket message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(HarvesterActionAnimationPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

            if (player == null)
                return;

            HarvesterTileEntity tileEntity = getTileEntity(player.world, message.pos);
            if (tileEntity == null)
                return;

            tileEntity.doHarvest(FacingHelper.getFacingFromId(message.integer));
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