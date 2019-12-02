package io.moonman.emergingtechnology.block.blocks;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.helpers.HydroponicHelper;
import io.moonman.emergingtechnology.init.ModBlocks;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.tile.tiles.HydroponicTileEntity;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.ITileEntityProvider;

public class Hydroponic extends Block implements ITileEntityProvider {

    public static final PropertyBool HAS_WATER = PropertyBool.create("haswater");
    public static final PropertyInteger MEDIUM = PropertyInteger.create("medium", 0, HydroponicHelper.getValidGrowthMedia().length + 1);

    private final String _name = "hydroponic";

    public Hydroponic() {
        super(Material.ANVIL);
        this.setHardness(3.0f);
        this.setRegistryName(EmergingTechnology.MODID, _name);
        this.setUnlocalizedName(EmergingTechnology.MODID + "." + _name);
        this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
        this.setSoundType(SoundType.METAL);

        setDefaultState(blockState.getBaseState().withProperty(HAS_WATER, false).withProperty(MEDIUM, 0));
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction,
            IPlantable plantable) {
        return state.getValue(HAS_WATER);
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote) {
            return true;
        }

        Item itemHeld = playerIn.getHeldItemMainhand().getItem();

        if (itemHeld == Items.WATER_BUCKET) {
            HydroponicTileEntity tileEntity = (HydroponicTileEntity) worldIn.getTileEntity(pos);
            tileEntity.fluidHandler.fill(new FluidStack(FluidRegistry.WATER, 1000), true);
        } else if (HydroponicHelper.isPlantItem(itemHeld) && facing == EnumFacing.UP) {
            return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        }
         else {
            playerIn.openGui(EmergingTechnology.instance, Reference.GUI_HYDROPONIC, worldIn, pos.getX(), pos.getY(),
                    pos.getZ());
            return true;
        }
        
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    public static void setState(boolean hasWater, int medium, World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos);
        TileEntity tileEntity = worldIn.getTileEntity(pos);

        worldIn.setBlockState(pos, 
        ModBlocks.hydroponic.getDefaultState()
        .withProperty(HAS_WATER, hasWater)
        .withProperty(MEDIUM, medium)
        , 3);

        if (tileEntity != null) {
            tileEntity.validate();
            worldIn.setTileEntity(pos, tileEntity);
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
      HydroponicTileEntity te = (HydroponicTileEntity) world.getTileEntity(pos);
        IItemHandler cap = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        for (int i = 0; i < cap.getSlots(); ++i)
        {
            ItemStack itemstack = cap.getStackInSlot(i);

            if (!itemstack.isEmpty())
            {
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new HydroponicTileEntity();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { HAS_WATER, MEDIUM });
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        boolean hasWater = meta == 1;
        return this.getDefaultState().withProperty(HAS_WATER, hasWater);

    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HAS_WATER) == true ? 1 : 0;
    }

}