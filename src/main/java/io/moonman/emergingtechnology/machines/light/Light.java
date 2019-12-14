package io.moonman.emergingtechnology.machines.light;

import java.util.List;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.helpers.machines.LightHelper;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.MachineBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk.EnumCreateEntityType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;

public class Light extends MachineBase implements ITileEntityProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    public static final PropertyInteger BULBTYPE = PropertyInteger.create("bulbtype", 0, 4);

    protected static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0f, 1.0f, 0.0f, 1.0f, 0.69f, 1.0f);

    public Light() {
        super(Material.GLASS, "light");
        this.setSoundType(SoundType.GLASS);
        this.setLightLevel(1.0f);
        this.setLightOpacity(0);

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false)
                .withProperty(BULBTYPE, 0));
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        int range = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightBlockRange;
        int energyUsage = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightEnergyBaseUsage;

        tooltip.add("Provides a growth boost to plants up to " + range + " blocks below.");
        tooltip.add("Must be powered at " + energyUsage + "RF and contain a bulb to function.");
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote) {
            return true;
        }

        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (tileEntity instanceof LightTileEntity) {

            LightTileEntity lightTileEntity = (LightTileEntity) tileEntity;

            ItemStack itemStackHeld = playerIn.getHeldItemMainhand();

            if (LightHelper.isItemStackValidBulb(itemStackHeld)
                    && lightTileEntity.itemHandler.getStackInSlot(0).isEmpty()) {

                ItemStack remainder = lightTileEntity.itemHandler.insertItem(0, itemStackHeld, false);

                playerIn.setHeldItem(EnumHand.MAIN_HAND, remainder);

                return true;
            }
        }

        playerIn.openGui(EmergingTechnology.instance, Reference.GUI_LIGHT, worldIn, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new LightTileEntity();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { FACING, POWERED, BULBTYPE });
    }

    public static void setState(boolean hasPower, int bulbType, World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos);
        TileEntity tileEntity = worldIn.getTileEntity(pos);

        worldIn.setBlockState(pos, ModBlocks.light.getDefaultState().withProperty(FACING, state.getValue(FACING))
                .withProperty(POWERED, hasPower).withProperty(BULBTYPE, bulbType), 3);

        if (tileEntity != null) {
            tileEntity.validate();
            worldIn.setTileEntity(pos, tileEntity);
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        int bulbTypeId = 0;
        boolean powered = false;

        TileEntity tileEntity = worldIn instanceof ChunkCache
                ? ((ChunkCache) worldIn).getTileEntity(pos, EnumCreateEntityType.CHECK)
                : worldIn.getTileEntity(pos);

        if (tileEntity instanceof LightTileEntity) {
            LightTileEntity lightTileEntity = (LightTileEntity) tileEntity;

            int id = lightTileEntity.getBulbTypeId();

            bulbTypeId = LightHelper.getBulbColourFromBulbId(id);
            powered = lightTileEntity.getEnergy() > 0;
        }

        return state.withProperty(POWERED, powered).withProperty(BULBTYPE, bulbTypeId);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7)).withProperty(POWERED,
                (meta & 8) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex() + (state.getValue(POWERED) ? 8 : 0);
    }
}