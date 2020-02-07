package io.moonman.emergingtechnology.item.hydroponics.nozzles;

import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.util.text.TextComponentTranslation;

public class NozzleLong extends NozzleBase {

    public NozzleLong() {
        super("nozzlelong");
    }

    @Override
    public TextComponentTranslation getSpecialDescription() {
        return Lang.NOZZLE_LONG_DESC;
    }

}