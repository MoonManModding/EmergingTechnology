package io.moonman.emergingtechnology.util;

import io.moonman.emergingtechnology.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TechnologyTab extends CreativeTabs {

public TechnologyTab(String label) {
	super(label);
}

@Override
public ItemStack getTabIconItem() {
	return new ItemStack(ModItems.hydroponic);
}

}