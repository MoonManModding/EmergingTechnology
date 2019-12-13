package io.moonman.emergingtechnology.item.hydroponics;

import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GreenBulb extends BulbItem {

    public GreenBulb() {
        super("greenbulb");
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        int growthMultiplier = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthGreenBulbModifier;
        int energyUsage = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyGreenBulbModifier;

        tooltip.add("A bulb used in a Hydroponic Grow Light.");
        tooltip.add("Provides " + growthMultiplier + "% boost to growth and uses " + energyUsage + "RF");
    }
}