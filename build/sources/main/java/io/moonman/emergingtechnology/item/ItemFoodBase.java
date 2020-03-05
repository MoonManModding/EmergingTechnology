package io.moonman.emergingtechnology.item;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraft.item.ItemFood;

public class ItemFoodBase extends ItemFood {

    public ItemFoodBase(String name, int healAmount, float saturation) {
        super(healAmount, saturation, false);
        this.setRegistryName(EmergingTechnology.MODID, name);
        this.setUnlocalizedName(EmergingTechnology.MODID + "." + name);
        this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
        this.setMaxStackSize(64);
    }
}