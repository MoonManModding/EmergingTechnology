package io.moonman.emergingtechnology.gui.classes;

import io.moonman.emergingtechnology.helpers.machines.enums.FabricatorStatusEnum;
import io.moonman.emergingtechnology.util.Lang;

public class GuiFabricatorStatus {

    private GuiRegion statusRegion;

    public GuiFabricatorStatus(int x, int y) {
        this.statusRegion = new GuiRegion(x, y, x + 5, y + 7);
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return this.statusRegion.isPositionInRegion(new GuiPosition(mouseX, mouseY));
    }

    public String getMessageForStatus(FabricatorStatusEnum status) {
        switch(status) {
            case ERROR:
                return Lang.get(Lang.GUI_FABRICATOR_ERROR);
            case INSUFFICIENT_ENERGY:
                return Lang.get(Lang.GUI_FABRICATOR_NO_ENERGY);
            case INSUFFICIENT_INPUT:
                return Lang.get(Lang.GUI_FABRICATOR_NO_INPUT);
            case OUTPUT_FULL:
                return Lang.get(Lang.GUI_FABRICATOR_FULL_OUTPUT);
            case INVALID_OUTPUT:
                return Lang.get(Lang.GUI_FABRICATOR_INVALID_OUTPUT);
            case INVALID_INPUT:
                return Lang.get(Lang.GUI_FABRICATOR_INVALID_INPUT);
            default:
                return "OK";
        }
    }
}