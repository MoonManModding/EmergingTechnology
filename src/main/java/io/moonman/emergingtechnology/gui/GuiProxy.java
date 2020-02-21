package io.moonman.emergingtechnology.gui;

import io.moonman.emergingtechnology.machines.algaebioreactor.AlgaeBioreactorContainer;
import io.moonman.emergingtechnology.machines.algaebioreactor.AlgaeBioreactorGui;
import io.moonman.emergingtechnology.machines.algaebioreactor.AlgaeBioreactorTileEntity;
import io.moonman.emergingtechnology.machines.battery.BatteryContainer;
import io.moonman.emergingtechnology.machines.battery.BatteryGui;
import io.moonman.emergingtechnology.machines.battery.BatteryTileEntity;
import io.moonman.emergingtechnology.machines.biomass.BiomassGeneratorContainer;
import io.moonman.emergingtechnology.machines.biomass.BiomassGeneratorGui;
import io.moonman.emergingtechnology.machines.biomass.BiomassGeneratorTileEntity;
import io.moonman.emergingtechnology.machines.bioreactor.BioreactorContainer;
import io.moonman.emergingtechnology.machines.bioreactor.BioreactorGui;
import io.moonman.emergingtechnology.machines.bioreactor.BioreactorTileEntity;
import io.moonman.emergingtechnology.machines.collector.CollectorContainer;
import io.moonman.emergingtechnology.machines.collector.CollectorGui;
import io.moonman.emergingtechnology.machines.collector.CollectorTileEntity;
import io.moonman.emergingtechnology.machines.cooker.CookerContainer;
import io.moonman.emergingtechnology.machines.cooker.CookerGui;
import io.moonman.emergingtechnology.machines.cooker.CookerTileEntity;
import io.moonman.emergingtechnology.machines.diffuser.DiffuserContainer;
import io.moonman.emergingtechnology.machines.diffuser.DiffuserGui;
import io.moonman.emergingtechnology.machines.diffuser.DiffuserTileEntity;
import io.moonman.emergingtechnology.machines.fabricator.FabricatorContainer;
import io.moonman.emergingtechnology.machines.fabricator.FabricatorGui;
import io.moonman.emergingtechnology.machines.fabricator.FabricatorTileEntity;
import io.moonman.emergingtechnology.machines.harvester.HarvesterContainer;
import io.moonman.emergingtechnology.machines.harvester.HarvesterGui;
import io.moonman.emergingtechnology.machines.harvester.HarvesterTileEntity;
import io.moonman.emergingtechnology.machines.hydroponic.HydroponicContainer;
import io.moonman.emergingtechnology.machines.hydroponic.HydroponicGui;
import io.moonman.emergingtechnology.machines.hydroponic.HydroponicTileEntity;
import io.moonman.emergingtechnology.machines.injector.InjectorContainer;
import io.moonman.emergingtechnology.machines.injector.InjectorGui;
import io.moonman.emergingtechnology.machines.injector.InjectorTileEntity;
import io.moonman.emergingtechnology.machines.light.LightContainer;
import io.moonman.emergingtechnology.machines.light.LightGui;
import io.moonman.emergingtechnology.machines.light.LightTileEntity;
import io.moonman.emergingtechnology.machines.processor.ProcessorContainer;
import io.moonman.emergingtechnology.machines.processor.ProcessorGui;
import io.moonman.emergingtechnology.machines.processor.ProcessorTileEntity;
import io.moonman.emergingtechnology.machines.scaffolder.ScaffolderContainer;
import io.moonman.emergingtechnology.machines.scaffolder.ScaffolderGui;
import io.moonman.emergingtechnology.machines.scaffolder.ScaffolderTileEntity;
import io.moonman.emergingtechnology.machines.scrubber.ScrubberContainer;
import io.moonman.emergingtechnology.machines.scrubber.ScrubberGui;
import io.moonman.emergingtechnology.machines.scrubber.ScrubberTileEntity;
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
        if (te instanceof BioreactorTileEntity) {
            return new BioreactorContainer(player.inventory, (BioreactorTileEntity) te);
        }
        if (te instanceof ScaffolderTileEntity) {
            return new ScaffolderContainer(player.inventory, (ScaffolderTileEntity) te);
        }
        if (te instanceof HarvesterTileEntity) {
            return new HarvesterContainer(player.inventory, (HarvesterTileEntity) te);
        }
        if (te instanceof CollectorTileEntity) {
            return new CollectorContainer(player.inventory, (CollectorTileEntity) te);
        }
        if (te instanceof BiomassGeneratorTileEntity) {
            return new BiomassGeneratorContainer(player.inventory, (BiomassGeneratorTileEntity) te);
        }
        if (te instanceof BatteryTileEntity) {
            return new BatteryContainer(player.inventory, (BatteryTileEntity) te);
        }
        if (te instanceof ScrubberTileEntity) {
            return new ScrubberContainer(player.inventory, (ScrubberTileEntity) te);
        }
        if (te instanceof DiffuserTileEntity) {
            return new DiffuserContainer(player.inventory, (DiffuserTileEntity) te);
        }
        if (te instanceof AlgaeBioreactorTileEntity) {
            return new AlgaeBioreactorContainer(player.inventory, (AlgaeBioreactorTileEntity) te);
        }
        if (te instanceof InjectorTileEntity) {
            return new InjectorContainer(player.inventory, (InjectorTileEntity) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        if (te instanceof HydroponicTileEntity) {
            return new HydroponicGui(player.inventory, (HydroponicTileEntity) te);
        }

        if (te instanceof LightTileEntity) {
            return new LightGui(player.inventory, (LightTileEntity) te);
        }

        if (te instanceof ProcessorTileEntity) {
            return new ProcessorGui(player.inventory, (ProcessorTileEntity) te);
        }

        if (te instanceof ShredderTileEntity) {
            return new ShredderGui(player.inventory, (ShredderTileEntity) te);
        }

        if (te instanceof FabricatorTileEntity) {
            return new FabricatorGui(player.inventory, (FabricatorTileEntity) te);
        }

        if (te instanceof CookerTileEntity) {
            return new CookerGui(player.inventory, (CookerTileEntity) te);
        }

        if (te instanceof BioreactorTileEntity) {
            return new BioreactorGui(player.inventory, (BioreactorTileEntity) te);
        }

        if (te instanceof ScaffolderTileEntity) {
            return new ScaffolderGui(player.inventory, (ScaffolderTileEntity) te);
        }

        if (te instanceof HarvesterTileEntity) {
            return new HarvesterGui(player.inventory, (HarvesterTileEntity) te);
        }

        if (te instanceof CollectorTileEntity) {
            return new CollectorGui(player.inventory, (CollectorTileEntity) te);
        }

        if (te instanceof BiomassGeneratorTileEntity) {
            return new BiomassGeneratorGui(player.inventory, (BiomassGeneratorTileEntity) te);
        }

        if (te instanceof BatteryTileEntity) {
            return new BatteryGui(player.inventory, (BatteryTileEntity) te);
        }

        if (te instanceof ScrubberTileEntity) {
            return new ScrubberGui(player.inventory, (ScrubberTileEntity) te);
        }

        if (te instanceof DiffuserTileEntity) {
            return new DiffuserGui(player.inventory, (DiffuserTileEntity) te);
        }

        if (te instanceof AlgaeBioreactorTileEntity) {
            return new AlgaeBioreactorGui(player.inventory, (AlgaeBioreactorTileEntity) te);
        }

        if (te instanceof InjectorTileEntity) {
            return new InjectorGui(player.inventory, (InjectorTileEntity) te);
        }

        return null;
    }
}