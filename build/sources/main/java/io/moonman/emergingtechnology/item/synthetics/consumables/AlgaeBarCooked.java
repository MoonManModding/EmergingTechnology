package io.moonman.emergingtechnology.item.synthetics.consumables;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.item.ItemFoodBase;

public class AlgaeBarCooked extends ItemFoodBase {

    public AlgaeBarCooked() {
        super("algaebarcooked", EmergingTechnologyConfig.SYNTHETICS_MODULE.algaeHunger,
        (float) EmergingTechnologyConfig.SYNTHETICS_MODULE.algaeHungerSaturation);
    }
}