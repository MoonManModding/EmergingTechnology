package io.moonman.emergingtechnology.item;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase(String name) {
        this.setRegistryName(EmergingTechnology.MODID, name);
        this.setUnlocalizedName(EmergingTechnology.MODID + "." + name);
        this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
        this.setMaxStackSize(64);
    }
}