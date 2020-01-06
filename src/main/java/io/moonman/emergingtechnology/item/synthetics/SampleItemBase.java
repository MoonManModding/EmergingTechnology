package io.moonman.emergingtechnology.item.synthetics;

import java.util.List;

import io.moonman.emergingtechnology.item.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SampleItemBase extends ItemBase {

    private String _name = "";
    public String entityId = "";

    public SampleItemBase(String name, String entityId) {
        super(name + "sample");
        this._name = name;
        this.entityId = entityId;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        tooltip.add("A sample containing " + this._name + " tissue.");
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        return this._name + " Tissue Sample";
    }
}