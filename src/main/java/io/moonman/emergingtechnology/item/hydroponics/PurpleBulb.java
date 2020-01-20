package io.moonman.emergingtechnology.item.hydroponics;

import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.enums.ResourceTypeEnum;
import io.moonman.emergingtechnology.util.KeyBindings;
import io.moonman.emergingtechnology.util.Lang;
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
        int lightBaseEnergy = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightEnergyBaseUsage;
        int growth = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthPurpleBulbModifier;
        int energy = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyPurpleBulbModifier * lightBaseEnergy;

        if (KeyBindings.showExtendedTooltips()) {
            tooltip.add(Lang.get(Lang.BULB_DESC));
            tooltip.add(Lang.getRequired(energy, ResourceTypeEnum.ENERGY));
            tooltip.add(Lang.getGenerated(growth, ResourceTypeEnum.GROWTH));
        } else {
            tooltip.add(Lang.get(Lang.INTERACT_SHIFT));
        }
    }
}