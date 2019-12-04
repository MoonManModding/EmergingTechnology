package io.moonman.emergingtechnology.item.items;

import io.moonman.emergingtechnology.EmergingTechnology;

public class GreenBulb extends BulbItem {

    private final String _name = "greenbulb";

    public GreenBulb() {
        this.setRegistryName(EmergingTechnology.MODID, _name);
        this.setUnlocalizedName(EmergingTechnology.MODID + "." + _name);
        this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
        this.setMaxStackSize(64);
    }
}