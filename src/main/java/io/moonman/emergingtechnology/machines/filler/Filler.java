package io.moonman.emergingtechnology.machines.filler;

import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.enums.ResourceTypeEnum;
import io.moonman.emergingtechnology.machines.SimpleMachineBase;
import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;

public class Filler extends SimpleMachineBase implements ITileEntityProvider {

    public Filler() {
        super(Material.IRON, "filler");
        this.setSoundType(SoundType.METAL);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        int water = EmergingTechnologyConfig.HYDROPONICS_MODULE.FILLER.fillerFluidTransferRate;

        tooltip.add(Lang.get(Lang.FILLER_DESC));
        tooltip.add(Lang.getGenerated(water, ResourceTypeEnum.WATER));
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

        if (itemHeld instanceof UniversalBucket) {

            UniversalBucket bucket = (UniversalBucket) itemHeld;

            if (bucket.getEmpty().isEmpty()) {
                playerIn.setHeldItem(EnumHand.MAIN_HAND, FluidUtil.getFilledBucket(new FluidStack(FluidRegistry.WATER, 1000)));
            }
        } else if (itemHeld == Items.BUCKET) {
            playerIn.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.WATER_BUCKET));
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}