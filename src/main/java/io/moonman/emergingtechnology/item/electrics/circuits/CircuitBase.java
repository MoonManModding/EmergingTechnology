package io.moonman.emergingtechnology.item.electrics.circuits;

import java.util.List;

import io.moonman.emergingtechnology.item.ItemBase;
import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CircuitBase extends ItemBase {

    private final int cores;

    public CircuitBase(String name, int cores) {
        super(name);

        this.cores = cores;
    }

    public int getCores() {
        return this.cores;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        tooltip.add(Lang.getCircuitDescription(cores));
    }
}