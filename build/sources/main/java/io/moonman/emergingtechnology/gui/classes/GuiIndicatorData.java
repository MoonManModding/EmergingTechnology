package io.moonman.emergingtechnology.gui.classes;

import java.util.List;

public class GuiIndicatorData {

    public boolean isHovered = false;
    public List<String> list;

    public GuiIndicatorData(boolean isHovered, List<String> list) {
        this.isHovered = isHovered;
        this.list = list;
    }
}