package io.moonman.emergingtechnology.machines.light;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.helpers.machines.LightHelper;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.Reference;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk.EnumCreateEntityType;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;

public class Light extends Block implements ITileEntityProvider {

    private final String _name = "light";

    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    public static final PropertyInteger BULBTYPE = PropertyInteger.create("bulbtype", 0, LightHelper.BULB_COUNT);

    protected static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0f, 1.0f, 0.0f, 1.0f, 0.69f, 1.0f);

    public Light() {
        super(Material.GLASS);
        this.setHardness(1.0f);
        this.setRegistryName(EmergingTechnology.MODID, _name);
        this.setUnlocalizedName(EmergingTechnology.MODID + "." + _name);
        this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
        this.setSoundType(SoundType.GLASS);
        this.setLightLevel(1.0f);
        this.setLightOpacity(0);

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false)
                .withProperty(BULBTYPE, 0));
    }

    @Override
    public boolean isOpaqueCube(IBlockState iBlockState) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState iBlockState) {
        return false;
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
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
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
    public int getLightValue(IBlockState state) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        LightTileEntity te = (LightTileEntity) world.getTileEntity(pos);
        IItemHandler cap = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        for (int i = 0; i < cap.getSlots(); ++i) {
            ItemStack itemstack = cap.getStackInSlot(i);

            if (!itemstack.isEmpty()) {
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote) {
            return true;
        }

        playerIn.openGui(EmergingTechnology.instance, Reference.GUI_LIGHT, worldIn, pos.getX(), pos.getY(),
                pos.getZ());
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

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        int bulbTypeId = 0;

        TileEntity tileEntity = worldIn instanceof ChunkCache ? ((ChunkCache)worldIn).getTileEntity(pos, EnumCreateEntityType.CHECK) : worldIn.getTileEntity(pos);

        if (tileEntity instanceof LightTileEntity)
        {
            LightTileEntity lightTileEntity = (LightTileEntity) tileEntity;

            bulbTypeId = lightTileEntity.getBulbTypeId();
        }

        return state.withProperty(BULBTYPE, bulbTypeId);
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