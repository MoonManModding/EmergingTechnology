package io.moonman.emergingtechnology.item.hydroponics.nozzles;

import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.util.text.TextComponentTranslation;

public class NozzleWide extends NozzleBase {

    public NozzleWide() {
        super("nozzlewide");
    }

    @Override
    public TextComponentTranslation getSpecialDescription() {
        return Lang.NOZZLE_WIDE_DESC;
    }
}