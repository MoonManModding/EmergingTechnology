package io.moonman.emergingtechnology.item.synthetics.syringes;

import java.util.List;

import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.item.synthetics.SyringeItemBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EmptySyringe extends SyringeItemBase {

    public EmptySyringe() {
        super("empty");
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        tooltip.add("An empty syringe used for collecting tissue samples from animals.");
        tooltip.add("Right click to take a sample.");
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target,
            EnumHand hand) {

        if (playerIn.world.isRemote) {
            return false;
        }

        if (target instanceof EntityCow) {
            playerIn.setHeldItem(hand, new ItemStack(ModItems.cowsyringe));
            return true;
        }

        if (target instanceof EntityPig) {
            playerIn.setHeldItem(hand, new ItemStack(ModItems.pigsyringe));
            return true;
        }

        if (target instanceof EntityChicken) {
            playerIn.setHeldItem(hand, new ItemStack(ModItems.chickensyringe));
            return true;
        }

        if (target instanceof EntityHorse){
            playerIn.setHeldItem(hand, new ItemStack(ModItems.horsesyringe));
            return true;
        }

        return false;
    }
}