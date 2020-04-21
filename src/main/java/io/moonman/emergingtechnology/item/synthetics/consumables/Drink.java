package io.moonman.emergingtechnology.item.synthetics.consumables;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.item.ItemFoodBase;

public class Drink extends ItemFoodBase {

    public Drink() {
        super("drink", EmergingTechnologyConfig.SYNTHETICS_MODULE.drinkHunger,
        (float) EmergingTechnologyConfig.SYNTHETICS_MODULE.drinkHungerSaturation);
    }
}