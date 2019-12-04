package io.moonman.emergingtechnology.item.items;

import io.moonman.emergingtechnology.EmergingTechnology;

public class RedBulb extends BulbItem {

    private final String _name = "redbulb";

    public RedBulb() {
        this.setRegistryName(EmergingTechnology.MODID, _name);
        this.setUnlocalizedName(EmergingTechnology.MODID + "." + _name);
        this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
        this.setMaxStackSize(64);
    }
}