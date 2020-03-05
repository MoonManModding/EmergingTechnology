package io.moonman.emergingtechnology.item.electrics;

import io.moonman.emergingtechnology.item.ItemBase;
import net.minecraft.item.ItemStack;

public class Biomass extends ItemBase {

    public Biomass() {
        super("biomass");
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return 1600;
    }
}