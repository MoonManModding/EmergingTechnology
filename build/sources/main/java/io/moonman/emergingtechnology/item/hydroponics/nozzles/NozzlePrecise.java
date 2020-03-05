package io.moonman.emergingtechnology.item.hydroponics.nozzles;

import io.moonman.emergingtechnology.util.Lang;

public class NozzlePrecise extends NozzleBase {

    public NozzlePrecise() {
        super("nozzleprecise");
    }
    
    @Override
    public String getSpecialDescription() {
        return Lang.getPreciseNozzleDescription();
    }
}