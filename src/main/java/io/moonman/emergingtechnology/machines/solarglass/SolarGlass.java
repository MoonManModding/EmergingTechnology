package io.moonman.emergingtechnology.machines.solarglass;

import java.util.List;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.enums.ResourceTypeEnum;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.machines.SimpleMachineBase;
import io.moonman.emergingtechnology.util.KeyBindings;
import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;

public class SolarGlass extends SimpleMachineBase implements ITileEntityProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public SolarGlass() {
        super(Material.GLASS, "solarglass");
        this.setSoundType(SoundType.METAL);
        this.setLightOpacity(0);

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
            EnumFacing side) {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();

        if (block == this || block == ModBlocks.clearplasticblock) {
            return false;
        }

        return block == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {

        int energy = EmergingTechnologyConfig.ELECTRICS_MODULE.SOLARGLASS.solarEnergyGenerated;

        if (KeyBindings.showExtendedTooltips()) {
            tooltip.add(Lang.get(Lang.SOLARGLASS_DESC));
            tooltip.add(Lang.getGenerated(energy, ResourceTypeEnum.ENERGY));
        } else {
            tooltip.add(Lang.get(Lang.INTERACT_SHIFT));
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new SolarGlassTileEntity();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { FACING });
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState north = worldIn.getBlockState(pos.north());
            IBlockState east = worldIn.getBlockState(pos.east());
            IBlockState south = worldIn.getBlockState(pos.south());
            IBlockState west = worldIn.getBlockState(pos.west());

            EnumFacing face = (EnumFacing) state.getValue(FACING);

            if (face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) {
                face = EnumFacing.SOUTH;
            } else if (face == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) {
                face = EnumFacing.NORTH;
            } else if (face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) {
                face = EnumFacing.WEST;
            } else if (face == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) {
                face = EnumFacing.EAST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, face));
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
            ItemStack stack) {
        worldIn.setBlockState(pos,
                this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getHorizontal(meta);
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }
}