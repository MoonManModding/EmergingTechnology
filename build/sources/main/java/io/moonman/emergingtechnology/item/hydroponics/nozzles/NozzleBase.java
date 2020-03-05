package io.moonman.emergingtechnology.item.hydroponics.nozzles;

import java.util.List;

import io.moonman.emergingtechnology.item.ItemBase;
import io.moonman.emergingtechnology.util.KeyBindings;
import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NozzleBase extends ItemBase {

    public NozzleBase(String name) {
        super(name);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {

        if (KeyBindings.showExtendedTooltips()) {
            tooltip.add(Lang.get(Lang.NOZZLE_DESC));
            tooltip.add(getSpecialDescription());
        } else {
            tooltip.add(Lang.get(Lang.INTERACT_SHIFT));
        }
    }

    public String getSpecialDescription() {
        return "";
    }
}