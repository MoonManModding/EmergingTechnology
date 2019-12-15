package io.moonman.emergingtechnology.gui;

import io.moonman.emergingtechnology.gui.classes.GuiPosition;
import net.minecraft.entity.player.InventoryPlayer;

/**
Provides useful methods and values for GUI rendering
*/
public class GuiHelper {

    // Colours
    public static final int LABEL_COLOUR = 4210752;

    public static final int EMPTY_COLOUR = 8553090;
    public static final int VALID_COLOUR = 32526;
    public static final int INVALID_COLOUR = 14567989;

    public static final int DARK_COLOUR = 2631720;

    public static final int WARNING_COLOUR = 16738816;

    public static final int ENERGY_USAGE_COLOUR = 14567989;

    // Gui locations
    public static GuiPosition getTopLeft() {
        return new GuiPosition(6, 8);
    }

    public static GuiPosition getTopRight(int xSize, int labelLength) {
        return new GuiPosition(xSize - labelLength, 8);
    }

    public static GuiPosition getMiddleRight(int xSize, int labelLength) {
        return new GuiPosition(xSize - labelLength, 23);
    }

    public static GuiPosition getInventory(int ySize) {
        return new GuiPosition(118, ySize - 95);
    }

    public static GuiPosition getFirstField() {
        return new GuiPosition(50, 35);
    }

    public static GuiPosition getSecondField() {
        return new GuiPosition(105, 35);
    }

    // String formatting methods
    public static String inventoryLabel(InventoryPlayer player) {
        return player.getDisplayName().getUnformattedText();
    }

    public static String getEnergyUsageLabel(int energyUsage) {
        return "-" + energyUsage + "RF";
    }

    // public static class GuiIndicator {
    //     private float percentage;

    //     public GuiIndicator(int current, int capacity) {
    //         this.percentage = ((float) current / capacity) * 100;
    //     }

    //     public String getPercentageString() {
    //         return String.format("%.2f", percentage) + "%";
    //     }

    //     public int getPercentageColour() {
    //         if (percentage > 95) {
    //             return VALID_COLOUR;
    //         } else if (percentage > 25) {
    //             return WARNING_COLOUR;
    //         } else {
    //             return INVALID_COLOUR;
    //         }
    //     }
    // }



}