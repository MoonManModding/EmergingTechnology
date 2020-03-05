package io.moonman.emergingtechnology.item.synthetics.consumables;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.item.synthetics.CookedMeatItemBase;

public class SyntheticCowCooked extends CookedMeatItemBase {

    public SyntheticCowCooked() {
        super("cow", "minecraft:cow", EmergingTechnologyConfig.SYNTHETICS_MODULE.beefHunger,
                (float) EmergingTechnologyConfig.SYNTHETICS_MODULE.beefHungerSaturation);
    }
}