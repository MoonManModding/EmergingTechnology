package io.moonman.emergingtechnology.item.hydroponics.nozzles;

import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.util.text.TextComponentTranslation;

public class NozzleSmart extends NozzleBase {

    public NozzleSmart() {
        super("nozzlesmart");
    }

    @Override
    public TextComponentTranslation getSpecialDescription() {
        return Lang.NOZZLE_SMART_DESC;
    }
}