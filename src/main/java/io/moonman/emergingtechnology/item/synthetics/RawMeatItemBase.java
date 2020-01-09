package io.moonman.emergingtechnology.item.synthetics;

import java.util.List;

import io.moonman.emergingtechnology.item.ItemBase;
import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RawMeatItemBase extends ItemBase {

    private String _name = "";
    public String entityId = "";

    public RawMeatItemBase(String name, String entityId) {
        super("synthetic" + name + "raw");
        this._name = name;
        this.entityId = entityId;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        tooltip.add(Lang.getMeatDescription(Lang.capitaliseName(this._name), false));
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        return Lang.getMeatName(Lang.capitaliseName(this._name), false);
    }
}