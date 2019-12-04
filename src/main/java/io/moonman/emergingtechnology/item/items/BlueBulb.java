package io.moonman.emergingtechnology.item.items;

import io.moonman.emergingtechnology.EmergingTechnology;

public class BlueBulb extends BulbItem {

    private final String _name = "bluebulb";

    public BlueBulb() {
        this.setRegistryName(EmergingTechnology.MODID, _name);
        this.setUnlocalizedName(EmergingTechnology.MODID + "." + _name);
        this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
        this.setMaxStackSize(64);
    }
}