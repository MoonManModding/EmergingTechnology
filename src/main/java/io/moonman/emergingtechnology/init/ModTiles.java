package io.moonman.emergingtechnology.init;

import java.util.function.Supplier;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.machines.filler.Filler;
import io.moonman.emergingtechnology.machines.filler.FillerTile;
import io.moonman.emergingtechnology.machines.solar.Solar;
import io.moonman.emergingtechnology.machines.solar.SolarTile;
import io.moonman.emergingtechnology.machines.solarglass.SolarGlass;
import io.moonman.emergingtechnology.machines.solarglass.SolarGlassTile;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModTiles {

    @ObjectHolder(EmergingTechnology.MODID + ":" + Solar.name)
    public static TileEntityType<SolarTile> SOLAR;

    @ObjectHolder(EmergingTechnology.MODID + ":" + SolarGlass.name)
    public static TileEntityType<SolarGlassTile> SOLARGLASS;

    @ObjectHolder(EmergingTechnology.MODID + ":" + Filler.name)
    public static TileEntityType<FillerTile> FILLER;

    public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
        registerTile(event, SolarTile::new, ModBlocks.SOLAR, Solar.name);
        registerTile(event, SolarGlassTile::new, ModBlocks.SOLARGLASS, SolarGlass.name);
        registerTile(event, FillerTile::new, ModBlocks.FILLER, Filler.name);
    }

    private static void registerTile(final RegistryEvent.Register<TileEntityType<?>> event, Supplier<? extends TileEntity> factoryIn, Block block, String name) {
        event.getRegistry().register(TileEntityType.Builder.create(factoryIn, block).build(null).setRegistryName(name));
    }

}