package io.moonman.emergingtechnology.gui;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.gui.classes.GuiIndicatorData;
import io.moonman.emergingtechnology.gui.classes.GuiLabel;
import io.moonman.emergingtechnology.gui.classes.GuiPosition;
import io.moonman.emergingtechnology.gui.classes.GuiRegion;
import io.moonman.emergingtechnology.gui.enums.IndicatorPositionEnum;
import io.moonman.emergingtechnology.gui.enums.IndicatorTypeEnum;

public class GuiTooltipHelper {

    public static GuiIndicatorData getIndicatorData(int guiStartLeft, int guiStartTop, IndicatorTypeEnum type,
            IndicatorPositionEnum position, int mouseX, int mouseY, int level, int max) {

        GuiLabel label = getLabel(type);

        GuiRegion region = getRegion(position, new GuiPosition(guiStartLeft, guiStartTop));
        GuiPosition mousePosition = new GuiPosition(mouseX, mouseY);

        if (region.isPositionInRegion(mousePosition)) {
            List<String> list = new ArrayList<String>();
            list.add(label.header);
            list.add(level + "/" + max + label.unit);
            return new GuiIndicatorData(true, list);
        }

        return new GuiIndicatorData(false, new ArrayList<String>());
    }

    public static GuiIndicatorData getHydroponicGrowthData(int guiStartLeft, int guiStartTop, int mouseX, int mouseY, int growthFromMedium, int growthFromFluid, int boostFromMedium, int boostFromFluid) {

        GuiLabel label = getLabel(IndicatorTypeEnum.GROWTH);

        GuiRegion region = getRegion(IndicatorPositionEnum.MAIN, new GuiPosition(guiStartLeft, guiStartTop));
        GuiPosition mousePosition = new GuiPosition(mouseX, mouseY);

        if (region.isPositionInRegion(mousePosition)) {
            List<String> list = new ArrayList<String>();
            list.add(label.header);
            list.add("Base (Medium): " + growthFromMedium + label.unit);
            list.add("Base (Fluid): " + growthFromFluid + label.unit);
            list.add("Boost (Medium): " + boostFromMedium + label.unit);
            list.add("Boost (Fluid): " + boostFromFluid + label.unit);
            return new GuiIndicatorData(true, list);
        }

        return new GuiIndicatorData(false, new ArrayList<String>());
    }

    public static GuiIndicatorData getLightGrowData(int guiStartLeft, int guiStartTop, int mouseX, int mouseY, int growthFromBulb, int boostFromBulb) {

        GuiLabel label = getLabel(IndicatorTypeEnum.GROWTH);

        GuiRegion region = getRegion(IndicatorPositionEnum.MAIN, new GuiPosition(guiStartLeft, guiStartTop));
        GuiPosition mousePosition = new GuiPosition(mouseX, mouseY);

        if (region.isPositionInRegion(mousePosition)) {
            List<String> list = new ArrayList<String>();
            list.add(label.header);
            list.add("Base: " + growthFromBulb + label.unit);
            list.add("Boost: " + boostFromBulb + label.unit);
            return new GuiIndicatorData(true, list);
        }

        return new GuiIndicatorData(false, new ArrayList<String>());
    }

    private static GuiLabel getLabel(IndicatorTypeEnum type) {
        switch (type) {
        case ENERGY:
            return new GuiLabel("Energy Storage", "RF");
        case FLUID:
            return new GuiLabel("Fluid Storage", "MB");
        case HEAT:
            return new GuiLabel("Temperature", "C");
        case GROWTH:
            return new GuiLabel("Growth Modifiers", "%");
        default:
            return new GuiLabel("Error", "$");
        }
    }

    private static GuiRegion getRegion(IndicatorPositionEnum positionType, GuiPosition guiStart) {
        switch (positionType) {
        case PRIMARY:
            return new GuiRegion(guiStart.x + 119, guiStart.y + 4, guiStart.x + 171, guiStart.y + 18);
        case SECONDARY:
            return new GuiRegion(guiStart.x + 119, guiStart.y + 19, guiStart.x + 171, guiStart.y + 31);
        case MAIN:
            return new GuiRegion(guiStart.x + 43, guiStart.y + 29, guiStart.x + 142, guiStart.y + 55);
        default:
            return new GuiRegion(guiStart.x, guiStart.x, guiStart.y, guiStart.y);
        }
    }
}