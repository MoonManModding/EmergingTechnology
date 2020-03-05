package io.moonman.emergingtechnology.item.hydroponics.nozzles;

import io.moonman.emergingtechnology.util.Lang;

public class NozzleLong extends NozzleBase {

    public NozzleLong() {
        super("nozzlelong");
    }

    @Override
    public String getSpecialDescription() {
        return Lang.getLongNozzleDescription();
    }

}