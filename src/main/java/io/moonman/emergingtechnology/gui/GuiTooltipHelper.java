package io.moonman.emergingtechnology.gui;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.gui.classes.GuiIndicatorData;
import io.moonman.emergingtechnology.gui.classes.GuiLabel;
import io.moonman.emergingtechnology.gui.classes.GuiPosition;
import io.moonman.emergingtechnology.gui.classes.GuiRegion;
import io.moonman.emergingtechnology.gui.enums.IndicatorPositionEnum;
import io.moonman.emergingtechnology.helpers.enums.ResourceTypeEnum;
import io.moonman.emergingtechnology.util.Lang;

public class GuiTooltipHelper {

    public static GuiIndicatorData getIndicatorData(int guiStartLeft, int guiStartTop, ResourceTypeEnum type,
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

    public static GuiIndicatorData getHydroponicGrowthData(int guiStartLeft, int guiStartTop, int mouseX, int mouseY, int growthFromMedium, int growthFromFluid, int boostFromMedium, int boostFromFluid, int growthFromLight) {

        GuiLabel label = getLabel(ResourceTypeEnum.GROWTH);

        GuiRegion region = getRegion(IndicatorPositionEnum.MAIN, new GuiPosition(guiStartLeft, guiStartTop));
        GuiPosition mousePosition = new GuiPosition(mouseX, mouseY);

        if (region.isPositionInRegion(mousePosition)) {
            List<String> list = new ArrayList<String>();
            list.add(label.header);
            list.add(Lang.get(Lang.GUI_BASE_MEDIUM) + growthFromMedium + label.unit);
            list.add(Lang.get(Lang.GUI_BASE_FLUID) + growthFromFluid + label.unit);
            list.add(Lang.get(Lang.GUI_BOOST_MEDIUM) + boostFromMedium + label.unit);
            list.add(Lang.get(Lang.GUI_BOOST_FLUID) + boostFromFluid + label.unit);
            list.add(Lang.get(Lang.GUI_BOOST_LIGHT) + growthFromLight + label.unit);
            return new GuiIndicatorData(true, list);
        }

        return new GuiIndicatorData(false, new ArrayList<String>());
    }

    public static GuiIndicatorData getLightGrowData(int guiStartLeft, int guiStartTop, int mouseX, int mouseY, int growthFromBulb, int boostFromBulb) {

        GuiLabel label = getLabel(ResourceTypeEnum.GROWTH);

        GuiRegion region = getRegion(IndicatorPositionEnum.MAIN, new GuiPosition(guiStartLeft, guiStartTop));
        GuiPosition mousePosition = new GuiPosition(mouseX, mouseY);

        if (region.isPositionInRegion(mousePosition)) {
            List<String> list = new ArrayList<String>();
            list.add(label.header);
            list.add(Lang.get(Lang.GUI_BASE) + growthFromBulb + label.unit);
            list.add(Lang.get(Lang.GUI_BOOST) + boostFromBulb + label.unit);
            list.add(Lang.get(Lang.GUI_MAX_RANGE) + EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightBlockRange);
            return new GuiIndicatorData(true, list);
        }

        return new GuiIndicatorData(false, new ArrayList<String>());
    }

    private static GuiLabel getLabel(ResourceTypeEnum type) {
        switch (type) {
        case ENERGY:
            return new GuiLabel(Lang.get(Lang.GUI_STORAGE_ENERGY), "RF");
        case FLUID:
            return new GuiLabel(Lang.get(Lang.GUI_STORAGE_FLUID), "MB");
        case HEAT:
            return new GuiLabel(Lang.get(Lang.GUI_STORAGE_HEAT), "C");
        case GROWTH:
            return new GuiLabel(Lang.get(Lang.GUI_GROWTH), "%");
        case GAS:
            return new GuiLabel(Lang.get(Lang.GUI_STORAGE_GAS), "MB");
        default:
            return new GuiLabel(Lang.get(Lang.GUI_ERROR), "$");
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
        case LOWER:
            return new GuiRegion(guiStart.x + 66, guiStart.y + 63, guiStart.x + 110, guiStart.y + 77);
        default:
            return new GuiRegion(guiStart.x, guiStart.x, guiStart.y, guiStart.y);
        }
    }
}