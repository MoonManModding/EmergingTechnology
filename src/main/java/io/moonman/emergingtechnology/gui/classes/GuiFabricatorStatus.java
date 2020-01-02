package io.moonman.emergingtechnology.gui.classes;

import io.moonman.emergingtechnology.helpers.machines.enums.FabricatorStatusEnum;

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
                return "Unable to fabricate item";
            case INSUFFICIENT_ENERGY:
                return "Insufficient energy";
            case INSUFFICIENT_INPUT:
                return "Insufficient items in input";
            case OUTPUT_FULL:
                return "Output full";
            case INVALID_OUTPUT:
                return "Invalid output item";
            case INVALID_INPUT:
                return "Invalid input item";
            default:
                return "No problems detected";
        }
    }
}