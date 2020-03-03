package io.moonman.emergingtechnology.util;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KeyBindings {

public KeyBindings() {
}

@SideOnly(Side.CLIENT)
public static boolean showExtendedTooltips() {
    return GuiScreen.isShiftKeyDown();
}

}