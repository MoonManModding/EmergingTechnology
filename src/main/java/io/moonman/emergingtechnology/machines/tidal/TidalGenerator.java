package io.moonman.emergingtechnology.machines.tidal;

import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.gui.enums.ResourceTypeEnum;
import io.moonman.emergingtechnology.machines.SimpleMachineBase;
import io.moonman.emergingtechnology.util.KeyBindings;
import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;

public class TidalGenerator extends SimpleMachineBase implements ITileEntityProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public TidalGenerator() {
        super(Material.IRON, "tidalgenerator");
        this.setSoundType(SoundType.METAL);

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {

        int energy = EmergingTechnologyConfig.ELECTRICS_MODULE.TIDALGENERATOR.tidalEnergyGenerated;
        int min = EmergingTechnologyConfig.ELECTRICS_MODULE.TIDALGENERATOR.minOptimalDepth;
        int max = EmergingTechnologyConfig.ELECTRICS_MODULE.TIDALGENERATOR.maxOptimalDepth;
        int surround = EmergingTechnologyConfig.ELECTRICS_MODULE.TIDALGENERATOR.minimumWaterBlocks;

        if (KeyBindings.showExtendedTooltips()) {
            tooltip.add(Lang.get(Lang.TIDAL_DESC));
            tooltip.add(Lang.getGenerated(energy, ResourceTypeEnum.ENERGY) + " " + Lang.getDepthBoost(min, max));
            tooltip.add(Lang.getWaterBlocksRequired(surround));
            if (!EmergingTechnologyConfig.ELECTRICS_MODULE.TIDALGENERATOR.biomeRequirementDisabled) {
                tooltip.add(Lang.get(Lang.BIOME_REQUIREMENT));
            }
        } else {
            tooltip.add(Lang.get(Lang.INTERACT_SHIFT));
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TidalGeneratorTileEntity();
    }

    @Override
    public ExtendedBlockState createBlockState() {
        return new ExtendedBlockState(this, new IProperty[] { FACING, Properties.StaticProperty },
                new IUnlistedProperty[] { Properties.AnimationProperty });
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

    // Required for animation (?)

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        // return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(Properties.StaticProperty, true);
    }

    // End required for animation

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