package io.moonman.emergingtechnology.machines.collector;

import java.util.List;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.MachineBase;
import io.moonman.emergingtechnology.util.KeyBindings;
import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;

public class Collector extends MachineBase implements ITileEntityProvider {

    public static final PropertyBool HAS_ITEMS = PropertyBool.create("hasitems");

    public Collector() {
        super(Material.IRON, "collector");
        this.setSoundType(SoundType.METAL);

        setDefaultState(blockState.getBaseState().withProperty(HAS_ITEMS, false));
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        int count = EmergingTechnologyConfig.POLYMERS_MODULE.COLLECTOR.minimumWaterBlocks;
        
        if (KeyBindings.showExtendedTooltips()) {
            tooltip.add(Lang.get(Lang.COLLECTOR_DESC));
            tooltip.add(Lang.get(Lang.BIOME_REQUIREMENT));
            tooltip.add(Lang.getWaterBlocksRequired(count));
            tooltip.add(Lang.get(Lang.WATER_SURFACE_REQUIREMENT));
        } else {
            tooltip.add(Lang.get(Lang.INTERACT_SHIFT));
        }

    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote) {
            return true;
        }

        playerIn.openGui(EmergingTechnology.instance, Reference.GUI_COLLECTOR, worldIn, pos.getX(), pos.getY(),
                pos.getZ());

        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new CollectorTileEntity();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { HAS_ITEMS });
    }

    public static void setState(boolean hasItems, World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos);

        worldIn.setBlockState(pos, state.withProperty(HAS_ITEMS, hasItems), 3);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(HAS_ITEMS, (meta & 8) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(HAS_ITEMS) ? 8 : 0;
        return meta;
    }
}