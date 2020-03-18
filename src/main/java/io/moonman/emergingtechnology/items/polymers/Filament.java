package io.moonman.emergingtechnology.items.polymers;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.items.classes.ItemBase;

public class Filament extends ItemBase {

    public static final String name = "filament";
    public static final String registryName = EmergingTechnology.MODID_REG + name;

    public Filament() {
        super(name);
    }
}