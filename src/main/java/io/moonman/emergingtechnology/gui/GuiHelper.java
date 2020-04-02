package io.moonman.emergingtechnology.gui;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.gui.classes.GuiPosition;
import io.moonman.emergingtechnology.gui.classes.GuiRegion;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

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
    public static final int LIGHT_COLOUR = 16777215;

    public static final int WARNING_COLOUR = 16738816;

    public static final int ENERGY_USAGE_COLOUR = 14567989;

    // Textures
    public static final ResourceLocation PLAY_BUTTON_TEXTURE = new ResourceLocation(EmergingTechnology.MODID, "textures/gui/buttons/play.png");
    public static final ResourceLocation STOP_BUTTON_TEXTURE = new ResourceLocation(EmergingTechnology.MODID, "textures/gui/buttons/stop.png");
    public static final ResourceLocation LEFT_BUTTON_TEXTURE = new ResourceLocation(EmergingTechnology.MODID, "textures/gui/buttons/left.png");
    public static final ResourceLocation RIGHT_BUTTON_TEXTURE = new ResourceLocation(EmergingTechnology.MODID, "textures/gui/buttons/right.png");
    public static final ResourceLocation OVERLAY_BUTTON_TEXTURE = new ResourceLocation(EmergingTechnology.MODID, "textures/gui/buttons/overlay.png");
    
    public static final ResourceLocation UP_BUTTON_TEXTURE = new ResourceLocation(EmergingTechnology.MODID, "textures/gui/buttons/up.png");
    public static final ResourceLocation DOWN_BUTTON_TEXTURE = new ResourceLocation(EmergingTechnology.MODID, "textures/gui/buttons/down.png");

    public static final ResourceLocation ON_BUTTON_TEXTURE = new ResourceLocation(EmergingTechnology.MODID, "textures/gui/buttons/on.png");
    public static final ResourceLocation OFF_BUTTON_TEXTURE = new ResourceLocation(EmergingTechnology.MODID, "textures/gui/buttons/off.png");

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
    
    public static GuiPosition getMiddleLower(int xSize, int labelLength) {
        return new GuiPosition((xSize - labelLength) - 61, 67);
    }

    public static GuiPosition getMiddleBottom(int xSize, int labelLength) {
        return new GuiPosition((xSize - labelLength) - 61, 107);
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
}