package io.moonman.emergingtechnology.item.synthetics;

import java.util.List;

import io.moonman.emergingtechnology.item.ItemBase;
import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SampleItemBase extends ItemBase {

    private String _displayName = "";
    public String entityId = "";

    public SampleItemBase(String registryName, String displayName, String entityId) {
        super(registryName + "sample");
        this._displayName = displayName;
        this.entityId = entityId;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        tooltip.add(Lang.getTissueDescription(Lang.capitaliseName(this._displayName), true));
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        return Lang.getTissueName(Lang.capitaliseName(this._displayName), true);
    }
}