package io.moonman.emergingtechnology.item.hydroponics.nozzles;

import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.util.text.TextComponentTranslation;

public class NozzlePrecise extends NozzleBase {

    public NozzlePrecise() {
        super("nozzleprecise");
    }
    
    @Override
    public TextComponentTranslation getSpecialDescription() {
        return Lang.NOZZLE_LONG_DESC;
    }
}