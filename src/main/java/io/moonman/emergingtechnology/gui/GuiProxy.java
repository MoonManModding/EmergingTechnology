package io.moonman.emergingtechnology.gui;

import io.moonman.emergingtechnology.container.containers.HydroponicContainer;
import io.moonman.emergingtechnology.gui.guis.HydroponicGui;
import io.moonman.emergingtechnology.tile.tiles.HydroponicTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof HydroponicTileEntity) {
            return new HydroponicContainer(player.inventory, (HydroponicTileEntity) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof HydroponicTileEntity) {
            HydroponicTileEntity containerTileEntity = (HydroponicTileEntity) te;
            return new HydroponicGui(player.inventory, containerTileEntity);
        }

        return null;
    }
}