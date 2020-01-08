package io.moonman.emergingtechnology.item.polymers;

import io.moonman.emergingtechnology.item.ItemBase;
import net.minecraft.item.ItemStack;

public class ShreddedPlant extends ItemBase {

    public ShreddedPlant() {
        super("shreddedplant");
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return 800;
    }
}