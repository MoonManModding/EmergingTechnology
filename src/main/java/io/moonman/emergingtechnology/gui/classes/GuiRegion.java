package io.moonman.emergingtechnology.gui.classes;

public class GuiRegion {
    public final int topLeftX;
    public final int topLeftY;
    public final int bottomRightX;
    public final int bottomRightY;
    
    public GuiRegion(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.bottomRightX = bottomRightX;
        this.bottomRightY = bottomRightY;
    }

    public boolean isPositionInRegion(GuiPosition position) {
        return position.x >= topLeftX && position.x <= bottomRightX && position.y >= topLeftY && position.y <= bottomRightY;
    }

    public boolean isPositionInRegion(int x, int y) {
        return x >= topLeftX && x <= bottomRightX && y >= topLeftY && y <= bottomRightY;
    }
}