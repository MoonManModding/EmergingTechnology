package io.moonman.emergingtechnology.items.classes;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase(String name) {
        super(new Item.Properties().group(EmergingTechnology.ITEMGROUP).maxStackSize(64));

        setRegistryName(name);
    }

    public ItemBase(String name, Food food) {
        super(new Item.Properties().group(EmergingTechnology.ITEMGROUP).maxStackSize(64).food(food));

        setRegistryName(name);
    }
}