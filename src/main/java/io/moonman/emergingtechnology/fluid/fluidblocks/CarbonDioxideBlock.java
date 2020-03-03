package io.moonman.emergingtechnology.fluid.fluidblocks;

import io.moonman.emergingtechnology.fluid.BlockFluidBase;
import io.moonman.emergingtechnology.init.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CarbonDioxideBlock extends BlockFluidBase {

    private static final String _name = "carbondioxideblock";

    public CarbonDioxideBlock() {
        super(_name, ModFluids.CARBON_DIOXIDE, Material.WATER);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor,
            float partialTicks) {
        if (!isWithinFluid(world, pos, ActiveRenderInfo.projectViewFromEntity(entity, partialTicks))) {
            BlockPos otherPos = pos.down(densityDir);
            IBlockState otherState = world.getBlockState(otherPos);
            return otherState.getBlock().getFogColor(world, otherPos, otherState, entity, originalColor, partialTicks);
        }

        if (getFluid() != null) {
            return new Vec3d(5.0F, 5.0F, 5.0F);
        }

        return super.getFogColor(world, pos, state, entity, originalColor, partialTicks);
    }

    private boolean isWithinFluid(IBlockAccess world, BlockPos pos, Vec3d vec) {
        float filled = getFilledPercentage(world, pos);
        return filled < 0 ? vec.y > pos.getY() + filled + 1 : vec.y < pos.getY() + filled;
    }

}