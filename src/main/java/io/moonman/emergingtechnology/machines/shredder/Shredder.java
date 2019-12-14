package io.moonman.emergingtechnology.machines.shredder;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.MachineBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.block.ITileEntityProvider;

public class Shredder extends MachineBase implements ITileEntityProvider {

    public static final PropertyBool PROCESSING = PropertyBool.create("processing");

    public Shredder() {
        super(Material.IRON, "shredder");
        this.setSoundType(SoundType.METAL);

        setDefaultState(blockState.getBaseState().withProperty(PROCESSING, false));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote) {
            return true;
        }

        playerIn.openGui(EmergingTechnology.instance, Reference.GUI_SHREDDER, worldIn, pos.getX(), pos.getY(),
                pos.getZ());

        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new ShredderTileEntity();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { PROCESSING });
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        // TileEntity tileEntity = worldIn.getTileEntity(pos);

        // if (tileEntity instanceof ShredderTileEntity) {
        //     //ShredderTileEntity shredderTileEntity = (ShredderTileEntity) tileEntity;

        //     boolean isProcessing = false;

        //     return state.withProperty(PROCESSING, isProcessing);
        // }

        return state;

    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(PROCESSING,
                (meta & 8) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(PROCESSING) ? 8 : 0;
    }

}