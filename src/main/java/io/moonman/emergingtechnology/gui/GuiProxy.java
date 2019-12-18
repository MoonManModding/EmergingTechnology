package io.moonman.emergingtechnology.gui;

import io.moonman.emergingtechnology.machines.cooker.CookerContainer;
import io.moonman.emergingtechnology.machines.cooker.CookerGui;
import io.moonman.emergingtechnology.machines.cooker.CookerTileEntity;
import io.moonman.emergingtechnology.machines.fabricator.FabricatorContainer;
import io.moonman.emergingtechnology.machines.fabricator.FabricatorGui;
import io.moonman.emergingtechnology.machines.fabricator.FabricatorTileEntity;
import io.moonman.emergingtechnology.machines.hydroponic.HydroponicContainer;
import io.moonman.emergingtechnology.machines.hydroponic.HydroponicGui;
import io.moonman.emergingtechnology.machines.hydroponic.HydroponicTileEntity;
import io.moonman.emergingtechnology.machines.light.LightContainer;
import io.moonman.emergingtechnology.machines.light.LightGui;
import io.moonman.emergingtechnology.machines.light.LightTileEntity;
import io.moonman.emergingtechnology.machines.processor.ProcessorContainer;
import io.moonman.emergingtechnology.machines.processor.ProcessorGui;
import io.moonman.emergingtechnology.machines.processor.ProcessorTileEntity;
import io.moonman.emergingtechnology.machines.shredder.ShredderContainer;
import io.moonman.emergingtechnology.machines.shredder.ShredderGui;
import io.moonman.emergingtechnology.machines.shredder.ShredderTileEntity;
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
        if (te instanceof LightTileEntity) {
            return new LightContainer(player.inventory, (LightTileEntity) te);
        }
        if (te instanceof ProcessorTileEntity) {
            return new ProcessorContainer(player.inventory, (ProcessorTileEntity) te);
        }
        if (te instanceof ShredderTileEntity) {
            return new ShredderContainer(player.inventory, (ShredderTileEntity) te);
        }
        if (te instanceof FabricatorTileEntity) {
            return new FabricatorContainer(player.inventory, (FabricatorTileEntity) te);
        }
        if (te instanceof CookerTileEntity) {
            return new CookerContainer(player.inventory, (CookerTileEntity) te);
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

        if (te instanceof LightTileEntity) {
            LightTileEntity containerTileEntity = (LightTileEntity) te;
            return new LightGui(player.inventory, containerTileEntity);
        }

        if (te instanceof ProcessorTileEntity) {
            ProcessorTileEntity containerTileEntity = (ProcessorTileEntity) te;
            return new ProcessorGui(player.inventory, containerTileEntity);
        }

        if (te instanceof ShredderTileEntity) {
            ShredderTileEntity containerTileEntity = (ShredderTileEntity) te;
            return new ShredderGui(player.inventory, containerTileEntity);
        }

        if (te instanceof FabricatorTileEntity) {
            FabricatorTileEntity containerTileEntity = (FabricatorTileEntity) te;
            return new FabricatorGui(player.inventory, containerTileEntity);
        }

        if (te instanceof CookerTileEntity) {
            CookerTileEntity containerTileEntity = (CookerTileEntity) te;
            return new CookerGui(player.inventory, containerTileEntity);
        }

        return null;
    }
}