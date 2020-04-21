package io.moonman.emergingtechnology.machines.filler;

import java.util.List;

import javax.annotation.Nullable;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.gui.enums.ResourceTypeEnum;
import io.moonman.emergingtechnology.helpers.CollisionHelper;
import io.moonman.emergingtechnology.machines.classes.block.SimpleMachineBase;
import io.moonman.emergingtechnology.util.KeyBindings;
import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

public class Filler extends SimpleMachineBase implements ITileEntityProvider {

    public Filler() {
        super(Material.IRON, "filler");
        this.setSoundType(SoundType.METAL);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        int water = EmergingTechnologyConfig.HYDROPONICS_MODULE.FILLER.fillerFluidTransferRate;

        if (KeyBindings.showExtendedTooltips()) {
            tooltip.add(Lang.get(Lang.FILLER_DESC));
            tooltip.add(Lang.getGenerated(water, ResourceTypeEnum.WATER));
        } else {
            tooltip.add(Lang.get(Lang.INTERACT_SHIFT));
        }
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
            List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if (!isActualState) {
            state = this.getActualState(state, worldIn, pos);
        }

        for (AxisAlignedBB axisalignedbb : CollisionHelper.getMachineCollisionBoxList(state)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new FillerTileEntity();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote) {
            return true;
        }

        ItemStack itemStackHeld = playerIn.getHeldItemMainhand();
        Item itemHeld = itemStackHeld.getItem();

        if (itemHeld == Items.BUCKET) {
            ItemHandlerHelper.giveItemToPlayer(playerIn, new ItemStack(Items.WATER_BUCKET));
            itemStackHeld.shrink(1);
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}