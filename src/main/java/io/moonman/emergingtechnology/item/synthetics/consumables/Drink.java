package io.moonman.emergingtechnology.item.synthetics.consumables;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.item.ItemFoodBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Drink extends ItemFoodBase {

    public Drink() {
        super("drink", EmergingTechnologyConfig.SYNTHETICS_MODULE.drinkHunger,
        (float) EmergingTechnologyConfig.SYNTHETICS_MODULE.drinkHungerSaturation);
    }
    
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {

        EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer)entityLiving : null;

        if (entityplayer == null || !entityplayer.capabilities.isCreativeMode)
        {
            if (stack.isEmpty())
            {
                return new ItemStack(ModItems.plasticbottle);
            }

            if (entityplayer != null)
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(ModItems.plasticbottle));
            }
        }

        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }


}