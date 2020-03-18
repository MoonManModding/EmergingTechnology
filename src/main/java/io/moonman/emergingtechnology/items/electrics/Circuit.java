package io.moonman.emergingtechnology.items.electrics;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.items.classes.ItemBase;

public class Circuit extends ItemBase {

    public static final String name = "circuit";
    public static final String registryName = EmergingTechnology.MODID_REG + name;

    public Circuit() {
        super(name);
    }
}