package io.moonman.emergingtechnology.item.hydroponics;

import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PurpleBulb extends BulbItem {

    public PurpleBulb() {
        super("purplebulb");
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        int growthMultiplier = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthPurpleBulbModifier;
        int energyUsage = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyPurpleBulbModifier;

        tooltip.add("A bulb used in a Hydroponic Grow Light.");
        tooltip.add("Provides " + growthMultiplier + "% boost to growth and uses " + energyUsage + "RF");
    }
}