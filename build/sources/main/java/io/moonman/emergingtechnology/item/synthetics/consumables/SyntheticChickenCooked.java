package io.moonman.emergingtechnology.item.synthetics.consumables;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.item.synthetics.CookedMeatItemBase;

public class SyntheticChickenCooked extends CookedMeatItemBase {

    public SyntheticChickenCooked() {
        super("chicken", "minecraft:chicken", EmergingTechnologyConfig.SYNTHETICS_MODULE.chickenHunger,
                (float) EmergingTechnologyConfig.SYNTHETICS_MODULE.chickenHungerSaturation);
    }
}