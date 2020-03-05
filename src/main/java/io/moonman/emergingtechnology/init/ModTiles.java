package io.moonman.emergingtechnology.init;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.machines.solar.Solar;
import io.moonman.emergingtechnology.machines.solar.SolarTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModTiles {

    @ObjectHolder(EmergingTechnology.MODID + ":" + Solar.name)
    public static TileEntityType<SolarTile> SOLAR;

    public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().register(TileEntityType.Builder.create(SolarTile::new, ModBlocks.SOLAR).build(null).setRegistryName(Solar.name));
    }

}