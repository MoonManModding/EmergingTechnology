package io.moonman.emergingtechnology.item.synthetics.consumables;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.item.synthetics.CookedMeatItemBase;

public class SyntheticPigCooked extends CookedMeatItemBase {

    public SyntheticPigCooked() {
        super("pig", "minecraft:pig", EmergingTechnologyConfig.SYNTHETICS_MODULE.porkchopHunger,
                (float) EmergingTechnologyConfig.SYNTHETICS_MODULE.porkchopHungerSaturation);
    }
}