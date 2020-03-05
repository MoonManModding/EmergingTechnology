package io.moonman.emergingtechnology.item.hydroponics.nozzles;

import io.moonman.emergingtechnology.util.Lang;

public class NozzleSmart extends NozzleBase {

    public NozzleSmart() {
        super("nozzlesmart");
    }

    @Override
    public String getSpecialDescription() {
        return Lang.getSmartNozzleDescription();
    }
}